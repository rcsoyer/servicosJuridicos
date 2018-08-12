import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Assunto } from 'app/shared/model/assunto.model';
import { AssuntoService } from './assunto.service';
import { AssuntoComponent } from './assunto.component';
import { AssuntoDetailComponent } from './assunto-detail.component';
import { AssuntoUpdateComponent } from './assunto-update.component';
import { AssuntoDeletePopupComponent } from './assunto-delete-dialog.component';
import { IAssunto } from 'app/shared/model/assunto.model';

@Injectable({ providedIn: 'root' })
export class AssuntoResolve implements Resolve<IAssunto> {
    constructor(private service: AssuntoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((assunto: HttpResponse<Assunto>) => assunto.body));
        }
        return of(new Assunto());
    }
}

export const assuntoRoute: Routes = [
    {
        path: 'assunto',
        component: AssuntoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'servicosJuridicosApp.assunto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'assunto/:id/view',
        component: AssuntoDetailComponent,
        resolve: {
            assunto: AssuntoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.assunto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'assunto/new',
        component: AssuntoUpdateComponent,
        resolve: {
            assunto: AssuntoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.assunto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'assunto/:id/edit',
        component: AssuntoUpdateComponent,
        resolve: {
            assunto: AssuntoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.assunto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assuntoPopupRoute: Routes = [
    {
        path: 'assunto/:id/delete',
        component: AssuntoDeletePopupComponent,
        resolve: {
            assunto: AssuntoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.assunto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
