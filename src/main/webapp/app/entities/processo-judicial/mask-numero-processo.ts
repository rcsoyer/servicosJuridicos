import createNumberMask from 'text-mask-addons/dist/createNumberMask';

export function maskNumeroProcesso() {

    return createNumberMask({
        prefix: '',
        decimalSymbol: '',
        integerLimit: 20,
        thousandsSeparatorSymbol: ''
    });
}
