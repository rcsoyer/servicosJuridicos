import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

/** @author rcsoyer */
export interface BasicService<M> {
    create(model: M): Observable<HttpResponse<M>>;

    update(model: M): Observable<HttpResponse<M>>;

    find(id: number): Observable<HttpResponse<M>>;

    query(req?: any): Observable<HttpResponse<M[]>>;

    queryByInput(model: M, pageable: any): Observable<HttpResponse<M[]>>;

    delete(id: number): Observable<HttpResponse<any>>;
}
