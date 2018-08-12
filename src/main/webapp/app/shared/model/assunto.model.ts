export interface IAssunto {
    id?: number;
    descricao?: string;
    ativo?: boolean;
    peso?: number;
}

export class Assunto implements IAssunto {
    constructor(public id?: number, public descricao?: string, public ativo?: boolean, public peso?: number) {
        this.ativo = false;
    }
}
