/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { ModalidadeUpdateComponent } from 'app/entities/modalidade/modalidade-update.component';
import { ModalidadeService } from 'app/entities/modalidade/modalidade.service';
import { Modalidade } from 'app/shared/model/modalidade.model';

describe('Component Tests', () => {
    describe('Modalidade Management Update Component', () => {
        let comp: ModalidadeUpdateComponent;
        let fixture: ComponentFixture<ModalidadeUpdateComponent>;
        let service: ModalidadeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [ModalidadeUpdateComponent]
            })
                .overrideTemplate(ModalidadeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ModalidadeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModalidadeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Modalidade(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.model = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Modalidade();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.model = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
