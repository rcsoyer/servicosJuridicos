import {AssuntosOptions} from 'app/entities/coordenacao-juridica/assuntos-options';
import {Component, Input, OnInit} from '@angular/core';
import {MultiSelectSettings} from 'app/shared/util/multiselect/multiselect.settings';
import {IMultiSelectSettings, IMultiSelectTexts} from 'angular-2-dropdown-multiselect';

/**
 * Não funciona
 */
@Component({
    selector: 'assuntos-multiselect',
    template: `
        <ss-multiselect-dropdown [(ngModel)]="model" class="form-control" id="field_assuntos"
                                 name="assuntos" [options]="assuntoSelectOptions()"
                                 [settings]="optionsSettings" [texts]="optionsTexts">
        </ss-multiselect-dropdown>`
})
export class AssuntosMultiselectComponent implements OnInit {

    private optionsTexts: IMultiSelectTexts;
    private optionsSettings: IMultiSelectSettings;

    nome: any;

    @Input()
    model: any;

    constructor(private assuntosOptions: AssuntosOptions, private multiSelectSettings: MultiSelectSettings) {
    }

    ngOnInit(): void {
        this.setOptionsSettings();
        this.assuntosOptions.queryAllAssuntos();
        // this.model = this.injector.get(NgModel);
    }

    private setOptionsSettings() {
        this.optionsTexts = this.multiSelectSettings.getTexts();
        this.optionsSettings = this.multiSelectSettings.getSettings();
    }

    private assuntoSelectOptions() {
        return this.assuntosOptions.assuntoSelectOptions;
    }
}
