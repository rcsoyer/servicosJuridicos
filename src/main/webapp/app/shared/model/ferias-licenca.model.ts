import {Moment} from 'moment';
import {BaseEntity} from 'app/shared/model/base-entity';

export const enum FeriasLicensaTipo {
    FERIAS = 'FERIAS',
    LICENSA = 'LICENSA'
}

export class FeriasLicenca implements BaseEntity {

    constructor(
        public id?: number,
        public dtInicio?: Moment,
        public dtFim?: Moment,
        public tipo?: FeriasLicensaTipo,
        public advogadoId?: number
    ) {
    }
}
