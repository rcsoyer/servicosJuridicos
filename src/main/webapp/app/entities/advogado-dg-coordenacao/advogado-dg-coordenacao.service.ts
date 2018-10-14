import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { AdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';

type EntityResponseType = HttpResponse<AdvogadoDgCoordenacao>;
type EntityArrayResponseType = HttpResponse<AdvogadoDgCoordenacao[]>;

@Injectable({ providedIn: 'root' })
export class AdvogadoDgCoordenacaoService {
    private resourceUrl = SERVER_API_URL + 'api/advogado-dg-coordenacaos';

    constructor(private http: HttpClient) {}

    create(advogadoDgCoordenacao: AdvogadoDgCoordenacao): Observable<EntityResponseType> {
        return this.http.post<AdvogadoDgCoordenacao>(this.resourceUrl, advogadoDgCoordenacao, { observe: 'response' });
    }

    update(advogadoDgCoordenacao: AdvogadoDgCoordenacao): Observable<EntityResponseType> {
        return this.http.put<AdvogadoDgCoordenacao>(this.resourceUrl, advogadoDgCoordenacao, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AdvogadoDgCoordenacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<AdvogadoDgCoordenacao[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
