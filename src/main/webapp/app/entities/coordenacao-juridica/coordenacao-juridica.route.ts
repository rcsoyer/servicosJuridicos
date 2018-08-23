import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from '../../core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CoordenacaoJuridica } from '../../shared/model/coordenacao-juridica.model';
import { CoordenacaoJuridicaService } from './coordenacao-juridica.service';
import { CoordenacaoJuridicaComponent } from './coordenacao-juridica.component';
import { CoordenacaoJuridicaDetailComponent } from './coordenacao-juridica-detail.component';
import { CoordenacaoJuridicaUpdateComponent } from './coordenacao-juridica-update.component';
import { CoordenacaoJuridicaDeletePopupComponent } from './coordenacao-juridica-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CoordenacaoJuridicaResolve implements Resolve<CoordenacaoJuridica> {
    constructor(private service: CoordenacaoJuridicaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((coordenacaoJuridica: HttpResponse<CoordenacaoJuridica>) => coordenacaoJuridica.body));
        }
        return of(new CoordenacaoJuridica());
    }
}

export const coordenacaoJuridicaRoute: Routes = [
    {
        path: 'coordenacao-juridica',
        component: CoordenacaoJuridicaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'servicosJuridicosApp.coordenacaoJuridica.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'coordenacao-juridica/:id/view',
        component: CoordenacaoJuridicaDetailComponent,
        resolve: {
            coordenacaoJuridica: CoordenacaoJuridicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.coordenacaoJuridica.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'coordenacao-juridica/new',
        component: CoordenacaoJuridicaUpdateComponent,
        resolve: {
            model: CoordenacaoJuridicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.coordenacaoJuridica.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'coordenacao-juridica/:id/edit',
        component: CoordenacaoJuridicaUpdateComponent,
        resolve: {
            model: CoordenacaoJuridicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.coordenacaoJuridica.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coordenacaoJuridicaPopupRoute: Routes = [
    {
        path: 'coordenacao-juridica/:id/delete',
        component: CoordenacaoJuridicaDeletePopupComponent,
        resolve: {
            coordenacaoJuridica: CoordenacaoJuridicaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.coordenacaoJuridica.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
