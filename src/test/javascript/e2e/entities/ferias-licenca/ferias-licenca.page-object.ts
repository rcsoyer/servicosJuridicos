import { element, by, promise, ElementFinder } from 'protractor';

export class FeriasLicencaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-ferias-licenca div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FeriasLicencaUpdatePage {
    pageTitle = element(by.id('jhi-ferias-licenca-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    dtInicioInput = element(by.id('field_dtInicio'));
    dtFimInput = element(by.id('field_dtFim'));
    tipoSelect = element(by.id('field_tipo'));
    advogadoSelect = element(by.id('field_advogado'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDtInicioInput(dtInicio): promise.Promise<void> {
        return this.dtInicioInput.sendKeys(dtInicio);
    }

    getDtInicioInput() {
        return this.dtInicioInput.getAttribute('value');
    }

    setDtFimInput(dtFim): promise.Promise<void> {
        return this.dtFimInput.sendKeys(dtFim);
    }

    getDtFimInput() {
        return this.dtFimInput.getAttribute('value');
    }

    setTipoSelect(tipo): promise.Promise<void> {
        return this.tipoSelect.sendKeys(tipo);
    }

    getTipoSelect() {
        return this.tipoSelect.element(by.css('option:checked')).getText();
    }

    tipoSelectLastOption(): promise.Promise<void> {
        return this.tipoSelect
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
