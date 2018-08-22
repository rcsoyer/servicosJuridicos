import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UpdateComponentAbastract } from '../../shared/components-abstract/update.component.abstract';
import { Modalidade } from '../../shared/model/modalidade.model';
import { ModalidadeService } from './modalidade.service';

@Component({ selector: 'modalidade-update', templateUrl: './modalidade-update.component.html' })
export class ModalidadeUpdateComponent extends UpdateComponentAbastract<Modalidade> implements OnInit {
    constructor(modalidadeService: ModalidadeService, activatedRoute: ActivatedRoute) {
        super(modalidadeService, activatedRoute);
    }

    ngOnInit() {
        this.onInit();
        this.defineTituloPagina('Modalidade');
    }

    protected trimInputText(): void {
        this.model.descricao = this.model.descricao.trim();
    }
}
