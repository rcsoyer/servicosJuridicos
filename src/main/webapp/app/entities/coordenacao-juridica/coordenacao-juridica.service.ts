import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICoordenacaoJuridica } from 'app/shared/model/coordenacao-juridica.model';

type EntityResponseType = HttpResponse<ICoordenacaoJuridica>;
type EntityArrayResponseType = HttpResponse<ICoordenacaoJuridica[]>;

@Injectable({ providedIn: 'root' })
export class CoordenacaoJuridicaService {
    private resourceUrl = SERVER_API_URL + 'api/coordenacao-juridicas';

    constructor(private http: HttpClient) {}

    create(coordenacaoJuridica: ICoordenacaoJuridica): Observable<EntityResponseType> {
        return this.http.post<ICoordenacaoJuridica>(this.resourceUrl, coordenacaoJuridica, { observe: 'response' });
    }

    update(coordenacaoJuridica: ICoordenacaoJuridica): Observable<EntityResponseType> {
        return this.http.put<ICoordenacaoJuridica>(this.resourceUrl, coordenacaoJuridica, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICoordenacaoJuridica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICoordenacaoJuridica[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
