/* tslint:disable max-line-length */
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {of} from 'rxjs';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Data} from '@angular/router';

import {ServicosJuridicosTestModule} from '../../../test.module';
import {ProcessoJudicialComponent} from 'app/entities/processo-judicial/processo-judicial.component';
import {ProcessoJudicialService} from 'app/entities/processo-judicial/processo-judicial.service';
import {ProcessoJudicial} from 'app/shared/model/processo-judicial.model';

describe('Component Tests', () => {
    describe('ProcessoJudicial Management Component', () => {
        let comp: ProcessoJudicialComponent;
        let fixture: ComponentFixture<ProcessoJudicialComponent>;
        let service: ProcessoJudicialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ServicosJuridicosTestModule],
                declarations: [ProcessoJudicialComponent],
                providers: [
                    {
                        provide: ActivatedRoute,
                        useValue: {
                            data: {
                                subscribe: (fn: (value: Data) => void) =>
                                    fn({
                                        pagingParams: {
                                            predicate: 'id',
                                            reverse: false,
                                            page: 0
                                        }
                                    })
                            }
                        }
                    }
                ]
            })
            .overrideTemplate(ProcessoJudicialComponent, '')
            .compileComponents();

            fixture = TestBed.createComponent(ProcessoJudicialComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcessoJudicialService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProcessoJudicial(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.listResultQuery[0]).toEqual(jasmine.objectContaining({id: 123}));
        });

        it('should load a page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProcessoJudicial(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.listResultQuery[0]).toEqual(jasmine.objectContaining({id: 123}));
        });

        it('should not load a page is the page is the same as the previous page', () => {
            spyOn(service, 'query').and.callThrough();

            // WHEN
            comp.loadPage(0);

            // THEN
            expect(service.query).toHaveBeenCalledTimes(0);
        });

        it('should re-initialize the page', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProcessoJudicial(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.loadPage(1);
            comp.clear();

            // THEN
            expect(comp.page).toEqual(0);
            expect(service.query).toHaveBeenCalledTimes(2);
            expect(comp.listResultQuery[0]).toEqual(jasmine.objectContaining({id: 123}));
        });
    });
});
