import { Injectable } from '@angular/core';

@Injectable()
export class AssuntoUtils {
    intepretarAtivo(ativo: boolean): string {
        return ativo ? 'Ativo' : 'Inativo';
    }
}
