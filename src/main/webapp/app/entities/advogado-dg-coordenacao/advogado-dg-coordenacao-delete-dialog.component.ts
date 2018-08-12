import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';
import { AdvogadoDgCoordenacaoService } from './advogado-dg-coordenacao.service';

@Component({
    selector: 'jhi-advogado-dg-coordenacao-delete-dialog',
    templateUrl: './advogado-dg-coordenacao-delete-dialog.component.html'
})
export class AdvogadoDgCoordenacaoDeleteDialogComponent {
    advogadoDgCoordenacao: IAdvogadoDgCoordenacao;

    constructor(
        private advogadoDgCoordenacaoService: AdvogadoDgCoordenacaoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.advogadoDgCoordenacaoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'advogadoDgCoordenacaoListModification',
                content: 'Deleted an advogadoDgCoordenacao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-advogado-dg-coordenacao-delete-popup',
    template: ''
})
export class AdvogadoDgCoordenacaoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advogadoDgCoordenacao }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdvogadoDgCoordenacaoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.advogadoDgCoordenacao = advogadoDgCoordenacao;
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
