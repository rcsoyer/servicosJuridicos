import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ModalidadeComponentsPage, ModalidadeUpdatePage } from './modalidade.page-object';

describe('Modalidade e2e test', () => {
    let navBarPage: NavBarPage;
    let modalidadeUpdatePage: ModalidadeUpdatePage;
    let modalidadeComponentsPage: ModalidadeComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Modalidades', () => {
        navBarPage.goToEntity('modalidade');
        modalidadeComponentsPage = new ModalidadeComponentsPage();
        expect(modalidadeComponentsPage.getTitle()).toMatch(/servicosJuridicosApp.modalidade.home.title/);
    });

    it('should load create Modalidade page', () => {
        modalidadeComponentsPage.clickOnCreateButton();
        modalidadeUpdatePage = new ModalidadeUpdatePage();
        expect(modalidadeUpdatePage.getPageTitle()).toMatch(/servicosJuridicosApp.modalidade.home.createOrEditLabel/);
        modalidadeUpdatePage.cancel();
    });

    it('should create and save Modalidades', () => {
        modalidadeComponentsPage.clickOnCreateButton();
        modalidadeUpdatePage.setDescricaoInput('descricao');
        expect(modalidadeUpdatePage.getDescricaoInput()).toMatch('descricao');
        modalidadeUpdatePage.save();
        expect(modalidadeUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
