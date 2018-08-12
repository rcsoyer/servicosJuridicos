import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServicosJuridicosSharedModule } from 'app/shared';
import {
    ProcessoJudicialComponent,
    ProcessoJudicialDetailComponent,
    ProcessoJudicialUpdateComponent,
    ProcessoJudicialDeletePopupComponent,
    ProcessoJudicialDeleteDialogComponent,
    processoJudicialRoute,
    processoJudicialPopupRoute
} from './';

const ENTITY_STATES = [...processoJudicialRoute, ...processoJudicialPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProcessoJudicialComponent,
        ProcessoJudicialDetailComponent,
        ProcessoJudicialUpdateComponent,
        ProcessoJudicialDeleteDialogComponent,
        ProcessoJudicialDeletePopupComponent
    ],
    entryComponents: [
        ProcessoJudicialComponent,
        ProcessoJudicialUpdateComponent,
        ProcessoJudicialDeleteDialogComponent,
        ProcessoJudicialDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosProcessoJudicialModule {}
