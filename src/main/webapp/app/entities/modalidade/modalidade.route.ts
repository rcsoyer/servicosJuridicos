import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import * as _ from 'lodash';
import { JhiResolvePagingParams } from 'ng-jhipster';
import * as R from 'ramda';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserRouteAccessService } from '../../core';
import { Modalidade } from '../../shared/model/modalidade.model';
import { ModalidadeDeletePopupComponent } from './modalidade-delete-dialog.component';
import { ModalidadeDetailComponent } from './modalidade-detail.component';
import { ModalidadeUpdateComponent } from './modalidade-update.component';
import { ModalidadeComponent } from './modalidade.component';
import { ModalidadeService } from './modalidade.service';

@Injectable({ providedIn: 'root' })
export class ModalidadeResolve implements Resolve<Modalidade> {
    constructor(private service: ModalidadeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'];
        const findModalidadeById =
            () => this.service.find(id).pipe(map((modalidade: HttpResponse<Modalidade>) => modalidade.body));
        const ofNewModalidade = () => of(new Modalidade());
        return R.ifElse(_.isEmpty, ofNewModalidade, findModalidadeById)(id);
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
            model: ModalidadeResolve
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
            model: ModalidadeResolve
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
