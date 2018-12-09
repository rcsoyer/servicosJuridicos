import { element, by, promise, ElementFinder } from 'protractor';

export class AdvogadoDgCoordenacaoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-advogado-dg-coordenacao div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AdvogadoDgCoordenacaoUpdatePage {
    pageTitle = element(by.id('jhi-advogado-dg-coordenacao-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dgPessoalInicioInput = element(by.id('field_dgPessoalInicio'));
    dgPessoalFimInput = element(by.id('field_dgPessoalFim'));
    dgDuplaInput = element(by.id('field_dgDupla'));
    rangeDgCoordenacaoSelect = element(by.id('field_rangeDgCoordenacao'));
    advogadoSelect = element(by.id('field_advogado'));
    coordenacaoSelect = element(by.id('field_coordenacao'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDgPessoalInicioInput(dgPessoalInicio): promise.Promise<void> {
        return this.dgPessoalInicioInput.sendKeys(dgPessoalInicio);
    }

    getDgPessoalInicioInput() {
        return this.dgPessoalInicioInput.getAttribute('value');
    }

    setDgPessoalFimInput(dgPessoalFim): promise.Promise<void> {
        return this.dgPessoalFimInput.sendKeys(dgPessoalFim);
    }

    getDgPessoalFimInput() {
        return this.dgPessoalFimInput.getAttribute('value');
    }

    setDgDuplaInput(dgDupla): promise.Promise<void> {
        return this.dgDuplaInput.sendKeys(dgDupla);
    }

    getDgDuplaInput() {
        return this.dgDuplaInput.getAttribute('value');
    }

    setRangeDgCoordenacaoSelect(rangeDgCoordenacao): promise.Promise<void> {
        return this.rangeDgCoordenacaoSelect.sendKeys(rangeDgCoordenacao);
    }

    getRangeDgCoordenacaoSelect() {
        return this.rangeDgCoordenacaoSelect.element(by.css('option:checked')).getText();
    }

    rangeDgCoordenacaoSelectLastOption(): promise.Promise<void> {
        return this.rangeDgCoordenacaoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    advogadoSelectLastOption(): promise.Promise<void> {
        return this.advogadoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    advogadoSelectOption(option): promise.Promise<void> {
        return this.advogadoSelect.sendKeys(option);
    }

    getAdvogadoSelect(): ElementFinder {
        return this.advogadoSelect;
    }

    getAdvogadoSelectedOption() {
        return this.advogadoSelect.element(by.css('option:checked')).getText();
    }

    coordenacaoSelectLastOption(): promise.Promise<void> {
        return this.coordenacaoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    coordenacaoSelectOption(option): promise.Promise<void> {
        return this.coordenacaoSelect.sendKeys(option);
    }

    getCoordenacaoSelect(): ElementFinder {
        return this.coordenacaoSelect;
    }

    getCoordenacaoSelectedOption() {
        return this.coordenacaoSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
