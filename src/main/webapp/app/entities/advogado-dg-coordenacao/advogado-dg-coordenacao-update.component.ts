import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';
import { AdvogadoDgCoordenacaoService } from './advogado-dg-coordenacao.service';
import { IAdvogado } from 'app/shared/model/advogado.model';
import { AdvogadoService } from 'app/entities/advogado';
import { ICoordenacaoJuridica } from 'app/shared/model/coordenacao-juridica.model';
import { CoordenacaoJuridicaService } from 'app/entities/coordenacao-juridica';

@Component({
    selector: 'jhi-advogado-dg-coordenacao-update',
    templateUrl: './advogado-dg-coordenacao-update.component.html'
})
export class AdvogadoDgCoordenacaoUpdateComponent implements OnInit {
    private _advogadoDgCoordenacao: IAdvogadoDgCoordenacao;
    isSaving: boolean;

    advogados: IAdvogado[];

    coordenacaojuridicas: ICoordenacaoJuridica[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private advogadoDgCoordenacaoService: AdvogadoDgCoordenacaoService,
        private advogadoService: AdvogadoService,
        private coordenacaoJuridicaService: CoordenacaoJuridicaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ advogadoDgCoordenacao }) => {
            this.advogadoDgCoordenacao = advogadoDgCoordenacao;
        });
        this.advogadoService.query().subscribe(
            (res: HttpResponse<IAdvogado[]>) => {
                this.advogados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.coordenacaoJuridicaService.query().subscribe(
            (res: HttpResponse<ICoordenacaoJuridica[]>) => {
                this.coordenacaojuridicas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.advogadoDgCoordenacao.id !== undefined) {
            this.subscribeToSaveResponse(this.advogadoDgCoordenacaoService.update(this.advogadoDgCoordenacao));
        } else {
            this.subscribeToSaveResponse(this.advogadoDgCoordenacaoService.create(this.advogadoDgCoordenacao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdvogadoDgCoordenacao>>) {
        result.subscribe(
            (res: HttpResponse<IAdvogadoDgCoordenacao>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackCoordenacaoJuridicaById(index: number, item: ICoordenacaoJuridica) {
        return item.id;
    }
    get advogadoDgCoordenacao() {
        return this._advogadoDgCoordenacao;
    }

    set advogadoDgCoordenacao(advogadoDgCoordenacao: IAdvogadoDgCoordenacao) {
        this._advogadoDgCoordenacao = advogadoDgCoordenacao;
    }
}
