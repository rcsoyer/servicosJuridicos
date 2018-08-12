import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModalidade } from 'app/shared/model/modalidade.model';

type EntityResponseType = HttpResponse<IModalidade>;
type EntityArrayResponseType = HttpResponse<IModalidade[]>;

@Injectable({ providedIn: 'root' })
export class ModalidadeService {
    private resourceUrl = SERVER_API_URL + 'api/modalidades';

    constructor(private http: HttpClient) {}

    create(modalidade: IModalidade): Observable<EntityResponseType> {
        return this.http.post<IModalidade>(this.resourceUrl, modalidade, { observe: 'response' });
    }

    update(modalidade: IModalidade): Observable<EntityResponseType> {
        return this.http.put<IModalidade>(this.resourceUrl, modalidade, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IModalidade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IModalidade[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
