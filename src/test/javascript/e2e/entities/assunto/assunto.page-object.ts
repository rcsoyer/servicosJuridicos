import { element, by, promise, ElementFinder } from 'protractor';

export class AssuntoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-assunto div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AssuntoUpdatePage {
    pageTitle = element(by.id('jhi-assunto-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descricaoInput = element(by.id('field_descricao'));
    ativoInput = element(by.id('field_ativo'));
    pesoInput = element(by.id('field_peso'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDescricaoInput(descricao): promise.Promise<void> {
        return this.descricaoInput.sendKeys(descricao);
    }

    getDescricaoInput() {
        return this.descricaoInput.getAttribute('value');
    }

    getAtivoInput() {
        return this.ativoInput;
    }
    setPesoInput(peso): promise.Promise<void> {
        return this.pesoInput.sendKeys(peso);
    }

    getPesoInput() {
        return this.pesoInput.getAttribute('value');
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
