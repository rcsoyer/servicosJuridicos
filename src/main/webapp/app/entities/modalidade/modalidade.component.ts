import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as _ from 'lodash';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import * as R from 'ramda';
import { Principal } from '../../core';
import { ComponentAbstract } from '../../shared/components-abstract/component.abstract';
import { Modalidade } from '../../shared/model/modalidade.model';
import { MODALIDADE_LIST_MODIFICATION } from './modalidade.constants';
import { ModalidadeService } from './modalidade.service';

@Component({ selector: 'modalidade-component', templateUrl: './modalidade.component.html' })
export class ModalidadeComponent extends ComponentAbstract<Modalidade> implements OnInit {
    private readonly path = '/modalidade';

    constructor(
        modalidadeService: ModalidadeService,
        parseLinks: JhiParseLinks,
        jhiAlertService: JhiAlertService,
        principal: Principal,
        activatedRoute: ActivatedRoute,
        router: Router,
        eventManager: JhiEventManager
    ) {
        super(modalidadeService, parseLinks, router, jhiAlertService, principal, activatedRoute, eventManager);
    }

    transition() {
        super.basicTransition(this.path);
    }

    clear() {
        super.clear(this.path);
    }

    ngOnInit() {
        super.onInit();
        this.registerChangeInModalidades();
    }

    protected createModelConsulta(): void {
        this.modelConsulta = new Modalidade();
    }

    registerChangeInModalidades() {
        this.registerChangeInEntidades(MODALIDADE_LIST_MODIFICATION);
    }

    protected sanitizeInputValues(): void {
        this.modelConsulta.descricao = _.trim(this.modelConsulta.descricao);
        const setDescricacaoNull = () => (this.modelConsulta.descricao = null);
        R.when(_.isEmpty, setDescricacaoNull)(this.modelConsulta.descricao);
    }
}
