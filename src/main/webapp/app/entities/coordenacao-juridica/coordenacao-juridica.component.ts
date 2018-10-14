import {HttpResponse} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {IMultiSelectOption, IMultiSelectSettings, IMultiSelectTexts} from 'angular-2-dropdown-multiselect';
import * as _ from 'lodash';
import {JhiAlertService, JhiEventManager, JhiParseLinks} from 'ng-jhipster';
import * as R from 'ramda';
import {Principal} from '../../core';
import {ComponentAbstract} from '../../shared/components-abstract/component.abstract';
import {Assunto} from '../../shared/model/assunto.model';
import {CoordenacaoJuridica} from '../../shared/model/coordenacao-juridica.model';
import {MultiSelectSettings} from '../../shared/util/multiselect/multiselect.settings';
import {AssuntoService} from '../assunto';
import {COORDENACAO_JURIDICA_LIST_MODIFICATION} from './coordenacao-juridica.constants';
import {CoordenacaoJuridicaService} from './coordenacao-juridica.service';

@Component({
    selector: 'coordenacao-juridica',
    templateUrl: './coordenacao-juridica.component.html'
})
export class CoordenacaoJuridicaComponent extends ComponentAbstract<CoordenacaoJuridica> implements OnInit {
    private assuntos: Assunto[];
    public assuntosModel: number[];
    public optionsTexts: IMultiSelectTexts;
    public assuntoOptions: IMultiSelectOption[];
    public optionsSettings: IMultiSelectSettings;
    private readonly path = '/coordenacao-juridica';

    constructor(
        coordenacaoJuridicaService: CoordenacaoJuridicaService,
        parseLinks: JhiParseLinks,
        jhiAlertService: JhiAlertService,
        principal: Principal,
        activatedRoute: ActivatedRoute,
        router: Router,
        eventManager: JhiEventManager,
        private multiSelectSettings: MultiSelectSettings,
        private assuntoService: AssuntoService
    ) {
        super(coordenacaoJuridicaService, parseLinks, router, jhiAlertService, principal, activatedRoute, eventManager);
    }

    protected createModelConsulta(): void {
        this.modelConsulta = new CoordenacaoJuridica();
    }

    transition(): void {
        super.basicTransition(this.path);
    }

    clear() {
        this.assuntosModel = null;
        super.clear(this.path);
    }

    ngOnInit() {
        super.onInit();
        this.setOptionsSettings();
        this.queryAllAssuntos();
        this.registerChangeInCoordenacoes();
    }

    private queryAllAssuntos(): void {
        this.assuntoService.query()
                           .subscribe(this.assuntosQueryResponse(), this.onQueryError());
    }

    private assuntosQueryResponse() {
        return (res: HttpResponse<Assunto[]>) => {
            this.assuntos = res.body;
            this.criarAssuntoOptions();
        };
    }

    private criarAssuntoOptions() {
        this.assuntoOptions = [];
        this.assuntos.forEach(assunto => this.assuntoOptions.push({ id: assunto.id, name: assunto.descricao }));
    }

    registerChangeInCoordenacoes() {
        this.registerChangeInEntidades(COORDENACAO_JURIDICA_LIST_MODIFICATION);
    }

    private setOptionsSettings() {
        this.optionsSettings = this.multiSelectSettings.getSettings();
        this.optionsTexts = this.multiSelectSettings.getTexts();
    }

    protected sanitizeInputValues(): void {
        this.modelConsulta.sigla = _.trim(this.modelConsulta.sigla);
        this.modelConsulta.nome = _.trim(this.modelConsulta.nome);
        const setNomeNull = () => (this.modelConsulta.nome = null);
        const setSiglaNull = () => (this.modelConsulta.sigla = null);
        R.when(_.isEmpty, setSiglaNull)(this.modelConsulta.sigla);
        R.when(_.isEmpty, setNomeNull)(this.modelConsulta.nome);
    }

    protected query(): void {
        this.setAssuntosFromModel();
        super.query();
    }

    private setAssuntosFromModel() {
        const setAssuntosNull = () => (this.modelConsulta.assuntos = null);
        const setAssuntosValues = assuntos => {
            const selecionados: Assunto[] = (this.modelConsulta.assuntos = []);
            assuntos.forEach(id => selecionados.push(new Assunto(id)));
        };
        R.ifElse(_.isEmpty, setAssuntosNull, setAssuntosValues)(this.assuntosModel);
    }
}
