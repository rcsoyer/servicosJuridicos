import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {IMultiSelectOption} from 'angular-2-dropdown-multiselect';
import {JhiAlertService} from 'ng-jhipster';
import {Assunto} from '../../shared/model/assunto.model';
import {AssuntoService} from '../assunto';

@Injectable({providedIn: 'root'})
export class AssuntosOptions {

    private assuntos: Assunto[];
    private _assuntoSelectOptions: IMultiSelectOption[];

    constructor(private assuntoService: AssuntoService, private jhiAlertService: JhiAlertService) {
    }

    queryAllAssuntos(): void {
        this.assuntoService.query()
            .subscribe(this.assuntosQueryResponse(), this.onQueryError());
    }

    private onQueryError() {
        return (res: HttpErrorResponse) => this.jhiAlertService.error(res.message, null, null);
    }

    private assuntosQueryResponse() {
        return (res: HttpResponse<Assunto[]>) => {
            this.assuntos = res.body;
            this.setAssuntoSelectOptions();
        };
    }

    private setAssuntoSelectOptions() {
        const selectOptions = [];
        this.assuntos.forEach(assunto => selectOptions.push({id: assunto.id, name: assunto.descricao}));
        this._assuntoSelectOptions = selectOptions;
    }

    get assuntoSelectOptions() {
        return this._assuntoSelectOptions;
    }
}
