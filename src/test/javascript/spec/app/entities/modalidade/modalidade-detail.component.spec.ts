/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { ModalidadeDetailComponent } from 'app/entities/modalidade/modalidade-detail.component';
import { Modalidade } from 'app/shared/model/modalidade.model';

describe('Component Tests', () => {
    describe('Modalidade Management Detail Component', () => {
        let comp: ModalidadeDetailComponent;
        let fixture: ComponentFixture<ModalidadeDetailComponent>;
        const route = ({ data: of({ modalidade: new Modalidade(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [ModalidadeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ModalidadeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ModalidadeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.modalidade).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
