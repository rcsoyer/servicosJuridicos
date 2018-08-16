import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import * as _ from 'lodash';
import { JhiAlertService } from 'ng-jhipster';
import * as R from 'ramda';
import { Observable } from 'rxjs/Observable';
import { BaseEntity } from '../model/base-entity';
import { BasicService } from '../service-commons/basic-service.service';

export abstract class UpdateComponentAbastract<T extends BaseEntity> {
    public model: T;
    public isSaving: boolean;
    public tituloPagina: string;
    protected listModification: string;

    constructor(protected service: BasicService<T>, protected activatedRoute: ActivatedRoute, private jhiAlertService: JhiAlertService) {
        this.isSaving = false;
    }

    protected defineTituloPagina(titulo: string): void {
        const setTituloEditar = () => (this.tituloPagina = 'Editar ' + titulo);
        const setTituloCadastrar = () => (this.tituloPagina = 'Cadastrar ' + titulo);
        R.ifElse(_.isNumber, setTituloEditar, setTituloCadastrar)(this.model.id);
    }

    protected subscribeModelRoute(): void {
        this.activatedRoute.data.subscribe(({ model }) => (this.model = model));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.trimInputText();
        const create = this.subscribeToCreate();
        const update = this.subscribeToUpdate();
        R.ifElse(_.isNumber, update, create)(this.model.id);
    }

    protected subscribeToCreate() {
        return this.subscribeToSaveResponse(this.service.create(this.model));
    }

    protected subscribeToUpdate() {
        return this.subscribeToSaveResponse(this.service.update(this.model));
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<T>>) {
        return () => result.subscribe(this.onSaveSuccess, this.onSaveError);
    }

    protected onSaveSuccess(res: HttpResponse<T>) {
        // const broadcastObj = { name: this.listModification, content: 'OK' };
        // this.eventManager.broadcast(broadcastObj);
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError(error: HttpErrorResponse) {
        this.isSaving = false;
        this.jhiAlertService.error(error.message, null, null);
    }

    protected abstract trimInputText(): void;
}
