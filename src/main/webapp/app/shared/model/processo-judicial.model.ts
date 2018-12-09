import {Moment} from 'moment';
import {BaseEntity} from 'app/shared/model/base-entity';

export class ProcessoJudicial implements BaseEntity {

    constructor(
        public id?: number,
        public numero?: string,
        public prazoFinal?: Moment,
        public dtAtribuicao?: Moment,
        public dtInicio?: Moment,
        public dtConclusao?: Moment,
        public assuntoId?: number,
        public modalidadeId?: number,
        public advogadoId?: number
    ) {
    }
}
