import { IAdvogadoDgCoordenacao } from 'app/shared/model//advogado-dg-coordenacao.model';
import { IAssunto } from 'app/shared/model//assunto.model';

export interface ICoordenacaoJuridica {
    id?: number;
    sigla?: string;
    nome?: string;
    centena?: string;
    dgAdvogados?: IAdvogadoDgCoordenacao[];
    assuntos?: IAssunto[];
}

export class CoordenacaoJuridica implements ICoordenacaoJuridica {
    constructor(
        public id?: number,
        public sigla?: string,
        public nome?: string,
        public centena?: string,
        public dgAdvogados?: IAdvogadoDgCoordenacao[],
        public assuntos?: IAssunto[]
    ) {}
}
