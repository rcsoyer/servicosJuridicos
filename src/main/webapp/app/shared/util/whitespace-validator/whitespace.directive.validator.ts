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
    selector: '[whitespaceValidator][ngModel]',
    providers: [
        {
            provide: NG_VALIDATORS,
            useExisting: WhitespaceValidator,
            multi: true
        }
    ]
})
export class WhitespaceValidator implements Validator {
    private validator: ValidatorFn;

    constructor() {
        this.validator = this.whitespaceValidator();
    }

    validate(c: FormControl) {
        return this.validator(c);
    }

    private whitespaceValidator(): ValidatorFn {
        return (c: FormControl) => {
            const value = c.value;
            const notValid =
                _.isString(value) && value.length && _.isEmpty(_.trim(value));

            if (notValid) {
                return {
                    whitespaceValidator: {
                        valid: false
                    }
                };
            }

            return null;
        };
    }
}
