import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import * as _ from 'lodash';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import * as R from 'ramda';
import {Principal} from '../../core';
import {ComponentAbstract} from '../../shared/components-abstract/component.abstract';
import {Assunto} from '../../shared/model/assunto.model';
import {AssuntoUtils} from './assunto-utils';
import {ASSUNTO_LIST_MODIFICATION} from './assunto.constants';
import {AssuntoService} from './assunto.service';

@Component({selector: 'assunto-component', templateUrl: './assunto.component.html'})
export class AssuntoComponent extends ComponentAbstract<Assunto> implements OnInit {

    private readonly path = '/assunto';

    constructor(
        assuntoService: AssuntoService,
        principal: Principal,
        activatedRoute: ActivatedRoute,
        router: Router,
        eventManager: JhiEventManager,
        parseLinks: JhiParseLinks,
        jhiAlertService: JhiAlertService,
        public assuntoUtils: AssuntoUtils
    ) {
        super(assuntoService, parseLinks, router, jhiAlertService, principal,
            activatedRoute, eventManager);
    }

    ngOnInit() {
        super.onInit();
        this.registerChangeInAssuntos();
    }

    transition(): void {
        super.basicTransition(this.path);
    }

    clear() {
        super.clear(this.path);
    }

    createPesos(): number[] {
        return _.range(1, 6);
    }

    private registerChangeInAssuntos() {
        this.registerChangeInEntidades(ASSUNTO_LIST_MODIFICATION);
    }

    protected createModelConsulta(): void {
        this.modelConsulta = new Assunto();
        this.modelConsulta.ativo = undefined;
    }

    protected sanitizeInputValues(): void {
        this.modelConsulta.descricao = _.trim(this.modelConsulta.descricao);
        const setDescricacaoNull = () => (this.modelConsulta.descricao = null);
        R.when(_.isEmpty, setDescricacaoNull)(this.modelConsulta.descricao);
    }
}
