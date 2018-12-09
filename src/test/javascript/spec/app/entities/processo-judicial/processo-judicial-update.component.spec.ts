/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { ProcessoJudicialUpdateComponent } from 'app/entities/processo-judicial/processo-judicial-update.component';
import { ProcessoJudicialService } from 'app/entities/processo-judicial/processo-judicial.service';
import { ProcessoJudicial } from 'app/shared/model/processo-judicial.model';

describe('Component Tests', () => {
    describe('ProcessoJudicial Management Update Component', () => {
        let comp: ProcessoJudicialUpdateComponent;
        let fixture: ComponentFixture<ProcessoJudicialUpdateComponent>;
        let service: ProcessoJudicialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [ProcessoJudicialUpdateComponent]
            })
                .overrideTemplate(ProcessoJudicialUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProcessoJudicialUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcessoJudicialService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProcessoJudicial(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.processoJudicial = entity;
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
                    const entity = new ProcessoJudicial();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.processoJudicial = entity;
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
