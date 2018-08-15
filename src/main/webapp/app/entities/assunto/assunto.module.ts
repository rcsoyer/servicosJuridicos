import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ServicosJuridicosSharedModule } from '../../shared';
import {
    AssuntoComponent,
    AssuntoDetailComponent,
    AssuntoUpdateComponent,
    AssuntoDeletePopupComponent,
    AssuntoDeleteDialogComponent,
    assuntoRoute,
    assuntoPopupRoute,
    AssuntoUtils
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
    providers: [AssuntoUtils],
    entryComponents: [AssuntoComponent, AssuntoUpdateComponent, AssuntoDeleteDialogComponent, AssuntoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosAssuntoModule { }
