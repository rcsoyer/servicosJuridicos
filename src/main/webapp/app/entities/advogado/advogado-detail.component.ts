import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Advogado } from 'app/shared/model/advogado.model';

@Component({
    selector: 'jhi-advogado-detail',
    templateUrl: './advogado-detail.component.html'
})
export class AdvogadoDetailComponent implements OnInit {
    advogado: Advogado;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advogado }) => {
            this.advogado = advogado;
        });
    }

    previousState() {
        window.history.back();
    }
}
