/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdvogadoDgCoordenacaoService } from 'app/entities/advogado-dg-coordenacao/advogado-dg-coordenacao.service';
import { AdvogadoDgCoordenacao } from 'app/shared/model/advogado-dg-coordenacao.model';
import { SERVER_API_URL } from 'app/app.constants';

describe('Service Tests', () => {
    describe('AdvogadoDgCoordenacao Service', () => {
        let injector: TestBed;
        let service: AdvogadoDgCoordenacaoService;
        let httpMock: HttpTestingController;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AdvogadoDgCoordenacaoService);
            httpMock = injector.get(HttpTestingController);
        });

        describe('Service methods', () => {
            it('should call correct URL', () => {
                service.find(123).subscribe(() => {});

                const req = httpMock.expectOne({ method: 'GET' });

                const resourceUrl = SERVER_API_URL + 'api/advogado-dg-coordenacaos';
                expect(req.request.url).toEqual(resourceUrl + '/' + 123);
            });

            it('should create a AdvogadoDgCoordenacao', () => {
                service.create(new AdvogadoDgCoordenacao(null)).subscribe(received => {
                    expect(received.body.id).toEqual(null);
                });

                const req = httpMock.expectOne({ method: 'POST' });
                req.flush({ id: null });
            });

            it('should update a AdvogadoDgCoordenacao', () => {
                service.update(new AdvogadoDgCoordenacao(123)).subscribe(received => {
                    expect(received.body.id).toEqual(123);
                });

                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush({ id: 123 });
            });

            it('should return a AdvogadoDgCoordenacao', () => {
                service.find(123).subscribe(received => {
                    expect(received.body.id).toEqual(123);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush({ id: 123 });
            });

            it('should return a list of AdvogadoDgCoordenacao', () => {
                service.query(null).subscribe(received => {
                    expect(received.body[0].id).toEqual(123);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush([new AdvogadoDgCoordenacao(123)]);
            });

            it('should delete a AdvogadoDgCoordenacao', () => {
                service.delete(123).subscribe(received => {
                    expect(received.url).toContain('/' + 123);
                });

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush(null);
            });

            it('should propagate not found response', () => {
                service.find(123).subscribe(null, (_error: any) => {
                    expect(_error.status).toEqual(404);
                });

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush('Invalid request parameters', {
                    status: 404,
                    statusText: 'Bad Request'
                });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
