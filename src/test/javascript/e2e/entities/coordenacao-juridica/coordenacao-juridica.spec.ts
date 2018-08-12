import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { CoordenacaoJuridicaComponentsPage, CoordenacaoJuridicaUpdatePage } from './coordenacao-juridica.page-object';

describe('CoordenacaoJuridica e2e test', () => {
    let navBarPage: NavBarPage;
    let coordenacaoJuridicaUpdatePage: CoordenacaoJuridicaUpdatePage;
    let coordenacaoJuridicaComponentsPage: CoordenacaoJuridicaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CoordenacaoJuridicas', () => {
        navBarPage.goToEntity('coordenacao-juridica');
        coordenacaoJuridicaComponentsPage = new CoordenacaoJuridicaComponentsPage();
        expect(coordenacaoJuridicaComponentsPage.getTitle()).toMatch(/servicosJuridicosApp.coordenacaoJuridica.home.title/);
    });

    it('should load create CoordenacaoJuridica page', () => {
        coordenacaoJuridicaComponentsPage.clickOnCreateButton();
        coordenacaoJuridicaUpdatePage = new CoordenacaoJuridicaUpdatePage();
        expect(coordenacaoJuridicaUpdatePage.getPageTitle()).toMatch(/servicosJuridicosApp.coordenacaoJuridica.home.createOrEditLabel/);
        coordenacaoJuridicaUpdatePage.cancel();
    });

    /* it('should create and save CoordenacaoJuridicas', () => {
        coordenacaoJuridicaComponentsPage.clickOnCreateButton();
        coordenacaoJuridicaUpdatePage.setSiglaInput('sigla');
        expect(coordenacaoJuridicaUpdatePage.getSiglaInput()).toMatch('sigla');
        coordenacaoJuridicaUpdatePage.setNomeInput('nome');
        expect(coordenacaoJuridicaUpdatePage.getNomeInput()).toMatch('nome');
        coordenacaoJuridicaUpdatePage.setCentenaInput('centena');
        expect(coordenacaoJuridicaUpdatePage.getCentenaInput()).toMatch('centena');
        // coordenacaoJuridicaUpdatePage.assuntoSelectLastOption();
        coordenacaoJuridicaUpdatePage.save();
        expect(coordenacaoJuridicaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
