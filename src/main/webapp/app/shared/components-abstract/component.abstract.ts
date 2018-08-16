import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as _ from 'lodash';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import * as R from 'ramda';
import { Subscription } from 'rxjs/Subscription';
import { Principal } from '../../core';
import { ITEMS_PER_PAGE } from '..';

export abstract class ComponentAbstract<T> implements OnDestroy {
    page: any;
    links: any;
    error: any;
    success: any;
    reverse: any;
    predicate: any;
    routeData: any;
    totalItems: any;
    queryCount: any;
    modelConsulta: T;
    previousPage: any;
    currentAccount: any;
    listResultQuery: T[];
    itemsPerPage: number;
    eventSubscriber: Subscription;
    protected hasMadeQuery: boolean;

    constructor(
        protected parseLinks: JhiParseLinks,
        protected router: Router,
        protected jhiAlertService: JhiAlertService,
        protected principal: Principal,
        protected activatedRoute: ActivatedRoute,
        protected eventManager: JhiEventManager
    ) {
        this.setRouteData();
        this.itemsPerPage = ITEMS_PER_PAGE;
    }

    private setRouteData(): void {
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadPage(page: number) {
        const previusPageNotEq = _.negate(R.equals(this.previousPage));
        const changePage = pg => {
            this.previousPage = pg;
            this.transition();
        };
        R.when(previusPageNotEq, changePage)(page);
    }

    protected encontrouResultados(): boolean {
        return !_.isEmpty(this.listResultQuery);
    }

    protected setPageDefault(): void {
        this.page = 1;
        this.setPredicateDefault();
    }

    protected setPredicateDefault(): void {
        this.predicate = 'id';
    }

    private getSort(): any {
        const order = this.reverse ? 'asc' : 'desc';
        const result = [this.predicate + ',' + order];

        if (!_.isEqual(this.predicate, 'id')) {
            result.push('id');
        }

        return result;
    }

    protected getPageable(): any {
        return {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.getSort()
        };
    }

    protected onQuerySuccess() {
        return (response: HttpResponse<T[]>) => {
            this.hasMadeQuery = true;
            const headers = response.headers;
            this.links = this.parseLinks.parse(headers.get('link'));
            this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
            this.queryCount = this.totalItems;
            this.listResultQuery = response.body;
        };
    }

    protected onQueryError() {
        return (res: HttpErrorResponse) => this.onError(res.message);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    protected getNavigationExtras() {
        const order = this.reverse ? 'asc' : 'desc';
        return {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + order
            }
        };
    }

    protected basicTransition(route: string) {
        const navigationExtras = this.getNavigationExtras();
        this.router.navigate([route], navigationExtras);
        this.query();
    }

    protected setCurrentAccount(): void {
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    protected registerChangeInEntidades(listModificationName: string) {
        const onSuccessQuery = response => this.query();
        this.eventSubscriber = this.eventManager.subscribe(listModificationName, onSuccessQuery);
    }

    protected clear(path: string) {
        this.listResultQuery = null;
        this.setPageDefault();
        this.hasMadeQuery = false;
        this.router.navigate([path]);
    }

    protected abstract sanitizeInputValues(): void;

    protected abstract query(): void;

    abstract transition(): void;
}
