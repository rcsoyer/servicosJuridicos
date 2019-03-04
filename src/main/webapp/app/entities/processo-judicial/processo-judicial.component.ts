import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import {ProcessoJudicial} from 'app/shared/model/processo-judicial.model';
import {Principal} from 'app/core';
import {ProcessoJudicialService} from './processo-judicial.service';
import {ComponentAbstract} from 'app/shared/components-abstract/component.abstract';
import {PROCESSO_JUDICIAL_LIST_MODIFICATION} from 'app/entities/processo-judicial/processo-judicial.constants';
import {maskNumeroProcesso} from 'app/entities/processo-judicial/mask-numero-processo';

@Component({
    selector: 'processo-judicial',
    templateUrl: './processo-judicial.component.html'
})
export class ProcessoJudicialComponent extends ComponentAbstract<ProcessoJudicial> implements OnInit {

    private readonly path = '/processo-judicial';

    constructor(
        processoJudicialService: ProcessoJudicialService,
        principal: Principal,
        activatedRoute: ActivatedRoute,
        router: Router,
        eventManager: JhiEventManager,
        parseLinks: JhiParseLinks,
        jhiAlertService: JhiAlertService
    ) {
        super(processoJudicialService, parseLinks, router, jhiAlertService, principal,
            activatedRoute, eventManager);
    }

    ngOnInit(): void {
        super.onInit();
        this.registerChangeInProcessosJudiciais();
    }

    transition(): void {
        super.basicTransition(this.path);
    }

    clear() {
        super.clear(this.path);
    }

    maskProcessoNumero() {
        return maskNumeroProcesso();
    }

    protected createModelConsulta(): void {
        this.modelConsulta = new ProcessoJudicial();
    }

    private registerChangeInProcessosJudiciais() {
        this.registerChangeInEntidades(PROCESSO_JUDICIAL_LIST_MODIFICATION);
    }
}
