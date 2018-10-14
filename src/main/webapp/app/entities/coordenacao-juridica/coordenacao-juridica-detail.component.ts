import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CoordenacaoJuridica } from 'app/shared/model/coordenacao-juridica.model';

@Component({
    selector: 'jhi-coordenacao-juridica-detail',
    templateUrl: './coordenacao-juridica-detail.component.html'
})
export class CoordenacaoJuridicaDetailComponent implements OnInit {
    coordenacaoJuridica: CoordenacaoJuridica;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ coordenacaoJuridica }) => {
            this.coordenacaoJuridica = coordenacaoJuridica;
        });
    }

    previousState() {
        window.history.back();
    }
}
