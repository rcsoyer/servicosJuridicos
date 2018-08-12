import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { AssuntoComponentsPage, AssuntoUpdatePage } from './assunto.page-object';

describe('Assunto e2e test', () => {
    let navBarPage: NavBarPage;
    let assuntoUpdatePage: AssuntoUpdatePage;
    let assuntoComponentsPage: AssuntoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Assuntos', () => {
        navBarPage.goToEntity('assunto');
        assuntoComponentsPage = new AssuntoComponentsPage();
        expect(assuntoComponentsPage.getTitle()).toMatch(/servicosJuridicosApp.assunto.home.title/);
    });

    it('should load create Assunto page', () => {
        assuntoComponentsPage.clickOnCreateButton();
        assuntoUpdatePage = new AssuntoUpdatePage();
        expect(assuntoUpdatePage.getPageTitle()).toMatch(/servicosJuridicosApp.assunto.home.createOrEditLabel/);
        assuntoUpdatePage.cancel();
    });

    it('should create and save Assuntos', () => {
        assuntoComponentsPage.clickOnCreateButton();
        assuntoUpdatePage.setDescricaoInput('descricao');
        expect(assuntoUpdatePage.getDescricaoInput()).toMatch('descricao');
        assuntoUpdatePage
            .getAtivoInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    assuntoUpdatePage.getAtivoInput().click();
                    expect(assuntoUpdatePage.getAtivoInput().isSelected()).toBeFalsy();
                } else {
                    assuntoUpdatePage.getAtivoInput().click();
                    expect(assuntoUpdatePage.getAtivoInput().isSelected()).toBeTruthy();
                }
            });
        assuntoUpdatePage.setPesoInput('5');
        expect(assuntoUpdatePage.getPesoInput()).toMatch('5');
        assuntoUpdatePage.save();
        expect(assuntoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
