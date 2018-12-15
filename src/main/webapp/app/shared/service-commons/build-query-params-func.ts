import {HttpParams} from '@angular/common/http';

/**
 * @author rcsoyer
 * Creates a compound HttpParams object with any generic DTO model and a pageable.
 * The returned obj can be used as para a param for a httpClient.get() method
 */
export function buildQueryParams(dto: any, pageable: any): HttpParams {
    return new HttpParams()
    .append('dto', JSON.stringify(dto))
    .append('pageable', JSON.stringify(pageable));
}
