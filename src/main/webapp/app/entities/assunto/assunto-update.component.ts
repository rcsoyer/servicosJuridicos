import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Assunto } from '../../shared/model/assunto.model';
import { AssuntoService } from './assunto.service';
import * as _ from 'lodash';
import { JhiAlertService } from 'ng-jhipster';
import { UpdateComponentAbastract } from '../../shared/components-abstract/update.component.abstract';

@Component({ selector: 'assunto-update', templateUrl: './assunto-update.component.html' })
export class AssuntoUpdateComponent extends UpdateComponentAbastract<Assunto> implements OnInit {
    constructor(assuntoService: AssuntoService, activatedRoute: ActivatedRoute, jhiAlertService: JhiAlertService) {
        super(assuntoService, activatedRoute, jhiAlertService);
    }

    ngOnInit() {
        this.model = new Assunto();
        this.isSaving = false;
        this.subscribeModelRoute();
    }

    createPesos(): number[] {
        return _.range(1, 6);
    }

    protected trimInputText() {
        this.model.descricao = this.model.descricao.trim();
    }
}
