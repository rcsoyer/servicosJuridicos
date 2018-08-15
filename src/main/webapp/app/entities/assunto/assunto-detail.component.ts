import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Assunto } from '../../shared/model/assunto.model';

@Component({
    selector: 'jhi-assunto-detail',
    templateUrl: './assunto-detail.component.html'
})
export class AssuntoDetailComponent implements OnInit {
    assunto: Assunto;

    constructor(private activatedRoute: ActivatedRoute) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assunto }) => {
            this.assunto = assunto;
        });
    }

    previousState() {
        window.history.back();
    }
}
