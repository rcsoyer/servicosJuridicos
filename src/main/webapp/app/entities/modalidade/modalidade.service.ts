import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BasicService } from '../../shared/service-commons/basic-service.service';
import { SERVER_API_URL } from '../../app.constants';
import { createRequestOption } from '../../shared';
import { Modalidade } from '../../shared/model/modalidade.model';

type EntityResponseType = HttpResponse<Modalidade>;
type EntityArrayResponseType = HttpResponse<Modalidade[]>;

@Injectable({ providedIn: 'root' })
export class ModalidadeService implements BasicService<Modalidade> {
    private resourceUrl = SERVER_API_URL + 'api/modalidades';

    constructor(private http: HttpClient) { }

    create(modalidade: Modalidade): Observable<EntityResponseType> {
        return this.http.post<Modalidade>(this.resourceUrl, modalidade, { observe: 'response' });
    }

    update(modalidade: Modalidade): Observable<EntityResponseType> {
        return this.http.put<Modalidade>(this.resourceUrl, modalidade, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Modalidade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Modalidade[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    queryByInput(m: Modalidade, pag: any): Observable<EntityArrayResponseType> {
        return null;
    }
}
