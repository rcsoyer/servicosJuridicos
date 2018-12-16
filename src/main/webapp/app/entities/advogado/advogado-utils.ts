import createNumberMask from 'text-mask-addons/dist/createNumberMask';
import {CpfMaskUtils} from 'app/shared/util/cpf/cpf-mask-utils';
import {Injectable} from '@angular/core';

@Injectable()
export class AdvogadoUtils {

    constructor(private cpfMaskUtils: CpfMaskUtils) {
    }

    maskRamal(): Function {
        return createNumberMask({
            prefix: '',
            decimalSymbol: '',
            integerLimit: 9,
            thousandsSeparatorSymbol: ''
        });
    }

    maskCPF(): Object {
        return this.cpfMaskUtils.maskCPF();
    }
}
