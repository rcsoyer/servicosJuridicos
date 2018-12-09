import {HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {BaseEntity} from 'app/shared/model/base-entity';

/** @author rcsoyer */
export interface BasicService<M extends BaseEntity> {

    create(model: M): Observable<HttpResponse<M>>;

    update(model: M): Observable<HttpResponse<M>>;

    find(id: number): Observable<HttpResponse<M>>;

    query(req?: any): Observable<HttpResponse<M[]>>;

    queryByInput(model: M, pageable: any): Observable<HttpResponse<M[]>>;

    delete(id: number): Observable<HttpResponse<any>>;
}
