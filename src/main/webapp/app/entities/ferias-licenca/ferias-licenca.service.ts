import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { FeriasLicenca } from 'app/shared/model/ferias-licenca.model';

type EntityResponseType = HttpResponse<FeriasLicenca>;
type EntityArrayResponseType = HttpResponse<FeriasLicenca[]>;

@Injectable({ providedIn: 'root' })
export class FeriasLicencaService {
    private resourceUrl = SERVER_API_URL + 'api/ferias-licencas';

    constructor(private http: HttpClient) {}

    create(feriasLicenca: FeriasLicenca): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(feriasLicenca);
        return this.http
            .post<FeriasLicenca>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(feriasLicenca: FeriasLicenca): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(feriasLicenca);
        return this.http
            .put<FeriasLicenca>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<FeriasLicenca>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<FeriasLicenca[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(feriasLicenca: FeriasLicenca): FeriasLicenca {
        const copy: FeriasLicenca = Object.assign({}, feriasLicenca, {
            dtInicio:
                feriasLicenca.dtInicio != null && feriasLicenca.dtInicio.isValid() ? feriasLicenca.dtInicio.format(DATE_FORMAT) : null,
            dtFim: feriasLicenca.dtFim != null && feriasLicenca.dtFim.isValid() ? feriasLicenca.dtFim.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dtInicio = res.body.dtInicio != null ? moment(res.body.dtInicio) : null;
        res.body.dtFim = res.body.dtFim != null ? moment(res.body.dtFim) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((feriasLicenca: FeriasLicenca) => {
            feriasLicenca.dtInicio = feriasLicenca.dtInicio != null ? moment(feriasLicenca.dtInicio) : null;
            feriasLicenca.dtFim = feriasLicenca.dtFim != null ? moment(feriasLicenca.dtFim) : null;
        });
        return res;
    }
}
