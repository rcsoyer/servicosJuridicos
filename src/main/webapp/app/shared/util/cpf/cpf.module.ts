import { NgModule } from '@angular/core';

import { CpfValidator } from './cpf-validator';
import { CpfMaskUtils } from './cpf-mask-utils';

@NgModule({
    declarations: [CpfValidator],
    exports: [CpfValidator],
    providers: [CpfMaskUtils]
})
export class CpfValidatorModule {}
