import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServicosJuridicosSharedModule } from 'app/shared';
import {
    FeriasLicencaComponent,
    FeriasLicencaDetailComponent,
    FeriasLicencaUpdateComponent,
    FeriasLicencaDeletePopupComponent,
    FeriasLicencaDeleteDialogComponent,
    feriasLicencaRoute,
    feriasLicencaPopupRoute
} from './';

const ENTITY_STATES = [...feriasLicencaRoute, ...feriasLicencaPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FeriasLicencaComponent,
        FeriasLicencaDetailComponent,
        FeriasLicencaUpdateComponent,
        FeriasLicencaDeleteDialogComponent,
        FeriasLicencaDeletePopupComponent
    ],
    entryComponents: [
        FeriasLicencaComponent,
        FeriasLicencaUpdateComponent,
        FeriasLicencaDeleteDialogComponent,
        FeriasLicencaDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosFeriasLicencaModule {}
