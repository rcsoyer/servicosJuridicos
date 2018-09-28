import {AdvogadoDgCoordenacao} from '../../shared/model//advogado-dg-coordenacao.model';
import {Assunto} from '../../shared/model//assunto.model';
import {BaseEntity} from './base-entity';
import * as _ from 'lodash';
import * as R from 'ramda';

export class CoordenacaoJuridica implements BaseEntity {
    constructor(
        public id?: number,
        public sigla?: string,
        public nome?: string,
        public centena?: string,
        public dgAdvogados?: AdvogadoDgCoordenacao[],
        public assuntos?: Assunto[]
    ) {
    }

    trimFields(): void {
        this.nome = _.trim(this.nome);
        this.sigla = _.trim(this.sigla);
        const setNomeToNull = () => this.nome = undefined;
        const setSiglaToNull = () => this.sigla = undefined;
        R.when(_.isEmpty, setNomeToNull)(this.nome);
        R.when(_.isEmpty, setSiglaToNull)(this.sigla);
    }
}
