/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { FeriasLicencaUpdateComponent } from 'app/entities/ferias-licenca/ferias-licenca-update.component';
import { FeriasLicencaService } from 'app/entities/ferias-licenca/ferias-licenca.service';
import { FeriasLicenca } from 'app/shared/model/ferias-licenca.model';

describe('Component Tests', () => {
    describe('FeriasLicenca Management Update Component', () => {
        let comp: FeriasLicencaUpdateComponent;
        let fixture: ComponentFixture<FeriasLicencaUpdateComponent>;
        let service: FeriasLicencaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [FeriasLicencaUpdateComponent]
            })
                .overrideTemplate(FeriasLicencaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FeriasLicencaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeriasLicencaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FeriasLicenca(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.feriasLicenca = entity;
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
                    const entity = new FeriasLicenca();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.feriasLicenca = entity;
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
