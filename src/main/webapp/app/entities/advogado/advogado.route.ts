import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Advogado } from 'app/shared/model/advogado.model';
import { AdvogadoService } from './advogado.service';
import { AdvogadoComponent } from './advogado.component';
import { AdvogadoDetailComponent } from './advogado-detail.component';
import { AdvogadoUpdateComponent } from './advogado-update.component';
import { AdvogadoDeletePopupComponent } from './advogado-delete-dialog.component';
import { IAdvogado } from 'app/shared/model/advogado.model';

@Injectable({ providedIn: 'root' })
export class AdvogadoResolve implements Resolve<IAdvogado> {
    constructor(private service: AdvogadoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((advogado: HttpResponse<Advogado>) => advogado.body));
        }
        return of(new Advogado());
    }
}

export const advogadoRoute: Routes = [
    {
        path: 'advogado',
        component: AdvogadoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'servicosJuridicosApp.advogado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'advogado/:id/view',
        component: AdvogadoDetailComponent,
        resolve: {
            advogado: AdvogadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'advogado/new',
        component: AdvogadoUpdateComponent,
        resolve: {
            advogado: AdvogadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogado.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'advogado/:id/edit',
        component: AdvogadoUpdateComponent,
        resolve: {
            advogado: AdvogadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogado.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const advogadoPopupRoute: Routes = [
    {
        path: 'advogado/:id/delete',
        component: AdvogadoDeletePopupComponent,
        resolve: {
            advogado: AdvogadoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogado.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
