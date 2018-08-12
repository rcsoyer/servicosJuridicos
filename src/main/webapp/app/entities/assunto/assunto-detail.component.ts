import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssunto } from 'app/shared/model/assunto.model';

@Component({
    selector: 'jhi-assunto-detail',
    templateUrl: './assunto-detail.component.html'
})
export class AssuntoDetailComponent implements OnInit {
    assunto: IAssunto;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assunto }) => {
            this.assunto = assunto;
        });
    }

    previousState() {
        window.history.back();
    }
}
