import { Injectable } from '@angular/core';
import { IMultiSelectTexts } from 'angular-2-dropdown-multiselect';

@Injectable()
export class OptionsTexts {
    private texts: IMultiSelectTexts;

    constructor() {
        this.defineTexts();
    }

    private defineTexts() {
        this.texts = {
            checkAll: 'Marcar todos',
            uncheckAll: 'Desmarcar todos',
            checkedPlural: 'itens selecionados',
            searchPlaceholder: 'Pesquisar',
            searchEmptyResult: 'Nada encontrado...',
            defaultTitle: 'Selecione',
            allSelected: 'Todos selecionados'
        };
    }

    getTexts(): IMultiSelectTexts {
        return this.texts;
    }
}
