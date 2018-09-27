import {AdvogadoDgCoordenacao} from '../../shared/model//advogado-dg-coordenacao.model';
import {Assunto} from '../../shared/model//assunto.model';
import {BaseEntity} from './base-entity';
import * as _ from 'lodash';

export class CoordenacaoJuridica implements BaseEntity {
    constructor(
        public id?: number,
        private _sigla?: string,
        public nome?: string,
        public centena?: string,
        public dgAdvogados?: AdvogadoDgCoordenacao[],
        public assuntos?: Assunto[]
    ) {
    }

    set sigla(sigla: string) {
        this._sigla = _.trim(sigla);
    }

    get sigla(): string {
        return this._sigla;
    }
}
