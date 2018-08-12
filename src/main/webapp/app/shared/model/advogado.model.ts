import { IProcessoJudicial } from 'app/shared/model//processo-judicial.model';
import { IFeriasLicenca } from 'app/shared/model//ferias-licenca.model';
import { IAdvogadoDgCoordenacao } from 'app/shared/model//advogado-dg-coordenacao.model';

export interface IAdvogado {
    id?: number;
    nome?: string;
    cpf?: string;
    ramal?: number;
    processos?: IProcessoJudicial[];
    feriasLicencas?: IFeriasLicenca[];
    dgCoordenacaos?: IAdvogadoDgCoordenacao[];
}

export class Advogado implements IAdvogado {
    constructor(
        public id?: number,
        public nome?: string,
        public cpf?: string,
        public ramal?: number,
        public processos?: IProcessoJudicial[],
        public feriasLicencas?: IFeriasLicenca[],
        public dgCoordenacaos?: IAdvogadoDgCoordenacao[]
    ) {}
}
