import {HttpClient, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';
import {createRequestOption} from '../../shared';
import {CoordenacaoJuridica} from '../../shared/model/coordenacao-juridica.model';
import {BasicService} from '../../shared/service-commons/basic-service.service';
import {buildQueryParams} from '../../shared/service-commons/build-query-params-func';
import {MaskNumberUtils} from '../../shared/util/masknumber-utils/masknumber-utils';

type EntityResponseType = HttpResponse<CoordenacaoJuridica>;
type EntityArrayResponseType = HttpResponse<CoordenacaoJuridica[]>;

@Injectable({providedIn: 'root'})
export class CoordenacaoJuridicaService implements BasicService<CoordenacaoJuridica> {

    private readonly baseApiURL = SERVER_API_URL + 'api/';
    private readonly resourceUrl = this.baseApiURL + 'coordenacao-juridica';

    constructor(private http: HttpClient, private maskNumberUtils: MaskNumberUtils) {
    }

    create(coordenacaoJuridica: CoordenacaoJuridica): Observable<EntityResponseType> {
        return this.http
            .post<CoordenacaoJuridica>(this.resourceUrl, coordenacaoJuridica, {observe: 'response'});
    }

    update(coordenacaoJuridica: CoordenacaoJuridica): Observable<EntityResponseType> {
        return this.http
            .put<CoordenacaoJuridica>(this.resourceUrl, coordenacaoJuridica, {observe: 'response'});
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<CoordenacaoJuridica>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<CoordenacaoJuridica[]>(this.resourceUrl, {params: options, observe: 'response'});
    }

    queryByInput(coordenacao: CoordenacaoJuridica, pageable: any): Observable<EntityArrayResponseType> {
        const path = this.baseApiURL + 'getCoordenacoes';
        const queryParams = buildQueryParams(coordenacao, pageable);
        return this.http
            .get<CoordenacaoJuridica[]>(path, {params: queryParams, observe: 'response'});
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }
}
