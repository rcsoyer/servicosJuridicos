import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Advogado} from 'app/shared/model/advogado.model';
import {AdvogadoService} from './advogado.service';
import {UpdateComponentAbastract} from '../../shared/components-abstract/update.component.abstract';
import {AdvogadoUtils} from 'app/entities/advogado/advogado-utils';

@Component({
    selector: 'advogado-update',
    templateUrl: './advogado-update.component.html'
})
export class AdvogadoUpdateComponent extends UpdateComponentAbastract<Advogado> implements OnInit {

    constructor(advogadoService: AdvogadoService, activatedRoute: ActivatedRoute,
                private advogadoUtils: AdvogadoUtils) {
        super(advogadoService, activatedRoute);
    }

    ngOnInit() {
        this.onInit();
        this.defineTituloPagina('Advogado');
    }
}
