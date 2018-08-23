import { AdvogadoDgCoordenacao } from '../../shared/model//advogado-dg-coordenacao.model';
import { Assunto } from '../../shared/model//assunto.model';
import { BaseEntity } from './base-entity';

export class CoordenacaoJuridica implements BaseEntity {
    constructor(
        public id?: number,
        public sigla?: string,
        public nome?: string,
        public centena?: string,
        public dgAdvogados?: AdvogadoDgCoordenacao[],
        public assuntos?: Assunto[]
    ) {}
}
