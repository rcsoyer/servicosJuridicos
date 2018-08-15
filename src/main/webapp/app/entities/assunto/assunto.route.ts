import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserRouteAccessService } from '../../core';
import { Assunto } from '../../shared/model/assunto.model';
import { AssuntoDeletePopupComponent } from './assunto-delete-dialog.component';
import { AssuntoDetailComponent } from './assunto-detail.component';
import { AssuntoUpdateComponent } from './assunto-update.component';
import { AssuntoComponent } from './assunto.component';
import { AssuntoService } from './assunto.service';

@Injectable({ providedIn: 'root' })
export class AssuntoResolve implements Resolve<Assunto> {
    constructor(private service: AssuntoService) { }

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
