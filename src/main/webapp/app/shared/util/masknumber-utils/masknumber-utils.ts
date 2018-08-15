import { Injectable } from '@angular/core';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';

@Injectable()
export class MaskNumberUtils {
    maskNumero(digitsLimit: number): Function {
        return createNumberMask({
            prefix: '',
            decimalSymbol: '',
            integerLimit: digitsLimit,
            thousandsSeparatorSymbol: '',
            allowLeadingZeroes: true
        });
    }
}
