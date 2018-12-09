/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AssuntoDeleteDialogComponent } from 'app/entities/assunto/assunto-delete-dialog.component';
import { AssuntoService } from 'app/entities/assunto/assunto.service';

describe('Component Tests', () => {
    describe('Assunto Management Delete Component', () => {
        let comp: AssuntoDeleteDialogComponent;
        let fixture: ComponentFixture<AssuntoDeleteDialogComponent>;
        let service: AssuntoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AssuntoDeleteDialogComponent]
            })
                .overrideTemplate(AssuntoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AssuntoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssuntoService);
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
