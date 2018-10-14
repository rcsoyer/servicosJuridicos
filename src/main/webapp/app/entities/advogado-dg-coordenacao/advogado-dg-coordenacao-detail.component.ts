import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { AdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';

@Component({
    selector: 'jhi-advogado-dg-coordenacao-detail',
    templateUrl: './advogado-dg-coordenacao-detail.component.html'
})
export class AdvogadoDgCoordenacaoDetailComponent implements OnInit {
    advogadoDgCoordenacao: AdvogadoDgCoordenacao;

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
