import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';

@Component({
    selector: 'jhi-advogado-dg-coordenacao-detail',
    templateUrl: './advogado-dg-coordenacao-detail.component.html'
})
export class AdvogadoDgCoordenacaoDetailComponent implements OnInit {
    advogadoDgCoordenacao: IAdvogadoDgCoordenacao;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advogadoDgCoordenacao }) => {
            this.advogadoDgCoordenacao = advogadoDgCoordenacao;
        });
    }

    previousState() {
        window.history.back();
    }
}
