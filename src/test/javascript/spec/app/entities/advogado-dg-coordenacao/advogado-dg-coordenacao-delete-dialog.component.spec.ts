/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AdvogadoDgCoordenacaoDeleteDialogComponent } from 'app/entities/advogado-dg-coordenacao/advogado-dg-coordenacao-delete-dialog.component';
import { AdvogadoDgCoordenacaoService } from 'app/entities/advogado-dg-coordenacao/advogado-dg-coordenacao.service';

describe('Component Tests', () => {
    describe('AdvogadoDgCoordenacao Management Delete Component', () => {
        let comp: AdvogadoDgCoordenacaoDeleteDialogComponent;
        let fixture: ComponentFixture<AdvogadoDgCoordenacaoDeleteDialogComponent>;
        let service: AdvogadoDgCoordenacaoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AdvogadoDgCoordenacaoDeleteDialogComponent]
            })
                .overrideTemplate(AdvogadoDgCoordenacaoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdvogadoDgCoordenacaoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvogadoDgCoordenacaoService);
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
