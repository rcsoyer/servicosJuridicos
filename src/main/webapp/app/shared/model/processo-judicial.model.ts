import { Moment } from 'moment';

export interface IProcessoJudicial {
    id?: number;
    numero?: string;
    prazoFinal?: Moment;
    dtAtribuicao?: Moment;
    dtInicio?: Moment;
    dtConclusao?: Moment;
    assuntoId?: number;
    modalidadeId?: number;
    advogadoId?: number;
}

export class ProcessoJudicial implements IProcessoJudicial {
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
    ) {}
}
