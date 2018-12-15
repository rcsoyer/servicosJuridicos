import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Advogado} from 'app/shared/model/advogado.model';
import {AdvogadoService} from './advogado.service';
import {UpdateComponentAbastract} from '../../shared/components-abstract/update.component.abstract';
import {CpfMaskUtils} from 'app/shared/util/cpf/cpf-mask-utils';
import createNumberMask from 'text-mask-addons/dist/createNumberMask';

@Component({
    selector: 'advogado-update',
    templateUrl: './advogado-update.component.html'
})
export class AdvogadoUpdateComponent extends UpdateComponentAbastract<Advogado> implements OnInit {

    constructor(advogadoService: AdvogadoService, activatedRoute: ActivatedRoute,
                 private cpfMaskUtils: CpfMaskUtils) {
        super(advogadoService, activatedRoute);
    }

    ngOnInit() {
        this.onInit();
        this.defineTituloPagina('Advogado');
    }

    maskRamal(): Function {
        return createNumberMask({
            prefix: '',
            decimalSymbol: '',
            integerLimit: 9,
            thousandsSeparatorSymbol: ''
        });
    }
}
