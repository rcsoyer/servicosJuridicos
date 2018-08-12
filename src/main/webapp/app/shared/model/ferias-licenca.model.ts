import { Moment } from 'moment';

export const enum FeriasLicensaTipo {
    FERIAS = 'FERIAS',
    LICENSA = 'LICENSA'
}

export interface IFeriasLicenca {
    id?: number;
    dtInicio?: Moment;
    dtFim?: Moment;
    tipo?: FeriasLicensaTipo;
    advogadoId?: number;
}

export class FeriasLicenca implements IFeriasLicenca {
    constructor(
        public id?: number,
        public dtInicio?: Moment,
        public dtFim?: Moment,
        public tipo?: FeriasLicensaTipo,
        public advogadoId?: number
    ) {}
}
