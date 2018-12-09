import { element, by, promise, ElementFinder } from 'protractor';

export class AdvogadoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-advogado div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class AdvogadoUpdatePage {
    pageTitle = element(by.id('jhi-advogado-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomeInput = element(by.id('field_nome'));
    cpfInput = element(by.id('field_cpf'));
    ramalInput = element(by.id('field_ramal'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNomeInput(nome): promise.Promise<void> {
        return this.nomeInput.sendKeys(nome);
    }

    getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    setCpfInput(cpf): promise.Promise<void> {
        return this.cpfInput.sendKeys(cpf);
    }

    getCpfInput() {
        return this.cpfInput.getAttribute('value');
    }

    setRamalInput(ramal): promise.Promise<void> {
        return this.ramalInput.sendKeys(ramal);
    }

    getRamalInput() {
        return this.ramalInput.getAttribute('value');
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
