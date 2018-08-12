import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServicosJuridicosSharedModule } from 'app/shared';
import {
    AssuntoComponent,
    AssuntoDetailComponent,
    AssuntoUpdateComponent,
    AssuntoDeletePopupComponent,
    AssuntoDeleteDialogComponent,
    assuntoRoute,
    assuntoPopupRoute
} from './';

const ENTITY_STATES = [...assuntoRoute, ...assuntoPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AssuntoComponent,
        AssuntoDetailComponent,
        AssuntoUpdateComponent,
        AssuntoDeleteDialogComponent,
        AssuntoDeletePopupComponent
    ],
    entryComponents: [AssuntoComponent, AssuntoUpdateComponent, AssuntoDeleteDialogComponent, AssuntoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosAssuntoModule {}
