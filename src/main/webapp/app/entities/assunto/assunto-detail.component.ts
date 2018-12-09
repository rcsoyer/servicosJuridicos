import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Assunto } from '../../shared/model/assunto.model';
import { AssuntoUtils } from './assunto-utils';

@Component({ selector: 'assunto-detail', templateUrl: './assunto-detail.component.html' })
export class AssuntoDetailComponent implements OnInit {
    assunto: Assunto;

    constructor(private activatedRoute: ActivatedRoute, public assuntoUtils: AssuntoUtils) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assunto }) => {
            this.assunto = assunto;
        });
    }

    previousState() {
        window.history.back();
    }
}
