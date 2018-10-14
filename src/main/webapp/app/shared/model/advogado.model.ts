import {ProcessoJudicial} from 'app/shared/model//processo-judicial.model';
import {FeriasLicenca} from 'app/shared/model//ferias-licenca.model';
import {AdvogadoDgCoordenacao} from 'app/shared/model//advogado-dg-coordenacao.model';
import {BaseEntity} from 'app/shared/model/base-entity';

export class Advogado implements BaseEntity {

    constructor(
        public id?: number,
        public nome?: string,
        public cpf?: string,
        public ramal?: number,
        public processos?: ProcessoJudicial[],
        public feriasLicencas?: FeriasLicenca[],
        public dgCoordenacaos?: AdvogadoDgCoordenacao[]
    ) {
    }
}
