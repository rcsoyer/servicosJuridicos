import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServicosJuridicosSharedModule } from 'app/shared';
import {
    AdvogadoDgCoordenacaoComponent,
    AdvogadoDgCoordenacaoDetailComponent,
    AdvogadoDgCoordenacaoUpdateComponent,
    AdvogadoDgCoordenacaoDeletePopupComponent,
    AdvogadoDgCoordenacaoDeleteDialogComponent,
    advogadoDgCoordenacaoRoute,
    advogadoDgCoordenacaoPopupRoute
} from './';

const ENTITY_STATES = [...advogadoDgCoordenacaoRoute, ...advogadoDgCoordenacaoPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdvogadoDgCoordenacaoComponent,
        AdvogadoDgCoordenacaoDetailComponent,
        AdvogadoDgCoordenacaoUpdateComponent,
        AdvogadoDgCoordenacaoDeleteDialogComponent,
        AdvogadoDgCoordenacaoDeletePopupComponent
    ],
    entryComponents: [
        AdvogadoDgCoordenacaoComponent,
        AdvogadoDgCoordenacaoUpdateComponent,
        AdvogadoDgCoordenacaoDeleteDialogComponent,
        AdvogadoDgCoordenacaoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosAdvogadoDgCoordenacaoModule {}
