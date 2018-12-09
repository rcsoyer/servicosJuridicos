/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { ProcessoJudicialDetailComponent } from 'app/entities/processo-judicial/processo-judicial-detail.component';
import { ProcessoJudicial } from 'app/shared/model/processo-judicial.model';

describe('Component Tests', () => {
    describe('ProcessoJudicial Management Detail Component', () => {
        let comp: ProcessoJudicialDetailComponent;
        let fixture: ComponentFixture<ProcessoJudicialDetailComponent>;
        const route = ({ data: of({ processoJudicial: new ProcessoJudicial(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [ProcessoJudicialDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProcessoJudicialDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProcessoJudicialDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.processoJudicial).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
