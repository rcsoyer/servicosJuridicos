import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { faMinus } from '@fortawesome/free-solid-svg-icons';
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
    public readonly faMinus = faMinus;

    constructor(protected service: BasicService<T>, protected activatedRoute: ActivatedRoute, protected jhiAlertService: JhiAlertService) {
        this.isSaving = false;
    }

    protected onInit() {
        this.isSaving = false;
        this.subscribeModelRoute();
    }

    protected defineTituloPagina(titulo: string): void {
        const setTituloEditar = () => (this.tituloPagina = 'Editar ' + titulo);
        const setTituloCadastrar = () => (this.tituloPagina = 'Cadastrar ' + titulo);
        R.ifElse(_.isNumber, setTituloEditar, setTituloCadastrar)(this.model.id);
    }

    private subscribeModelRoute(): void {
        this.activatedRoute.data.subscribe(({ model }) => (this.model = model));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.trimInputText();
        R.ifElse(_.isNumber, this.subscribeToUpdate, this.subscribeToCreate)(this.model.id);
    }

    private subscribeToCreate() {
        return this.subscribeToSaveResponse(this.service.create(this.model));
    }

    private subscribeToUpdate() {
        return this.subscribeToSaveResponse(this.service.update(this.model));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<T>>) {
        return () => result.subscribe(this.onSaveSuccess, this.onSaveError);
    }

    private onSaveSuccess(res: HttpResponse<T>) {
        // const broadcastObj = { name: this.listModification, content: 'OK' };
        // this.eventManager.broadcast(broadcastObj);
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError(error: HttpErrorResponse) {
        this.isSaving = false;
        this.jhiAlertService.error(error.message, null, null);
    }

    protected abstract trimInputText(): void;
}
