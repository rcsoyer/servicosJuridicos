import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeriasLicenca } from 'app/shared/model/ferias-licenca.model';
import { FeriasLicencaService } from './ferias-licenca.service';

@Component({
    selector: 'jhi-ferias-licenca-delete-dialog',
    templateUrl: './ferias-licenca-delete-dialog.component.html'
})
export class FeriasLicencaDeleteDialogComponent {
    feriasLicenca: IFeriasLicenca;

    constructor(
        private feriasLicencaService: FeriasLicencaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.feriasLicencaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'feriasLicencaListModification',
                content: 'Deleted an feriasLicenca'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ferias-licenca-delete-popup',
    template: ''
})
export class FeriasLicencaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ feriasLicenca }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FeriasLicencaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.feriasLicenca = feriasLicenca;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
