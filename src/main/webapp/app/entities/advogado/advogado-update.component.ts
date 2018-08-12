import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAdvogado } from 'app/shared/model/advogado.model';
import { AdvogadoService } from './advogado.service';

@Component({
    selector: 'jhi-advogado-update',
    templateUrl: './advogado-update.component.html'
})
export class AdvogadoUpdateComponent implements OnInit {
    private _advogado: IAdvogado;
    isSaving: boolean;

    constructor(private advogadoService: AdvogadoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ advogado }) => {
            this.advogado = advogado;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.advogado.id !== undefined) {
            this.subscribeToSaveResponse(this.advogadoService.update(this.advogado));
        } else {
            this.subscribeToSaveResponse(this.advogadoService.create(this.advogado));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdvogado>>) {
        result.subscribe((res: HttpResponse<IAdvogado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get advogado() {
        return this._advogado;
    }

    set advogado(advogado: IAdvogado) {
        this._advogado = advogado;
    }
}
