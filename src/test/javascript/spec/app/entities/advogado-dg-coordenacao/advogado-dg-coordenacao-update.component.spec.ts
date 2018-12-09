/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AdvogadoDgCoordenacaoUpdateComponent } from 'app/entities/advogado-dg-coordenacao/advogado-dg-coordenacao-update.component';
import { AdvogadoDgCoordenacaoService } from 'app/entities/advogado-dg-coordenacao/advogado-dg-coordenacao.service';
import { AdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';

describe('Component Tests', () => {
    describe('AdvogadoDgCoordenacao Management Update Component', () => {
        let comp: AdvogadoDgCoordenacaoUpdateComponent;
        let fixture: ComponentFixture<AdvogadoDgCoordenacaoUpdateComponent>;
        let service: AdvogadoDgCoordenacaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AdvogadoDgCoordenacaoUpdateComponent]
            })
                .overrideTemplate(AdvogadoDgCoordenacaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdvogadoDgCoordenacaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvogadoDgCoordenacaoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AdvogadoDgCoordenacao(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.advogadoDgCoordenacao = entity;
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
                    const entity = new AdvogadoDgCoordenacao();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.advogadoDgCoordenacao = entity;
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
