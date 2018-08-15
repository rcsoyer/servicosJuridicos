import {
    ReactiveFormsModule,
    NG_VALIDATORS,
    FormsModule,
    FormGroup,
    FormControl,
    ValidatorFn,
    Validator
} from '@angular/forms';
import * as _ from 'lodash';
import { Directive } from '@angular/core';
import { CpfMaskUtils } from './cpf-mask-utils';

@Directive({
    selector: '[cpfValidator][ngModel]',
    providers: [
        {
            provide: NG_VALIDATORS,
            useExisting: CpfValidator,
            multi: true
        }
    ]
})
export class CpfValidator implements Validator {
    validator: ValidatorFn;

    constructor(private cpfMaskUtils: CpfMaskUtils) {
        this.validator = this.cpfValidator();
    }

    validate(cpf: FormControl) {
        return this.validator(cpf);
    }

    private cpfValidator(): ValidatorFn {
        return (cpf: FormControl) => {
            const qtdDigitos = this.cpfMaskUtils.removeMask(cpf.value).length;
            const isVazio = _.isEqual(qtdDigitos, 0);
            const isCPFOk = _.isEqual(qtdDigitos, 11);

            if (isVazio || isCPFOk) {
                return null;
            } else {
                return {
                    cpfValidator: {
                        valid: false
                    }
                };
            }
        };
    }
}
