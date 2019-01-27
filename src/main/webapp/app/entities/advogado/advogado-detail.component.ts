import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Advogado} from 'app/shared/model/advogado.model';
import {CpfMaskUtils} from 'app/shared/util/cpf/cpf-mask-utils';

@Component({
    selector: 'advogado-detail',
    templateUrl: './advogado-detail.component.html'
})
export class AdvogadoDetailComponent implements OnInit {

    advogado: Advogado;

    constructor(private activatedRoute: ActivatedRoute, private cpfMaskUtils: CpfMaskUtils) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({advogado}) => {
            this.advogado = advogado;
        });
    }

    formatCpf(): string {
        return this.cpfMaskUtils.formatar(this.advogado.cpf);
    }

    previousState() {
        window.history.back();
    }
}
