/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AdvogadoDeleteDialogComponent } from 'app/entities/advogado/advogado-delete-dialog.component';
import { AdvogadoService } from 'app/entities/advogado/advogado.service';

describe('Component Tests', () => {
    describe('Advogado Management Delete Component', () => {
        let comp: AdvogadoDeleteDialogComponent;
        let fixture: ComponentFixture<AdvogadoDeleteDialogComponent>;
        let service: AdvogadoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AdvogadoDeleteDialogComponent]
            })
                .overrideTemplate(AdvogadoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdvogadoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvogadoService);
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
