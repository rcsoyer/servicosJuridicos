import {
    ReactiveFormsModule,
    NG_VALIDATORS,
    FormsModule,
    FormGroup,
    FormControl,
    ValidatorFn,
    Validator
} from '@angular/forms';
import { Directive } from '@angular/core';
import * as _ from 'lodash';

@Directive({
    selector: '[multiSelectValidator][ngModel]',
    providers: [
        {
            provide: NG_VALIDATORS,
            useExisting: MultiSelectValidator,
            multi: true
        }
    ]
})
export class MultiSelectValidator implements Validator {
    private validator: ValidatorFn;

    constructor() {
        this.validator = this.multiSelectValidator();
    }

    validate(c: FormControl) {
        return this.validator(c);
    }

    private multiSelectValidator(): ValidatorFn {
        return (c: FormControl) => {
            const isValid = !c.touched || !_.isEmpty(c.value);

            if (isValid) {
                return null;
            } else {
                return {
                    multiSelectValidator: {
                        valid: false
                    }
                };
            }
        };
    }
}
