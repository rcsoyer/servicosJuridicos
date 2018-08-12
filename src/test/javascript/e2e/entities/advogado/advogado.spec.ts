import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { AdvogadoComponentsPage, AdvogadoUpdatePage } from './advogado.page-object';

describe('Advogado e2e test', () => {
    let navBarPage: NavBarPage;
    let advogadoUpdatePage: AdvogadoUpdatePage;
    let advogadoComponentsPage: AdvogadoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Advogados', () => {
        navBarPage.goToEntity('advogado');
        advogadoComponentsPage = new AdvogadoComponentsPage();
        expect(advogadoComponentsPage.getTitle()).toMatch(/servicosJuridicosApp.advogado.home.title/);
    });

    it('should load create Advogado page', () => {
        advogadoComponentsPage.clickOnCreateButton();
        advogadoUpdatePage = new AdvogadoUpdatePage();
        expect(advogadoUpdatePage.getPageTitle()).toMatch(/servicosJuridicosApp.advogado.home.createOrEditLabel/);
        advogadoUpdatePage.cancel();
    });

    it('should create and save Advogados', () => {
        advogadoComponentsPage.clickOnCreateButton();
        advogadoUpdatePage.setNomeInput('nome');
        expect(advogadoUpdatePage.getNomeInput()).toMatch('nome');
        advogadoUpdatePage.setCpfInput('cpf');
        expect(advogadoUpdatePage.getCpfInput()).toMatch('cpf');
        advogadoUpdatePage.setRamalInput('5');
        expect(advogadoUpdatePage.getRamalInput()).toMatch('5');
        advogadoUpdatePage.save();
        expect(advogadoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
