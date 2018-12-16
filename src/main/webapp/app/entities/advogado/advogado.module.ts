import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ServicosJuridicosSharedModule} from 'app/shared';
import {CpfValidatorModule} from '../../shared/util/cpf/cpf.module';
import {WhitespaceModule} from '../../shared/util/whitespace-validator/whitespace.validator.module';
import {TextMaskModule} from 'angular2-text-mask';
import {
    AdvogadoComponent,
    AdvogadoDeleteDialogComponent,
    AdvogadoDeletePopupComponent,
    AdvogadoDetailComponent,
    advogadoPopupRoute,
    advogadoRoute,
    AdvogadoUpdateComponent
} from './';
import {AdvogadoUtils} from 'app/entities/advogado/advogado-utils';

const ENTITY_STATES = [...advogadoRoute, ...advogadoPopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES),
        CpfValidatorModule, WhitespaceModule, TextMaskModule],
    declarations: [
        AdvogadoComponent,
        AdvogadoDetailComponent,
        AdvogadoUpdateComponent,
        AdvogadoDeleteDialogComponent,
        AdvogadoDeletePopupComponent
    ],
    entryComponents: [AdvogadoComponent, AdvogadoUpdateComponent, AdvogadoDeleteDialogComponent, AdvogadoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [AdvogadoUtils]
})
export class ServicosJuridicosAdvogadoModule {
}
