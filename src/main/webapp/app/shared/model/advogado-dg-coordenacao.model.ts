import {BaseEntity} from 'app/shared/model/base-entity';

export const enum RangeDgCoordenacao {
    INCLUSIVE = 'INCLUSIVE',
    EXCLUSIVE = 'EXCLUSIVE'
}

export class AdvogadoDgCoordenacao implements BaseEntity {

    constructor(
        public id?: number,
        public dgPessoalInicio?: string,
        public dgPessoalFim?: string,
        public dgDupla?: string,
        public rangeDgCoordenacao?: RangeDgCoordenacao,
        public advogadoId?: number,
        public coordenacaoId?: number
    ) {
    }
}
