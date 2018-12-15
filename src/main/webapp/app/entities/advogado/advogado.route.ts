import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiResolvePagingParams} from 'ng-jhipster';
import {UserRouteAccessService} from 'app/core';
import {of} from 'rxjs';
import {map} from 'rxjs/operators';
import {AdvogadoService} from './advogado.service';
import {AdvogadoComponent} from './advogado.component';
import {AdvogadoDetailComponent} from './advogado-detail.component';
import {AdvogadoUpdateComponent} from './advogado-update.component';
import {AdvogadoDeletePopupComponent} from './advogado-delete-dialog.component';
import {Advogado} from 'app/shared/model/advogado.model';
import * as R from 'ramda';
import * as _ from 'lodash';

@Injectable({providedIn: 'root'})
export class AdvogadoResolve implements Resolve<Advogado> {

    constructor(private service: AdvogadoService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'];
        const findAdvogadoById = () =>
            this.service.find(id)
            .pipe(map((response: HttpResponse<Advogado>) => response.body));
        const ofNewAdvogado = () => of(new Advogado());
        return R.ifElse(_.isEmpty, ofNewAdvogado, findAdvogadoById)(id);
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
            model: AdvogadoResolve
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
            model: AdvogadoResolve
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
