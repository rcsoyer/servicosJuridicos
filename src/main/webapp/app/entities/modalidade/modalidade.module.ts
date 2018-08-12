import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ServicosJuridicosSharedModule } from 'app/shared';
import {
    ModalidadeComponent,
    ModalidadeDetailComponent,
    ModalidadeUpdateComponent,
    ModalidadeDeletePopupComponent,
    ModalidadeDeleteDialogComponent,
    modalidadeRoute,
    modalidadePopupRoute
} from './';

const ENTITY_STATES = [...modalidadeRoute, ...modalidadePopupRoute];

@NgModule({
    imports: [ServicosJuridicosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ModalidadeComponent,
        ModalidadeDetailComponent,
        ModalidadeUpdateComponent,
        ModalidadeDeleteDialogComponent,
        ModalidadeDeletePopupComponent
    ],
    entryComponents: [ModalidadeComponent, ModalidadeUpdateComponent, ModalidadeDeleteDialogComponent, ModalidadeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosModalidadeModule {}
