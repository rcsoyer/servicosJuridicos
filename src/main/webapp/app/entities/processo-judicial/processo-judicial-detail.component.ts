import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProcessoJudicial } from 'app/shared/model/processo-judicial.model';

@Component({
    selector: 'jhi-processo-judicial-detail',
    templateUrl: './processo-judicial-detail.component.html'
})
export class ProcessoJudicialDetailComponent implements OnInit {
    processoJudicial: IProcessoJudicial;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ processoJudicial }) => {
            this.processoJudicial = processoJudicial;
        });
    }

    previousState() {
        window.history.back();
    }
}
