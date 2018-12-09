import { element, by, promise, ElementFinder } from 'protractor';

export class CoordenacaoJuridicaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-coordenacao-juridica div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CoordenacaoJuridicaUpdatePage {
    pageTitle = element(by.id('jhi-coordenacao-juridica-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    siglaInput = element(by.id('field_sigla'));
    nomeInput = element(by.id('field_nome'));
    centenaInput = element(by.id('field_centena'));
    assuntoSelect = element(by.id('field_assunto'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setSiglaInput(sigla): promise.Promise<void> {
        return this.siglaInput.sendKeys(sigla);
    }

    getSiglaInput() {
        return this.siglaInput.getAttribute('value');
    }

    setNomeInput(nome): promise.Promise<void> {
        return this.nomeInput.sendKeys(nome);
    }

    getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    setCentenaInput(centena): promise.Promise<void> {
        return this.centenaInput.sendKeys(centena);
    }

    getCentenaInput() {
        return this.centenaInput.getAttribute('value');
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
