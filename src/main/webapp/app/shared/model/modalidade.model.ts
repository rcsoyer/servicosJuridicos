export interface IModalidade {
    id?: number;
    descricao?: string;
}

export class Modalidade implements IModalidade {
    constructor(public id?: number, public descricao?: string) {}
}
