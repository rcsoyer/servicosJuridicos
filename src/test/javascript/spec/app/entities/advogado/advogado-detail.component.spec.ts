/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AdvogadoDetailComponent } from 'app/entities/advogado/advogado-detail.component';
import { Advogado } from 'app/shared/model/advogado.model';

describe('Component Tests', () => {
    describe('Advogado Management Detail Component', () => {
        let comp: AdvogadoDetailComponent;
        let fixture: ComponentFixture<AdvogadoDetailComponent>;
        const route = ({ data: of({ advogado: new Advogado(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AdvogadoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdvogadoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdvogadoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.advogado).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
