import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServicosJuridicosSharedModule } from 'app/shared';
import {
    CoordenacaoJuridicaComponent,
    CoordenacaoJuridicaDetailComponent,
    CoordenacaoJuridicaUpdateComponent,
    CoordenacaoJuridicaDeletePopupComponent,
    CoordenacaoJuridicaDeleteDialogComponent,
    coordenacaoJuridicaRoute,
    coordenacaoJuridicaPopupRoute
} from './';

const ENTITY_STATES = [...coordenacaoJuridicaRoute, ...coordenacaoJuridicaPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CoordenacaoJuridicaComponent,
        CoordenacaoJuridicaDetailComponent,
        CoordenacaoJuridicaUpdateComponent,
        CoordenacaoJuridicaDeleteDialogComponent,
        CoordenacaoJuridicaDeletePopupComponent
    ],
    entryComponents: [
        CoordenacaoJuridicaComponent,
        CoordenacaoJuridicaUpdateComponent,
        CoordenacaoJuridicaDeleteDialogComponent,
        CoordenacaoJuridicaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosCoordenacaoJuridicaModule {}
