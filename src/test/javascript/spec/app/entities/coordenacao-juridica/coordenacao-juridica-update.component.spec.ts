/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { CoordenacaoJuridicaUpdateComponent } from 'app/entities/coordenacao-juridica/coordenacao-juridica-update.component';
import { CoordenacaoJuridicaService } from 'app/entities/coordenacao-juridica/coordenacao-juridica.service';
import { CoordenacaoJuridica } from 'app/shared/model/coordenacao-juridica.model';

describe('Component Tests', () => {
    describe('CoordenacaoJuridica Management Update Component', () => {
        let comp: CoordenacaoJuridicaUpdateComponent;
        let fixture: ComponentFixture<CoordenacaoJuridicaUpdateComponent>;
        let service: CoordenacaoJuridicaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [CoordenacaoJuridicaUpdateComponent]
            })
                .overrideTemplate(CoordenacaoJuridicaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CoordenacaoJuridicaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoordenacaoJuridicaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CoordenacaoJuridica(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.coordenacaoJuridica = entity;
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
                    const entity = new CoordenacaoJuridica();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.coordenacaoJuridica = entity;
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
