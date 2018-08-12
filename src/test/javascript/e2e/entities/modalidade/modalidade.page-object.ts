import { element, by, promise, ElementFinder } from 'protractor';

export class ModalidadeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-modalidade div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ModalidadeUpdatePage {
    pageTitle = element(by.id('jhi-modalidade-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descricaoInput = element(by.id('field_descricao'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDescricaoInput(descricao): promise.Promise<void> {
        return this.descricaoInput.sendKeys(descricao);
    }

    getDescricaoInput() {
        return this.descricaoInput.getAttribute('value');
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
