/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { FeriasLicencaDetailComponent } from 'app/entities/ferias-licenca/ferias-licenca-detail.component';
import { FeriasLicenca } from 'app/shared/model/ferias-licenca.model';

describe('Component Tests', () => {
    describe('FeriasLicenca Management Detail Component', () => {
        let comp: FeriasLicencaDetailComponent;
        let fixture: ComponentFixture<FeriasLicencaDetailComponent>;
        const route = ({ data: of({ feriasLicenca: new FeriasLicenca(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [FeriasLicencaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FeriasLicencaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FeriasLicencaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.feriasLicenca).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
