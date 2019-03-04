import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {WhitespaceModule} from 'app/shared/util/whitespace-validator/whitespace.validator.module';
import {TextMaskModule} from 'angular2-text-mask';
import {ServicosJuridicosSharedModule} from 'app/shared';
import {
    ProcessoJudicialComponent,
    ProcessoJudicialDeleteDialogComponent,
    ProcessoJudicialDeletePopupComponent,
    ProcessoJudicialDetailComponent,
    processoJudicialPopupRoute,
    processoJudicialRoute,
    ProcessoJudicialUpdateComponent
} from './';

const ENTITY_STATES = [...processoJudicialRoute, ...processoJudicialPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES), WhitespaceModule, TextMaskModule],
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
export class ServicosJuridicosProcessoJudicialModule {
}
