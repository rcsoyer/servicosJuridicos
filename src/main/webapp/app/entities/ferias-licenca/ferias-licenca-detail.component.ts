import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeriasLicenca } from 'app/shared/model/ferias-licenca.model';

@Component({
    selector: 'jhi-ferias-licenca-detail',
    templateUrl: './ferias-licenca-detail.component.html'
})
export class FeriasLicencaDetailComponent implements OnInit {
    feriasLicenca: IFeriasLicenca;

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
