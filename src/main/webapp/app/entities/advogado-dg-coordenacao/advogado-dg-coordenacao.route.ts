import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiResolvePagingParams} from 'ng-jhipster';
import {UserRouteAccessService} from 'app/core';
import {of} from 'rxjs';
import {map} from 'rxjs/operators';
import {AdvogadoDgCoordenacaoService} from './advogado-dg-coordenacao.service';
import {AdvogadoDgCoordenacaoComponent} from './advogado-dg-coordenacao.component';
import {AdvogadoDgCoordenacaoDetailComponent} from './advogado-dg-coordenacao-detail.component';
import {AdvogadoDgCoordenacaoUpdateComponent} from './advogado-dg-coordenacao-update.component';
import {AdvogadoDgCoordenacaoDeletePopupComponent} from './advogado-dg-coordenacao-delete-dialog.component';
import {AdvogadoDgCoordenacao} from 'app/shared/model/advogado-dg-coordenacao.model';

@Injectable({providedIn: 'root'})
export class AdvogadoDgCoordenacaoResolve implements Resolve<AdvogadoDgCoordenacao> {
    constructor(private service: AdvogadoDgCoordenacaoService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((advogadoDgCoordenacao: HttpResponse<AdvogadoDgCoordenacao>) => advogadoDgCoordenacao.body));
        }
        return of(new AdvogadoDgCoordenacao());
    }
}

export const advogadoDgCoordenacaoRoute: Routes = [
    {
        path: 'advogado-dg-coordenacao',
        component: AdvogadoDgCoordenacaoComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'servicosJuridicosApp.advogadoDgCoordenacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'advogado-dg-coordenacao/:id/view',
        component: AdvogadoDgCoordenacaoDetailComponent,
        resolve: {
            advogadoDgCoordenacao: AdvogadoDgCoordenacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogadoDgCoordenacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'advogado-dg-coordenacao/new',
        component: AdvogadoDgCoordenacaoUpdateComponent,
        resolve: {
            advogadoDgCoordenacao: AdvogadoDgCoordenacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogadoDgCoordenacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'advogado-dg-coordenacao/:id/edit',
        component: AdvogadoDgCoordenacaoUpdateComponent,
        resolve: {
            advogadoDgCoordenacao: AdvogadoDgCoordenacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogadoDgCoordenacao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const advogadoDgCoordenacaoPopupRoute: Routes = [
    {
        path: 'advogado-dg-coordenacao/:id/delete',
        component: AdvogadoDgCoordenacaoDeletePopupComponent,
        resolve: {
            advogadoDgCoordenacao: AdvogadoDgCoordenacaoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'servicosJuridicosApp.advogadoDgCoordenacao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
