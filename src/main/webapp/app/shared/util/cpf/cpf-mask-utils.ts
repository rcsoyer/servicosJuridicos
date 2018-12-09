import { Injectable } from '@angular/core';
import * as _ from 'lodash';

@Injectable()
export class CpfMaskUtils {
    removeMask(cpf: string): string {
        return _.replace(cpf, /\D/g, '');
    }

    maskCPF(): Object {
        return {
            mask: [
                /\d/,
                /\d/,
                /\d/,
                '.',
                /\d/,
                /\d/,
                /\d/,
                '.',
                /\d/,
                /\d/,
                /\d/,
                '-',
                /\d/,
                /\d/
            ],
            keepCharPositions: true,
            placeholderChar: '\u2000'
        };
    }

    formatar(cpf: string): string {
        cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
        cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
        cpf = cpf.replace(/(\d{3})(\d)/, '$1-$2');
        return cpf;
    }
}
