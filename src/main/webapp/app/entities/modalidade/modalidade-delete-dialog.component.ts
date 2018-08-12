import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModalidade } from 'app/shared/model/modalidade.model';
import { ModalidadeService } from './modalidade.service';

@Component({
    selector: 'jhi-modalidade-delete-dialog',
    templateUrl: './modalidade-delete-dialog.component.html'
})
export class ModalidadeDeleteDialogComponent {
    modalidade: IModalidade;

    constructor(private modalidadeService: ModalidadeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.modalidadeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'modalidadeListModification',
                content: 'Deleted an modalidade'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-modalidade-delete-popup',
    template: ''
})
export class ModalidadeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ modalidade }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ModalidadeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.modalidade = modalidade;
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
