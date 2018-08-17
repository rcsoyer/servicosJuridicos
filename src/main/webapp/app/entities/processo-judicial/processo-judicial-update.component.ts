import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProcessoJudicial } from '../../shared/model/processo-judicial.model';
import { ProcessoJudicialService } from './processo-judicial.service';
import { Assunto } from '../../shared/model/assunto.model';
import { AssuntoService } from '../../entities/assunto';
import { IModalidade } from '../../shared/model/modalidade.model';
import { ModalidadeService } from '../../entities/modalidade';
import { IAdvogado } from '../../shared/model/advogado.model';
import { AdvogadoService } from '../../entities/advogado';

@Component({
    selector: 'jhi-processo-judicial-update',
    templateUrl: './processo-judicial-update.component.html'
})
export class ProcessoJudicialUpdateComponent implements OnInit {
    private _processoJudicial: IProcessoJudicial;
    isSaving: boolean;

    assuntos: Assunto[];

    modalidades: IModalidade[];

    advogados: IAdvogado[];
    prazoFinalDp: any;
    dtAtribuicaoDp: any;
    dtInicioDp: any;
    dtConclusaoDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private processoJudicialService: ProcessoJudicialService,
        private assuntoService: AssuntoService,
        private modalidadeService: ModalidadeService,
        private advogadoService: AdvogadoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ processoJudicial }) => {
            this.processoJudicial = processoJudicial;
        });
        this.assuntoService.query().subscribe(
            (res: HttpResponse<Assunto[]>) => {
                this.assuntos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.modalidadeService.query().subscribe(
            (res: HttpResponse<IModalidade[]>) => {
                this.modalidades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.processoJudicial.id !== undefined) {
            this.subscribeToSaveResponse(this.processoJudicialService.update(this.processoJudicial));
        } else {
            this.subscribeToSaveResponse(this.processoJudicialService.create(this.processoJudicial));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProcessoJudicial>>) {
        result.subscribe((res: HttpResponse<IProcessoJudicial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackModalidadeById(index: number, item: IModalidade) {
        return item.id;
    }

    trackAdvogadoById(index: number, item: IAdvogado) {
        return item.id;
    }
    get processoJudicial() {
        return this._processoJudicial;
    }

    set processoJudicial(processoJudicial: IProcessoJudicial) {
        this._processoJudicial = processoJudicial;
    }
}
