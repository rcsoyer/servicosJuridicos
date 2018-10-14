import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiResolvePagingParams} from 'ng-jhipster';
import {UserRouteAccessService} from 'app/core';
import {of} from 'rxjs';
import {map} from 'rxjs/operators';
import {ProcessoJudicialService} from './processo-judicial.service';
import {ProcessoJudicialComponent} from './processo-judicial.component';
import {ProcessoJudicialDetailComponent} from './processo-judicial-detail.component';
import {ProcessoJudicialUpdateComponent} from './processo-judicial-update.component';
import {ProcessoJudicialDeletePopupComponent} from './processo-judicial-delete-dialog.component';
import {ProcessoJudicial} from 'app/shared/model/processo-judicial.model';

@Injectable({providedIn: 'root'})
export class ProcessoJudicialResolve implements Resolve<ProcessoJudicial> {
    constructor(private service: ProcessoJudicialService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((processoJudicial: HttpResponse<ProcessoJudicial>) => processoJudicial.body));
        }
        return of(new ProcessoJudicial());
    }
}

export const processoJudicialRoute: Routes = [
    {
        path: 'processo-judicial',
        component: ProcessoJudicialComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'servicosJuridicosApp.processoJudicial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'processo-judicial/:id/view',
        component: ProcessoJudicialDetailComponent,
        resolve: {
            processoJudicial: ProcessoJudicialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.processoJudicial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'processo-judicial/new',
        component: ProcessoJudicialUpdateComponent,
        resolve: {
            processoJudicial: ProcessoJudicialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.processoJudicial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'processo-judicial/:id/edit',
        component: ProcessoJudicialUpdateComponent,
        resolve: {
            processoJudicial: ProcessoJudicialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.processoJudicial.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const processoJudicialPopupRoute: Routes = [
    {
        path: 'processo-judicial/:id/delete',
        component: ProcessoJudicialDeletePopupComponent,
        resolve: {
            processoJudicial: ProcessoJudicialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.processoJudicial.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
