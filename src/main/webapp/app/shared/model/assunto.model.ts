export class Assunto {
    constructor(public id?: number, public descricao?: string, public ativo?: boolean, public peso?: number) {
        this.ativo = false;
    }
}
