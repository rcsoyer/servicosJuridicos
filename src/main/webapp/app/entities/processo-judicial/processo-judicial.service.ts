import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import * as moment from 'moment';
import {DATE_FORMAT} from 'app/shared/constants/input.constants';
import {map} from 'rxjs/operators';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared';
import {ProcessoJudicial} from 'app/shared/model/processo-judicial.model';
import {BasicService} from 'app/shared/service-commons/basic-service.service';

type EntityResponseType = HttpResponse<ProcessoJudicial>;
type EntityArrayResponseType = HttpResponse<ProcessoJudicial[]>;

@Injectable({providedIn: 'root'})
export class ProcessoJudicialService implements BasicService<ProcessoJudicial> {

    private resourceUrl = SERVER_API_URL + 'api/processo-judicials';

    constructor(private http: HttpClient) {
    }

    create(processoJudicial: ProcessoJudicial): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(processoJudicial);
        return this.http
        .post<ProcessoJudicial>(this.resourceUrl, copy, {observe: 'response'})
        .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(processoJudicial: ProcessoJudicial): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(processoJudicial);
        return this.http
        .put<ProcessoJudicial>(this.resourceUrl, copy, {observe: 'response'})
        .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
        .get<ProcessoJudicial>(`${this.resourceUrl}/${id}`, {observe: 'response'})
        .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
        .get<ProcessoJudicial[]>(this.resourceUrl, {params: options, observe: 'response'})
        .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    private convertDateFromClient(processoJudicial: ProcessoJudicial): ProcessoJudicial {
        const copy: ProcessoJudicial = Object.assign({}, processoJudicial, {
            prazoFinal:
                processoJudicial.prazoFinal != null && processoJudicial.prazoFinal.isValid()
                    ? processoJudicial.prazoFinal.format(DATE_FORMAT)
                    : null,
            dtAtribuicao:
                processoJudicial.dtAtribuicao != null && processoJudicial.dtAtribuicao.isValid()
                    ? processoJudicial.dtAtribuicao.format(DATE_FORMAT)
                    : null,
            dtInicio:
                processoJudicial.dtInicio != null && processoJudicial.dtInicio.isValid()
                    ? processoJudicial.dtInicio.format(DATE_FORMAT)
                    : null,
            dtConclusao:
                processoJudicial.dtConclusao != null && processoJudicial.dtConclusao.isValid()
                    ? processoJudicial.dtConclusao.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.prazoFinal = res.body.prazoFinal != null ? moment(res.body.prazoFinal) : null;
        res.body.dtAtribuicao = res.body.dtAtribuicao != null ? moment(res.body.dtAtribuicao) : null;
        res.body.dtInicio = res.body.dtInicio != null ? moment(res.body.dtInicio) : null;
        res.body.dtConclusao = res.body.dtConclusao != null ? moment(res.body.dtConclusao) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((processoJudicial: ProcessoJudicial) => {
            processoJudicial.prazoFinal = processoJudicial.prazoFinal != null ? moment(processoJudicial.prazoFinal) : null;
            processoJudicial.dtAtribuicao = processoJudicial.dtAtribuicao != null ? moment(processoJudicial.dtAtribuicao) : null;
            processoJudicial.dtInicio = processoJudicial.dtInicio != null ? moment(processoJudicial.dtInicio) : null;
            processoJudicial.dtConclusao = processoJudicial.dtConclusao != null ? moment(processoJudicial.dtConclusao) : null;
        });
        return res;
    }

    queryByInput(model: ProcessoJudicial, pageable: any): Observable<HttpResponse<ProcessoJudicial[]>> {
        return undefined;
    }
}
