import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {JhiAlertService} from 'ng-jhipster';
import {AdvogadoDgCoordenacao} from 'app/shared/model/advogado-dg-coordenacao.model';
import {AdvogadoDgCoordenacaoService} from './advogado-dg-coordenacao.service';
import {Advogado} from 'app/shared/model/advogado.model';
import {AdvogadoService} from 'app/entities/advogado';
import {CoordenacaoJuridica} from 'app/shared/model/coordenacao-juridica.model';
import {CoordenacaoJuridicaService} from 'app/entities/coordenacao-juridica';

@Component({
    selector: 'jhi-advogado-dg-coordenacao-update',
    templateUrl: './advogado-dg-coordenacao-update.component.html'
})
export class AdvogadoDgCoordenacaoUpdateComponent implements OnInit {
    private _advogadoDgCoordenacao: AdvogadoDgCoordenacao;
    isSaving: boolean;

    advogados: Advogado[];

    coordenacaojuridicas: CoordenacaoJuridica[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private advogadoDgCoordenacaoService: AdvogadoDgCoordenacaoService,
        private advogadoService: AdvogadoService,
        private coordenacaoJuridicaService: CoordenacaoJuridicaService,
        private activatedRoute: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({advogadoDgCoordenacao}) => {
            this.advogadoDgCoordenacao = advogadoDgCoordenacao;
        });
        this.advogadoService.query().subscribe(
            (res: HttpResponse<Advogado[]>) => {
                this.advogados = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.coordenacaoJuridicaService.query().subscribe(
            (res: HttpResponse<CoordenacaoJuridica[]>) => {
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

    private subscribeToSaveResponse(result: Observable<HttpResponse<AdvogadoDgCoordenacao>>) {
        result.subscribe(
            (res: HttpResponse<AdvogadoDgCoordenacao>) => this.onSaveSuccess(),
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

    trackAdvogadoById(index: number, item: Advogado) {
        return item.id;
    }

    trackCoordenacaoJuridicaById(index: number, item: CoordenacaoJuridica) {
        return item.id;
    }

    get advogadoDgCoordenacao() {
        return this._advogadoDgCoordenacao;
    }

    set advogadoDgCoordenacao(advogadoDgCoordenacao: AdvogadoDgCoordenacao) {
        this._advogadoDgCoordenacao = advogadoDgCoordenacao;
    }
}
