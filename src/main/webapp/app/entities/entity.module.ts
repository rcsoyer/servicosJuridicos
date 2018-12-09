import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ServicosJuridicosAssuntoModule } from './assunto/assunto.module';
import { ServicosJuridicosCoordenacaoJuridicaModule } from './coordenacao-juridica/coordenacao-juridica.module';
import { ServicosJuridicosAdvogadoModule } from './advogado/advogado.module';
import { ServicosJuridicosProcessoJudicialModule } from './processo-judicial/processo-judicial.module';
import { ServicosJuridicosModalidadeModule } from './modalidade/modalidade.module';
import { ServicosJuridicosFeriasLicencaModule } from './ferias-licenca/ferias-licenca.module';
import { ServicosJuridicosAdvogadoDgCoordenacaoModule } from './advogado-dg-coordenacao/advogado-dg-coordenacao.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ServicosJuridicosAssuntoModule,
        ServicosJuridicosCoordenacaoJuridicaModule,
        ServicosJuridicosAdvogadoModule,
        ServicosJuridicosProcessoJudicialModule,
        ServicosJuridicosModalidadeModule,
        ServicosJuridicosFeriasLicencaModule,
        ServicosJuridicosAdvogadoDgCoordenacaoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServicosJuridicosEntityModule {}
