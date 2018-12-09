import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServicosJuridicosSharedModule } from 'app/shared';
import {
    AdvogadoComponent,
    AdvogadoDetailComponent,
    AdvogadoUpdateComponent,
    AdvogadoDeletePopupComponent,
    AdvogadoDeleteDialogComponent,
    advogadoRoute,
    advogadoPopupRoute
} from './';

const ENTITY_STATES = [...advogadoRoute, ...advogadoPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdvogadoComponent,
        AdvogadoDetailComponent,
        AdvogadoUpdateComponent,
        AdvogadoDeleteDialogComponent,
        AdvogadoDeletePopupComponent
    ],
    entryComponents: [AdvogadoComponent, AdvogadoUpdateComponent, AdvogadoDeleteDialogComponent, AdvogadoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosAdvogadoModule {}
