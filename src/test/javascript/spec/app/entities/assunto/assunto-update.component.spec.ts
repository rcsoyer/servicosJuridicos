/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AssuntoUpdateComponent } from 'app/entities/assunto/assunto-update.component';
import { AssuntoService } from 'app/entities/assunto/assunto.service';
import { Assunto } from 'app/shared/model/assunto.model';

describe('Component Tests', () => {
    describe('Assunto Management Update Component', () => {
        let comp: AssuntoUpdateComponent;
        let fixture: ComponentFixture<AssuntoUpdateComponent>;
        let service: AssuntoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AssuntoUpdateComponent]
            })
                .overrideTemplate(AssuntoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AssuntoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssuntoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Assunto(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.assunto = entity;
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
                    const entity = new Assunto();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.assunto = entity;
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
