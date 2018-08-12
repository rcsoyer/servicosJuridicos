import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdvogado } from 'app/shared/model/advogado.model';

type EntityResponseType = HttpResponse<IAdvogado>;
type EntityArrayResponseType = HttpResponse<IAdvogado[]>;

@Injectable({ providedIn: 'root' })
export class AdvogadoService {
    private resourceUrl = SERVER_API_URL + 'api/advogados';

    constructor(private http: HttpClient) {}

    create(advogado: IAdvogado): Observable<EntityResponseType> {
        return this.http.post<IAdvogado>(this.resourceUrl, advogado, { observe: 'response' });
    }

    update(advogado: IAdvogado): Observable<EntityResponseType> {
        return this.http.put<IAdvogado>(this.resourceUrl, advogado, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdvogado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdvogado[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
