import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { AdvogadoDgCoordenacaoComponentsPage, AdvogadoDgCoordenacaoUpdatePage } from './advogado-dg-coordenacao.page-object';

describe('AdvogadoDgCoordenacao e2e test', () => {
    let navBarPage: NavBarPage;
    let advogadoDgCoordenacaoUpdatePage: AdvogadoDgCoordenacaoUpdatePage;
    let advogadoDgCoordenacaoComponentsPage: AdvogadoDgCoordenacaoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load AdvogadoDgCoordenacaos', () => {
        navBarPage.goToEntity('advogado-dg-coordenacao');
        advogadoDgCoordenacaoComponentsPage = new AdvogadoDgCoordenacaoComponentsPage();
        expect(advogadoDgCoordenacaoComponentsPage.getTitle()).toMatch(/servicosJuridicosApp.advogadoDgCoordenacao.home.title/);
    });

    it('should load create AdvogadoDgCoordenacao page', () => {
        advogadoDgCoordenacaoComponentsPage.clickOnCreateButton();
        advogadoDgCoordenacaoUpdatePage = new AdvogadoDgCoordenacaoUpdatePage();
        expect(advogadoDgCoordenacaoUpdatePage.getPageTitle()).toMatch(/servicosJuridicosApp.advogadoDgCoordenacao.home.createOrEditLabel/);
        advogadoDgCoordenacaoUpdatePage.cancel();
    });

    /* it('should create and save AdvogadoDgCoordenacaos', () => {
        advogadoDgCoordenacaoComponentsPage.clickOnCreateButton();
        advogadoDgCoordenacaoUpdatePage.setDgPessoalInicioInput('dgPessoalInicio');
        expect(advogadoDgCoordenacaoUpdatePage.getDgPessoalInicioInput()).toMatch('dgPessoalInicio');
        advogadoDgCoordenacaoUpdatePage.setDgPessoalFimInput('dgPessoalFim');
        expect(advogadoDgCoordenacaoUpdatePage.getDgPessoalFimInput()).toMatch('dgPessoalFim');
        advogadoDgCoordenacaoUpdatePage.setDgDuplaInput('dgDupla');
        expect(advogadoDgCoordenacaoUpdatePage.getDgDuplaInput()).toMatch('dgDupla');
        advogadoDgCoordenacaoUpdatePage.rangeDgCoordenacaoSelectLastOption();
        advogadoDgCoordenacaoUpdatePage.advogadoSelectLastOption();
        advogadoDgCoordenacaoUpdatePage.coordenacaoSelectLastOption();
        advogadoDgCoordenacaoUpdatePage.save();
        expect(advogadoDgCoordenacaoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
