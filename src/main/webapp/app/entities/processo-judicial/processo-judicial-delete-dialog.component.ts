import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProcessoJudicial } from 'app/shared/model/processo-judicial.model';
import { ProcessoJudicialService } from './processo-judicial.service';

@Component({
    selector: 'jhi-processo-judicial-delete-dialog',
    templateUrl: './processo-judicial-delete-dialog.component.html'
})
export class ProcessoJudicialDeleteDialogComponent {
    processoJudicial: ProcessoJudicial;

    constructor(
        private processoJudicialService: ProcessoJudicialService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.processoJudicialService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'processoJudicialListModification',
                content: 'Deleted an processoJudicial'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-processo-judicial-delete-popup',
    template: ''
})
export class ProcessoJudicialDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ processoJudicial }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProcessoJudicialDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.processoJudicial = processoJudicial;
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
