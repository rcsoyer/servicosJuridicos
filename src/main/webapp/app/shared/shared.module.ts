import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {NgbDateAdapter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateMomentAdapter} from './util/datepicker-adapter';
import {
    HasAnyAuthorityDirective,
    JhiLoginModalComponent,
    ServicosJuridicosSharedCommonModule,
    ServicosJuridicosSharedLibsModule
} from '.';

@NgModule({
    imports: [ServicosJuridicosSharedLibsModule, ServicosJuridicosSharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{provide: NgbDateAdapter, useClass: NgbDateMomentAdapter}],
    entryComponents: [JhiLoginModalComponent],
    exports: [ServicosJuridicosSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosSharedModule {
}
