import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiResolvePagingParams} from 'ng-jhipster';
import {UserRouteAccessService} from 'app/core';
import {of} from 'rxjs';
import {map} from 'rxjs/operators';
import {FeriasLicencaService} from './ferias-licenca.service';
import {FeriasLicencaComponent} from './ferias-licenca.component';
import {FeriasLicencaDetailComponent} from './ferias-licenca-detail.component';
import {FeriasLicencaUpdateComponent} from './ferias-licenca-update.component';
import {FeriasLicencaDeletePopupComponent} from './ferias-licenca-delete-dialog.component';
import {FeriasLicenca} from 'app/shared/model/ferias-licenca.model';

@Injectable({providedIn: 'root'})
export class FeriasLicencaResolve implements Resolve<FeriasLicenca> {

    constructor(private service: FeriasLicencaService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((feriasLicenca: HttpResponse<FeriasLicenca>) => feriasLicenca.body));
        }
        return of(new FeriasLicenca());
    }
}

export const feriasLicencaRoute: Routes = [
    {
        path: 'ferias-licenca',
        component: FeriasLicencaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'servicosJuridicosApp.feriasLicenca.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ferias-licenca/:id/view',
        component: FeriasLicencaDetailComponent,
        resolve: {
            feriasLicenca: FeriasLicencaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.feriasLicenca.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ferias-licenca/new',
        component: FeriasLicencaUpdateComponent,
        resolve: {
            feriasLicenca: FeriasLicencaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.feriasLicenca.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ferias-licenca/:id/edit',
        component: FeriasLicencaUpdateComponent,
        resolve: {
            feriasLicenca: FeriasLicencaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.feriasLicenca.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const feriasLicencaPopupRoute: Routes = [
    {
        path: 'ferias-licenca/:id/delete',
        component: FeriasLicencaDeletePopupComponent,
        resolve: {
            feriasLicenca: FeriasLicencaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.feriasLicenca.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
