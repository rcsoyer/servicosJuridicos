import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAssunto } from 'app/shared/model/assunto.model';
import { AssuntoService } from './assunto.service';

@Component({
    selector: 'jhi-assunto-update',
    templateUrl: './assunto-update.component.html'
})
export class AssuntoUpdateComponent implements OnInit {
    private _assunto: IAssunto;
    isSaving: boolean;

    constructor(private assuntoService: AssuntoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ assunto }) => {
            this.assunto = assunto;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.assunto.id !== undefined) {
            this.subscribeToSaveResponse(this.assuntoService.update(this.assunto));
        } else {
            this.subscribeToSaveResponse(this.assuntoService.create(this.assunto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAssunto>>) {
        result.subscribe((res: HttpResponse<IAssunto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get assunto() {
        return this._assunto;
    }

    set assunto(assunto: IAssunto) {
        this._assunto = assunto;
    }
}
