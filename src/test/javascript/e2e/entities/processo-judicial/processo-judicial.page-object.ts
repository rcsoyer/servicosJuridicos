import { element, by, promise, ElementFinder } from 'protractor';

export class ProcessoJudicialComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-processo-judicial div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProcessoJudicialUpdatePage {
    pageTitle = element(by.id('jhi-processo-judicial-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    numeroInput = element(by.id('field_numero'));
    prazoFinalInput = element(by.id('field_prazoFinal'));
    dtAtribuicaoInput = element(by.id('field_dtAtribuicao'));
    dtInicioInput = element(by.id('field_dtInicio'));
    dtConclusaoInput = element(by.id('field_dtConclusao'));
    assuntoSelect = element(by.id('field_assunto'));
    modalidadeSelect = element(by.id('field_modalidade'));
    advogadoSelect = element(by.id('field_advogado'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNumeroInput(numero): promise.Promise<void> {
        return this.numeroInput.sendKeys(numero);
    }

    getNumeroInput() {
        return this.numeroInput.getAttribute('value');
    }

    setPrazoFinalInput(prazoFinal): promise.Promise<void> {
        return this.prazoFinalInput.sendKeys(prazoFinal);
    }

    getPrazoFinalInput() {
        return this.prazoFinalInput.getAttribute('value');
    }

    setDtAtribuicaoInput(dtAtribuicao): promise.Promise<void> {
        return this.dtAtribuicaoInput.sendKeys(dtAtribuicao);
    }

    getDtAtribuicaoInput() {
        return this.dtAtribuicaoInput.getAttribute('value');
    }

    setDtInicioInput(dtInicio): promise.Promise<void> {
        return this.dtInicioInput.sendKeys(dtInicio);
    }

    getDtInicioInput() {
        return this.dtInicioInput.getAttribute('value');
    }

    setDtConclusaoInput(dtConclusao): promise.Promise<void> {
        return this.dtConclusaoInput.sendKeys(dtConclusao);
    }

    getDtConclusaoInput() {
        return this.dtConclusaoInput.getAttribute('value');
    }

    assuntoSelectLastOption(): promise.Promise<void> {
        return this.assuntoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    assuntoSelectOption(option): promise.Promise<void> {
        return this.assuntoSelect.sendKeys(option);
    }

    getAssuntoSelect(): ElementFinder {
        return this.assuntoSelect;
    }

    getAssuntoSelectedOption() {
        return this.assuntoSelect.element(by.css('option:checked')).getText();
    }

    modalidadeSelectLastOption(): promise.Promise<void> {
        return this.modalidadeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    modalidadeSelectOption(option): promise.Promise<void> {
        return this.modalidadeSelect.sendKeys(option);
    }

    getModalidadeSelect(): ElementFinder {
        return this.modalidadeSelect;
    }

    getModalidadeSelectedOption() {
        return this.modalidadeSelect.element(by.css('option:checked')).getText();
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
