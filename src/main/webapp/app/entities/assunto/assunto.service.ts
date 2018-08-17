import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import { createRequestOption } from '../../shared';
import { BasicService } from '../../shared/service-commons/basic-service.service';
import { buildQueryParams } from '../../shared/service-commons/build-query-params-func';
import { Assunto } from '../../shared/model/assunto.model';

type EntityResponseType = HttpResponse<Assunto>;
type EntityArrayResponseType = HttpResponse<Assunto[]>;

@Injectable({ providedIn: 'root' })
export class AssuntoService implements BasicService<Assunto> {
    private baseApiURL: string = SERVER_API_URL + 'api/';
    private resourceUrl: string = this.baseApiURL + 'assunto';

    constructor(private http: HttpClient) {}

    create(assunto: Assunto): Observable<EntityResponseType> {
        return this.http.post<Assunto>(this.resourceUrl, assunto, { observe: 'response' });
    }

    update(assunto: Assunto): Observable<EntityResponseType> {
        return this.http.put<Assunto>(this.resourceUrl, assunto, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Assunto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Assunto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryByInput(assunto: Assunto, pageable?: any): Observable<EntityArrayResponseType> {
        const path = this.baseApiURL + 'queryAssuntos';
        const queryParams = buildQueryParams(assunto, pageable);
        return this.http.get<Assunto[]>(path, { params: queryParams, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
