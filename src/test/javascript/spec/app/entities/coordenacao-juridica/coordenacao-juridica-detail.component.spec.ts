/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServicosJuridicosTestModule } from '../../../test.module';
import { CoordenacaoJuridicaDetailComponent } from 'app/entities/coordenacao-juridica/coordenacao-juridica-detail.component';
import { CoordenacaoJuridica } from 'app/shared/model/coordenacao-juridica.model';

describe('Component Tests', () => {
    describe('CoordenacaoJuridica Management Detail Component', () => {
        let comp: CoordenacaoJuridicaDetailComponent;
        let fixture: ComponentFixture<CoordenacaoJuridicaDetailComponent>;
        const route = ({ data: of({ coordenacaoJuridica: new CoordenacaoJuridica(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [CoordenacaoJuridicaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CoordenacaoJuridicaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CoordenacaoJuridicaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.coordenacaoJuridica).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
