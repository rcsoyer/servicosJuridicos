/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AssuntoDetailComponent } from 'app/entities/assunto/assunto-detail.component';
import { Assunto } from 'app/shared/model/assunto.model';

describe('Component Tests', () => {
    describe('Assunto Management Detail Component', () => {
        let comp: AssuntoDetailComponent;
        let fixture: ComponentFixture<AssuntoDetailComponent>;
        const route = ({ data: of({ assunto: new Assunto(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AssuntoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AssuntoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AssuntoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.assunto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
