import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Modalidade } from 'app/shared/model/modalidade.model';
import { ModalidadeService } from './modalidade.service';
import { ModalidadeComponent } from './modalidade.component';
import { ModalidadeDetailComponent } from './modalidade-detail.component';
import { ModalidadeUpdateComponent } from './modalidade-update.component';
import { ModalidadeDeletePopupComponent } from './modalidade-delete-dialog.component';
import { IModalidade } from 'app/shared/model/modalidade.model';

@Injectable({ providedIn: 'root' })
export class ModalidadeResolve implements Resolve<IModalidade> {
    constructor(private service: ModalidadeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((modalidade: HttpResponse<Modalidade>) => modalidade.body));
        }
        return of(new Modalidade());
    }
}

export const modalidadeRoute: Routes = [
    {
        path: 'modalidade',
        component: ModalidadeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'servicosJuridicosApp.modalidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'modalidade/:id/view',
        component: ModalidadeDetailComponent,
        resolve: {
            modalidade: ModalidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.modalidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'modalidade/new',
        component: ModalidadeUpdateComponent,
        resolve: {
            modalidade: ModalidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.modalidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'modalidade/:id/edit',
        component: ModalidadeUpdateComponent,
        resolve: {
            modalidade: ModalidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.modalidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modalidadePopupRoute: Routes = [
    {
        path: 'modalidade/:id/delete',
        component: ModalidadeDeletePopupComponent,
        resolve: {
            modalidade: ModalidadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.modalidade.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
