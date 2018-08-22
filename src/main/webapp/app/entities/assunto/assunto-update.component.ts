import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as _ from 'lodash';
import { UpdateComponentAbastract } from '../../shared/components-abstract/update.component.abstract';
import { Assunto } from '../../shared/model/assunto.model';
import { AssuntoService } from './assunto.service';

@Component({ selector: 'assunto-update', templateUrl: './assunto-update.component.html' })
export class AssuntoUpdateComponent extends UpdateComponentAbastract<Assunto> implements OnInit {
    constructor(assuntoService: AssuntoService, activatedRoute: ActivatedRoute) {
        super(assuntoService, activatedRoute);
    }

    ngOnInit() {
        this.onInit();
        this.defineTituloPagina('Assunto');
    }

    createPesos(): number[] {
        return _.range(1, 6);
    }

    protected trimInputText() {
        this.model.descricao = this.model.descricao.trim();
    }
}
