import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { FeriasLicenca } from 'app/shared/model/ferias-licenca.model';

@Component({
    selector: 'jhi-ferias-licenca-detail',
    templateUrl: './ferias-licenca-detail.component.html'
})
export class FeriasLicencaDetailComponent implements OnInit {
    feriasLicenca: FeriasLicenca;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ feriasLicenca }) => {
            this.feriasLicenca = feriasLicenca;
        });
    }

    previousState() {
        window.history.back();
    }
}
