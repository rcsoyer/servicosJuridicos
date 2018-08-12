export const enum RangeDgCoordenacao {
    INCLUSIVE = 'INCLUSIVE',
    EXCLUSIVE = 'EXCLUSIVE'
}

export interface IAdvogadoDgCoordenacao {
    id?: number;
    dgPessoalInicio?: string;
    dgPessoalFim?: string;
    dgDupla?: string;
    rangeDgCoordenacao?: RangeDgCoordenacao;
    advogadoId?: number;
    coordenacaoId?: number;
}

export class AdvogadoDgCoordenacao implements IAdvogadoDgCoordenacao {
    constructor(
        public id?: number,
        public dgPessoalInicio?: string,
        public dgPessoalFim?: string,
        public dgDupla?: string,
        public rangeDgCoordenacao?: RangeDgCoordenacao,
        public advogadoId?: number,
        public coordenacaoId?: number
    ) {}
}
