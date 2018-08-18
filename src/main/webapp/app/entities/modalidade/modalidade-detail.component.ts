import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Modalidade } from 'app/shared/model/modalidade.model';

@Component({
    selector: 'jhi-modalidade-detail',
    templateUrl: './modalidade-detail.component.html'
})
export class ModalidadeDetailComponent implements OnInit {
    modalidade: Modalidade;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ modalidade }) => {
            this.modalidade = modalidade;
        });
    }

    previousState() {
        window.history.back();
    }
}
