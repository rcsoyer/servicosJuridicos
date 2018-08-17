import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICoordenacaoJuridica } from '../../shared/model/coordenacao-juridica.model';
import { CoordenacaoJuridicaService } from './coordenacao-juridica.service';
import { Assunto } from '../../shared/model/assunto.model';
import { AssuntoService } from '../../entities/assunto';

@Component({
    selector: 'jhi-coordenacao-juridica-update',
    templateUrl: './coordenacao-juridica-update.component.html'
})
export class CoordenacaoJuridicaUpdateComponent implements OnInit {
    private _coordenacaoJuridica: ICoordenacaoJuridica;
    isSaving: boolean;

    assuntos: Assunto[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private coordenacaoJuridicaService: CoordenacaoJuridicaService,
        private assuntoService: AssuntoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ coordenacaoJuridica }) => {
            this.coordenacaoJuridica = coordenacaoJuridica;
        });
        this.assuntoService.query().subscribe(
            (res: HttpResponse<Assunto[]>) => {
                this.assuntos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.coordenacaoJuridica.id !== undefined) {
            this.subscribeToSaveResponse(this.coordenacaoJuridicaService.update(this.coordenacaoJuridica));
        } else {
            this.subscribeToSaveResponse(this.coordenacaoJuridicaService.create(this.coordenacaoJuridica));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICoordenacaoJuridica>>) {
        result.subscribe((res: HttpResponse<ICoordenacaoJuridica>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAssuntoById(index: number, item: Assunto) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get coordenacaoJuridica() {
        return this._coordenacaoJuridica;
    }

    set coordenacaoJuridica(coordenacaoJuridica: ICoordenacaoJuridica) {
        this._coordenacaoJuridica = coordenacaoJuridica;
    }
}
