import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared';
import {Advogado} from 'app/shared/model/advogado.model';
import {BasicService} from '../../shared/service-commons/basic-service.service';
import {buildQueryParams} from 'app/shared/service-commons/build-query-params-func';
import {CpfMaskUtils} from 'app/shared/util/cpf/cpf-mask-utils';

type EntityResponseType = HttpResponse<Advogado>;
type EntityArrayResponseType = HttpResponse<Advogado[]>;

@Injectable({providedIn: 'root'})
export class AdvogadoService implements BasicService<Advogado> {

    private readonly baseApiURL = SERVER_API_URL + 'api/';
    private readonly resourceUrl = this.baseApiURL + 'advogado';

    constructor(private http: HttpClient, private cpfMaskUtils: CpfMaskUtils) {
    }

    create(advogado: Advogado): Observable<EntityResponseType> {
        advogado.cpf = this.cpfMaskUtils.removeMask(advogado.cpf);
        return this.http.post<Advogado>(this.resourceUrl, advogado, {observe: 'response'});
    }

    update(advogado: Advogado): Observable<EntityResponseType> {
        advogado.cpf = this.cpfMaskUtils.removeMask(advogado.cpf);
        return this.http.put<Advogado>(this.resourceUrl, advogado, {observe: 'response'});
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Advogado>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<Advogado[]>(this.resourceUrl, {params: options, observe: 'response'});
    }

    queryByInput(advogado: Advogado, pageable?: any): Observable<EntityArrayResponseType> {
        const path = this.baseApiURL + 'queryAdvogados';
        advogado.cpf = this.cpfMaskUtils.removeMask(advogado.cpf);
        const queryParams = buildQueryParams(advogado, pageable);
        return this.http.get<Advogado[]>(path, {params: queryParams, observe: 'response'});
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }
}
