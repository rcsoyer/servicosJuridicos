import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAssunto } from 'app/shared/model/assunto.model';

type EntityResponseType = HttpResponse<IAssunto>;
type EntityArrayResponseType = HttpResponse<IAssunto[]>;

@Injectable({ providedIn: 'root' })
export class AssuntoService {
    private resourceUrl = SERVER_API_URL + 'api/assuntos';

    constructor(private http: HttpClient) {}

    create(assunto: IAssunto): Observable<EntityResponseType> {
        return this.http.post<IAssunto>(this.resourceUrl, assunto, { observe: 'response' });
    }

    update(assunto: IAssunto): Observable<EntityResponseType> {
        return this.http.put<IAssunto>(this.resourceUrl, assunto, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAssunto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAssunto[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
