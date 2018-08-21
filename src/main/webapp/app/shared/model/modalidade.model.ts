import { BaseEntity } from './base-entity';

export class Modalidade implements BaseEntity {
    constructor(public id?: number, public descricao?: string) {}
}
