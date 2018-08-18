import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Modalidade } from 'app/shared/model/modalidade.model';
import { ModalidadeService } from './modalidade.service';

@Component({
    selector: 'jhi-modalidade-update',
    templateUrl: './modalidade-update.component.html'
})
export class ModalidadeUpdateComponent implements OnInit {
    private _modalidade: Modalidade;
    isSaving: boolean;

    constructor(private modalidadeService: ModalidadeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ modalidade }) => {
            this.modalidade = modalidade;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.modalidade.id !== undefined) {
            this.subscribeToSaveResponse(this.modalidadeService.update(this.modalidade));
        } else {
            this.subscribeToSaveResponse(this.modalidadeService.create(this.modalidade));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Modalidade>>) {
        result.subscribe((res: HttpResponse<Modalidade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get modalidade() {
        return this._modalidade;
    }

    set modalidade(modalidade: Modalidade) {
        this._modalidade = modalidade;
    }
}
