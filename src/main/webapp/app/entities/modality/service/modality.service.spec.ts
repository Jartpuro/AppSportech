import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IModality, Modality } from '../modality.model';

import { ModalityService } from './modality.service';

describe('Modality Service', () => {
  let service: ModalityService;
  let httpMock: HttpTestingController;
  let elemDefault: IModality;
  let expectedResult: IModality | IModality[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ModalityService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      modalityName: 'AAAAAAA',
      modalityColor: 'AAAAAAA',
      modalityState: State.ACTIVE,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Modality', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Modality()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Modality', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          modalityName: 'BBBBBB',
          modalityColor: 'BBBBBB',
          modalityState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Modality', () => {
      const patchObject = Object.assign({}, new Modality());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Modality', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          modalityName: 'BBBBBB',
          modalityColor: 'BBBBBB',
          modalityState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Modality', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addModalityToCollectionIfMissing', () => {
      it('should add a Modality to an empty array', () => {
        const modality: IModality = { id: 123 };
        expectedResult = service.addModalityToCollectionIfMissing([], modality);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modality);
      });

      it('should not add a Modality to an array that contains it', () => {
        const modality: IModality = { id: 123 };
        const modalityCollection: IModality[] = [
          {
            ...modality,
          },
          { id: 456 },
        ];
        expectedResult = service.addModalityToCollectionIfMissing(modalityCollection, modality);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Modality to an array that doesn't contain it", () => {
        const modality: IModality = { id: 123 };
        const modalityCollection: IModality[] = [{ id: 456 }];
        expectedResult = service.addModalityToCollectionIfMissing(modalityCollection, modality);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modality);
      });

      it('should add only unique Modality to an array', () => {
        const modalityArray: IModality[] = [{ id: 123 }, { id: 456 }, { id: 6278 }];
        const modalityCollection: IModality[] = [{ id: 123 }];
        expectedResult = service.addModalityToCollectionIfMissing(modalityCollection, ...modalityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const modality: IModality = { id: 123 };
        const modality2: IModality = { id: 456 };
        expectedResult = service.addModalityToCollectionIfMissing([], modality, modality2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(modality);
        expect(expectedResult).toContain(modality2);
      });

      it('should accept null and undefined values', () => {
        const modality: IModality = { id: 123 };
        expectedResult = service.addModalityToCollectionIfMissing([], null, modality, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(modality);
      });

      it('should return initial array if no Modality is added', () => {
        const modalityCollection: IModality[] = [{ id: 123 }];
        expectedResult = service.addModalityToCollectionIfMissing(modalityCollection, undefined, null);
        expect(expectedResult).toEqual(modalityCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
