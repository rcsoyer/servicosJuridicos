import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
import * as _ from 'lodash';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import * as R from 'ramda';
import { Principal } from '../../core';
import { ComponentAbstract } from '../../shared/components-abstract/component.abstract';
import { Assunto } from '../../shared/model/assunto.model';
import { AssuntoUtils } from './assunto-utils';
import { ASSUNTO_LIST_MODIFICATION } from './assunto.constants';
import { AssuntoService } from './assunto.service';

@Component({ selector: 'assunto-component', templateUrl: './assunto.component.html' })
export class AssuntoComponent extends ComponentAbstract<Assunto> implements OnInit {
    private readonly path = '/assunto';
    public readonly faMinus = faMinus;

    constructor(
        private assuntoService: AssuntoService,
        protected principal: Principal,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        public assuntoUtils: AssuntoUtils,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService
    ) {
        super(
            parseLinks,
            router,
            jhiAlertService,
            principal,
            activatedRoute,
            eventManager
        );
    }

    private createModelConsulta(): void {
        this.modelConsulta = new Assunto();
        this.modelConsulta.ativo = undefined;
    }

    transition(): void {
        super.basicTransition(this.path);
    }

    protected clear() {
        super.clear(this.path);
    }

    ngOnInit() {
        this.createModelConsulta();
        this.setCurrentAccount();
        this.registerChangeInAssuntos();
    }

    trackId(index: number, item: Assunto) {
        return item.id;
    }

    registerChangeInAssuntos() {
        this.registerChangeInEntidades(ASSUNTO_LIST_MODIFICATION);
    }

    createPesos(): number[] {
        return _.range(1, 6);
    }

    protected query(): void {
        this.sanitizeInputValues();
        this.assuntoService
            .queryByInput(this.modelConsulta, this.getPageable())
            .subscribe(this.onQuerySuccess(), this.onQueryError());
    }

    protected sanitizeInputValues(): void {
        const setDescricacaoNull = () => (this.modelConsulta.descricao = null);
        R.when(_.isEmpty, setDescricacaoNull)(_.trim(this.modelConsulta.descricao));
    }
}
