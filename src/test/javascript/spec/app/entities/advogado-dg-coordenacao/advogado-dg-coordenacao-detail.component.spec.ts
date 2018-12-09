/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { AdvogadoDgCoordenacaoDetailComponent } from 'app/entities/advogado-dg-coordenacao/advogado-dg-coordenacao-detail.component';
import { AdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';

describe('Component Tests', () => {
    describe('AdvogadoDgCoordenacao Management Detail Component', () => {
        let comp: AdvogadoDgCoordenacaoDetailComponent;
        let fixture: ComponentFixture<AdvogadoDgCoordenacaoDetailComponent>;
        const route = ({ data: of({ advogadoDgCoordenacao: new AdvogadoDgCoordenacao(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [AdvogadoDgCoordenacaoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdvogadoDgCoordenacaoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdvogadoDgCoordenacaoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.advogadoDgCoordenacao).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
