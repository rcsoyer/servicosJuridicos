import {BaseEntity} from './base-entity';

export class Assunto implements BaseEntity {

    constructor(public id?: number, public descricao?: string,
                public ativo?: boolean, public peso?: number) {
        this.ativo = true;
    }
}
