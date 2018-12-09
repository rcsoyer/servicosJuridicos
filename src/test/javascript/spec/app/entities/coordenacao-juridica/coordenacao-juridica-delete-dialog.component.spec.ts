/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { CoordenacaoJuridicaDeleteDialogComponent } from 'app/entities/coordenacao-juridica/coordenacao-juridica-delete-dialog.component';
import { CoordenacaoJuridicaService } from 'app/entities/coordenacao-juridica/coordenacao-juridica.service';

describe('Component Tests', () => {
    describe('CoordenacaoJuridica Management Delete Component', () => {
        let comp: CoordenacaoJuridicaDeleteDialogComponent;
        let fixture: ComponentFixture<CoordenacaoJuridicaDeleteDialogComponent>;
        let service: CoordenacaoJuridicaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [CoordenacaoJuridicaDeleteDialogComponent]
            })
                .overrideTemplate(CoordenacaoJuridicaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CoordenacaoJuridicaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoordenacaoJuridicaService);
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
