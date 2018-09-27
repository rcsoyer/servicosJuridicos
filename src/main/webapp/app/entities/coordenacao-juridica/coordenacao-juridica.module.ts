import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MultiselectDropdownModule} from 'angular-2-dropdown-multiselect';
import {TextMaskModule} from 'angular2-text-mask';
import {ServicosJuridicosSharedModule} from '../../shared';
import {MultiSelectModule} from '../../shared/util/multiselect/multiselect.module';
import {WhitespaceModule} from '../../shared/util/whitespace-validator/whitespace.validator.module';
import {
    CoordenacaoJuridicaComponent,
    CoordenacaoJuridicaDeleteDialogComponent,
    CoordenacaoJuridicaDeletePopupComponent,
    CoordenacaoJuridicaDetailComponent,
    coordenacaoJuridicaPopupRoute,
    coordenacaoJuridicaRoute,
    CoordenacaoJuridicaUpdateComponent
} from './';

const ENTITY_STATES = [...coordenacaoJuridicaRoute, ...coordenacaoJuridicaPopupRoute];

@NgModule({
    imports: [
        ServicosJuridicosSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        WhitespaceModule,
        MultiSelectModule,
        TextMaskModule,
        MultiselectDropdownModule
    ],
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
export class ServicosJuridicosCoordenacaoJuridicaModule {
}
