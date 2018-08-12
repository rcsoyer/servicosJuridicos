/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { ProcessoJudicialDeleteDialogComponent } from 'app/entities/processo-judicial/processo-judicial-delete-dialog.component';
import { ProcessoJudicialService } from 'app/entities/processo-judicial/processo-judicial.service';

describe('Component Tests', () => {
    describe('ProcessoJudicial Management Delete Component', () => {
        let comp: ProcessoJudicialDeleteDialogComponent;
        let fixture: ComponentFixture<ProcessoJudicialDeleteDialogComponent>;
        let service: ProcessoJudicialService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [ProcessoJudicialDeleteDialogComponent]
            })
                .overrideTemplate(ProcessoJudicialDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProcessoJudicialDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcessoJudicialService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
