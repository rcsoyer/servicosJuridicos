import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';

type EntityResponseType = HttpResponse<IAdvogadoDgCoordenacao>;
type EntityArrayResponseType = HttpResponse<IAdvogadoDgCoordenacao[]>;

@Injectable({ providedIn: 'root' })
export class AdvogadoDgCoordenacaoService {
    private resourceUrl = SERVER_API_URL + 'api/advogado-dg-coordenacaos';

    constructor(private http: HttpClient) {}

    create(advogadoDgCoordenacao: IAdvogadoDgCoordenacao): Observable<EntityResponseType> {
        return this.http.post<IAdvogadoDgCoordenacao>(this.resourceUrl, advogadoDgCoordenacao, { observe: 'response' });
    }

    update(advogadoDgCoordenacao: IAdvogadoDgCoordenacao): Observable<EntityResponseType> {
        return this.http.put<IAdvogadoDgCoordenacao>(this.resourceUrl, advogadoDgCoordenacao, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdvogadoDgCoordenacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdvogadoDgCoordenacao[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
