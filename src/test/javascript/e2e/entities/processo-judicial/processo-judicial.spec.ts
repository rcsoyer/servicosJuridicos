import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ProcessoJudicialComponentsPage, ProcessoJudicialUpdatePage } from './processo-judicial.page-object';

describe('ProcessoJudicial e2e test', () => {
    let navBarPage: NavBarPage;
    let processoJudicialUpdatePage: ProcessoJudicialUpdatePage;
    let processoJudicialComponentsPage: ProcessoJudicialComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ProcessoJudicials', () => {
        navBarPage.goToEntity('processo-judicial');
        processoJudicialComponentsPage = new ProcessoJudicialComponentsPage();
        expect(processoJudicialComponentsPage.getTitle()).toMatch(/servicosJuridicosApp.processoJudicial.home.title/);
    });

    it('should load create ProcessoJudicial page', () => {
        processoJudicialComponentsPage.clickOnCreateButton();
        processoJudicialUpdatePage = new ProcessoJudicialUpdatePage();
        expect(processoJudicialUpdatePage.getPageTitle()).toMatch(/servicosJuridicosApp.processoJudicial.home.createOrEditLabel/);
        processoJudicialUpdatePage.cancel();
    });

    /* it('should create and save ProcessoJudicials', () => {
        processoJudicialComponentsPage.clickOnCreateButton();
        processoJudicialUpdatePage.setNumeroInput('numero');
        expect(processoJudicialUpdatePage.getNumeroInput()).toMatch('numero');
        processoJudicialUpdatePage.setPrazoFinalInput('2000-12-31');
        expect(processoJudicialUpdatePage.getPrazoFinalInput()).toMatch('2000-12-31');
        processoJudicialUpdatePage.setDtAtribuicaoInput('2000-12-31');
        expect(processoJudicialUpdatePage.getDtAtribuicaoInput()).toMatch('2000-12-31');
        processoJudicialUpdatePage.setDtInicioInput('2000-12-31');
        expect(processoJudicialUpdatePage.getDtInicioInput()).toMatch('2000-12-31');
        processoJudicialUpdatePage.setDtConclusaoInput('2000-12-31');
        expect(processoJudicialUpdatePage.getDtConclusaoInput()).toMatch('2000-12-31');
        processoJudicialUpdatePage.assuntoSelectLastOption();
        processoJudicialUpdatePage.modalidadeSelectLastOption();
        processoJudicialUpdatePage.advogadoSelectLastOption();
        processoJudicialUpdatePage.save();
        expect(processoJudicialUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
