import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { FeriasLicencaComponentsPage, FeriasLicencaUpdatePage } from './ferias-licenca.page-object';

describe('FeriasLicenca e2e test', () => {
    let navBarPage: NavBarPage;
    let feriasLicencaUpdatePage: FeriasLicencaUpdatePage;
    let feriasLicencaComponentsPage: FeriasLicencaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load FeriasLicencas', () => {
        navBarPage.goToEntity('ferias-licenca');
        feriasLicencaComponentsPage = new FeriasLicencaComponentsPage();
        expect(feriasLicencaComponentsPage.getTitle()).toMatch(/servicosJuridicosApp.feriasLicenca.home.title/);
    });

    it('should load create FeriasLicenca page', () => {
        feriasLicencaComponentsPage.clickOnCreateButton();
        feriasLicencaUpdatePage = new FeriasLicencaUpdatePage();
        expect(feriasLicencaUpdatePage.getPageTitle()).toMatch(/servicosJuridicosApp.feriasLicenca.home.createOrEditLabel/);
        feriasLicencaUpdatePage.cancel();
    });

    /* it('should create and save FeriasLicencas', () => {
        feriasLicencaComponentsPage.clickOnCreateButton();
        feriasLicencaUpdatePage.setDtInicioInput('2000-12-31');
        expect(feriasLicencaUpdatePage.getDtInicioInput()).toMatch('2000-12-31');
        feriasLicencaUpdatePage.setDtFimInput('2000-12-31');
        expect(feriasLicencaUpdatePage.getDtFimInput()).toMatch('2000-12-31');
        feriasLicencaUpdatePage.tipoSelectLastOption();
        feriasLicencaUpdatePage.advogadoSelectLastOption();
        feriasLicencaUpdatePage.save();
        expect(feriasLicencaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
