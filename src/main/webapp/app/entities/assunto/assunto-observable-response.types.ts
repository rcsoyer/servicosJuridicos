import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Assunto } from '../../shared/model/assunto.model';

export type AssuntoObservResponse = Observable<HttpResponse<Assunto>>;
export type AssuntoArrayObservResponse = Observable<HttpResponse<Assunto[]>>;
