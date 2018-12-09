import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Advogado } from 'app/shared/model/advogado.model';
import { AdvogadoService } from './advogado.service';

@Component({
    selector: 'jhi-advogado-delete-dialog',
    templateUrl: './advogado-delete-dialog.component.html'
})
export class AdvogadoDeleteDialogComponent {
    advogado: Advogado;

    constructor(private advogadoService: AdvogadoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.advogadoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'advogadoListModification',
                content: 'Deleted an advogado'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-advogado-delete-popup',
    template: ''
})
export class AdvogadoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advogado }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdvogadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.advogado = advogado;
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
