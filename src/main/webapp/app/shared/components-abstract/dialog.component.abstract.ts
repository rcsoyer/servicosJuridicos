import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import * as _ from 'lodash';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import * as R from 'ramda';
import { Observable } from 'rxjs/Observable';
import { BasicService } from '../../shared/service-commons/basic-service.service';
import { BaseEntity } from '../../shared/model/base-entity';

export abstract class DialogComponentAbstract<T extends BaseEntity> {
    public model: T;
    public isSaving: boolean;
    public tituloModal: string;
    protected listModification: string;

    constructor(
        public activeModal: NgbActiveModal,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected service: BasicService<T>
    ) {
        this.isSaving = false;
    }

    protected definirTituloModal(titulo: string): void {
        const setTituloEditar = () => (this.tituloModal = 'Editar ' + titulo);
        const setTituloCadastrar = () =>
            (this.tituloModal = 'Cadastrar ' + titulo);
        R.ifElse(_.isNumber, setTituloEditar, setTituloCadastrar)(
            this.model.id
        );
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<T>>) {
        return () => result.subscribe(this.onSaveSuccess(), this.onSaveError());
    }

    protected onSaveSuccess() {
        return (res: HttpResponse<T>) => {
            const broadcastObj = { name: this.listModification, content: 'OK' };
            this.eventManager.broadcast(broadcastObj);
            this.isSaving = false;
            this.activeModal.dismiss(res.body);
        };
    }

    protected onSaveError() {
        return (res: HttpErrorResponse) => (this.isSaving = false);
    }

    protected subscribeToCreate() {
        return this.subscribeToSaveResponse(this.service.create(this.model));
    }

    protected subscribeToUpdate() {
        return this.subscribeToSaveResponse(this.service.update(this.model));
    }

    protected onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.trimInputText();
        const create = this.subscribeToCreate();
        const update = this.subscribeToUpdate();
        R.ifElse(_.isNumber, update, create)(this.model.id);
    }

    protected abstract trimInputText(): void;
}
