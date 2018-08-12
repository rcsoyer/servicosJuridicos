import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFeriasLicenca } from 'app/shared/model/ferias-licenca.model';
import { FeriasLicencaService } from './ferias-licenca.service';
import { IAdvogado } from 'app/shared/model/advogado.model';
import { AdvogadoService } from 'app/entities/advogado';

@Component({
    selector: 'jhi-ferias-licenca-update',
    templateUrl: './ferias-licenca-update.component.html'
})
export class FeriasLicencaUpdateComponent implements OnInit {
    private _feriasLicenca: IFeriasLicenca;
    isSaving: boolean;

    advogados: IAdvogado[];
    dtInicioDp: any;
    dtFimDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private feriasLicencaService: FeriasLicencaService,
        private advogadoService: AdvogadoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ feriasLicenca }) => {
            this.feriasLicenca = feriasLicenca;
        });
        this.advogadoService.query().subscribe(
            (res: HttpResponse<IAdvogado[]>) => {
                this.advogados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.feriasLicenca.id !== undefined) {
            this.subscribeToSaveResponse(this.feriasLicencaService.update(this.feriasLicenca));
        } else {
            this.subscribeToSaveResponse(this.feriasLicencaService.create(this.feriasLicenca));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFeriasLicenca>>) {
        result.subscribe((res: HttpResponse<IFeriasLicenca>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdvogadoById(index: number, item: IAdvogado) {
        return item.id;
    }
    get feriasLicenca() {
        return this._feriasLicenca;
    }

    set feriasLicenca(feriasLicenca: IFeriasLicenca) {
        this._feriasLicenca = feriasLicenca;
    }
}
