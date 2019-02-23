import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CoordenacaoJuridica} from '../../shared/model/coordenacao-juridica.model';
import {CoordenacaoJuridicaService} from './coordenacao-juridica.service';
import {UpdateComponentAbstract} from '../../shared/components-abstract/update.component.abstract';
import {IMultiSelectSettings, IMultiSelectTexts} from 'angular-2-dropdown-multiselect';
import {MultiSelectSettings} from 'app/shared/util/multiselect/multiselect.settings';
import {JhiAlertService} from 'ng-jhipster';
import {AssuntosOptions} from './assuntos-options';
import {MaskNumberUtils} from '../../shared/util/masknumber-utils/masknumber-utils';
import {Assunto} from 'app/shared/model/assunto.model';

@Component({
    selector: 'coordenacao-juridica-update',
    templateUrl: './coordenacao-juridica-update.component.html'
})
export class CoordenacaoJuridicaUpdateComponent extends UpdateComponentAbstract<CoordenacaoJuridica>
    implements OnInit {

    assuntosModel: number[];
    optionsTexts: IMultiSelectTexts;
    optionsSettings: IMultiSelectSettings;

    constructor(coordenacaoJuridicaService: CoordenacaoJuridicaService,
                activatedRoute: ActivatedRoute,
                private multiSelectSettings: MultiSelectSettings,
                private jhiAlertService: JhiAlertService,
                private assuntosOptions: AssuntosOptions,
                private maskNumberUtils: MaskNumberUtils) {
        super(coordenacaoJuridicaService, activatedRoute);
    }

    ngOnInit() {
        this.onInit();
        this.setOptionsSettings();
        this.defineTituloPagina('Coordenação Jurídica');
        this.assuntosOptions.queryAllAssuntos();
        this.setAssuntosModelQueried();
    }

    getAssuntoSelectOptions() {
        return this.assuntosOptions.assuntoSelectOptions;
    }

    maskNumero(): Function {
        return this.maskNumberUtils.maskNumero(3);
    }

    save() {
        this.setAssuntosFromUserInput();
        super.save();
    }

    private setOptionsSettings() {
        this.optionsTexts = this.multiSelectSettings.getTexts();
        this.optionsSettings = this.multiSelectSettings.getSettings();
    }

    private setAssuntosFromUserInput(): void {
        this.model.assuntos = [];
        this.assuntosModel.forEach(id => this.model.assuntos.push(new Assunto(id)));
    }

    private setAssuntosModelQueried(): void {
        this.assuntosModel = this.model.assuntos.map(assunto => assunto.id);
    }
}
