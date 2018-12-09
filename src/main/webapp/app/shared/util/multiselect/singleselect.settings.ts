import { Injectable } from '@angular/core';
import {
    IMultiSelectSettings,
    IMultiSelectTexts
} from 'angular-2-dropdown-multiselect';
import { OptionsTexts } from './options.texts';

@Injectable()
export class SingleSelectSettings {
    private optionsSettings: IMultiSelectSettings;

    constructor(private optionsTexts: OptionsTexts) {
        this.definirOptionsSettings();
    }

    private definirOptionsSettings() {
        this.optionsSettings = {
            enableSearch: true,
            selectionLimit: 1,
            autoUnselect: true,
            closeOnSelect: true
        };
    }

    getSettings(): IMultiSelectSettings {
        return this.optionsSettings;
    }

    getTexts(): IMultiSelectTexts {
        return this.optionsTexts.getTexts();
    }
}
