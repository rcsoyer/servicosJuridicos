/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { FeriasLicencaDeleteDialogComponent } from 'app/entities/ferias-licenca/ferias-licenca-delete-dialog.component';
import { FeriasLicencaService } from 'app/entities/ferias-licenca/ferias-licenca.service';

describe('Component Tests', () => {
    describe('FeriasLicenca Management Delete Component', () => {
        let comp: FeriasLicencaDeleteDialogComponent;
        let fixture: ComponentFixture<FeriasLicencaDeleteDialogComponent>;
        let service: FeriasLicencaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [FeriasLicencaDeleteDialogComponent]
            })
                .overrideTemplate(FeriasLicencaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FeriasLicencaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeriasLicencaService);
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
