import { Injectable } from '@angular/core';
import {
    IMultiSelectSettings,
    IMultiSelectTexts
} from 'angular-2-dropdown-multiselect';
import { OptionsTexts } from './options.texts';

@Injectable()
export class MultiSelectSettings {
    private optionsSettings: IMultiSelectSettings;

    constructor(private optionsTexts: OptionsTexts) {
        this.definirOptionsSettings();
    }

    private definirOptionsSettings() {
        this.optionsSettings = {
            enableSearch: true,
            dynamicTitleMaxItems: 50,
            displayAllSelectedText: true,
            showCheckAll: true,
            showUncheckAll: true
        };
    }

    getSettings(): IMultiSelectSettings {
        return this.optionsSettings;
    }

    getTexts(): IMultiSelectTexts {
        return this.optionsTexts.getTexts();
    }
}
