import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {Advogado} from 'app/shared/model/advogado.model';
import {Principal} from 'app/core';
import {AdvogadoService} from './advogado.service';
import {ComponentAbstract} from '../../shared/components-abstract/component.abstract';
import {ADVOGADO_LIST_MODIFICATION} from 'app/entities/advogado/advogado.constants';
import * as _ from 'lodash';
import * as R from 'ramda';
import {AdvogadoUtils} from 'app/entities/advogado/advogado-utils';

@Component({
    selector: 'advogado-component',
    templateUrl: './advogado.component.html'
})
export class AdvogadoComponent extends ComponentAbstract<Advogado> implements OnInit {

    private readonly path = '/advogado';

    constructor(
        advogadoService: AdvogadoService,
        principal: Principal,
        activatedRoute: ActivatedRoute,
        router: Router,
        eventManager: JhiEventManager,
        parseLinks: JhiParseLinks,
        jhiAlertService: JhiAlertService,
        private advogadoUtils: AdvogadoUtils
    ) {
        super(advogadoService, parseLinks, router, jhiAlertService, principal,
            activatedRoute, eventManager);
    }

    ngOnInit() {
        super.onInit();
        this.registerChangeInAdvogados();
    }

    transition(): void {
        super.basicTransition(this.path);
    }

    clear() {
        super.clear(this.path);
    }

    private registerChangeInAdvogados() {
        this.registerChangeInEntidades(ADVOGADO_LIST_MODIFICATION);
    }

    protected createModelConsulta(): void {
        this.modelConsulta = new Advogado();
    }

    protected sanitizeInputValues(): void {
        this.modelConsulta.nome = _.trim(this.modelConsulta.nome);
        const setNomeNull = () => (this.modelConsulta.nome = null);
        R.when(_.isEmpty, setNomeNull)(this.modelConsulta.nome);
    }
}
