import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { ICampus, Campus } from '../campus.model';

import { CampusService } from './campus.service';

describe('Campus Service', () => {
  let service: CampusService;
  let httpMock: HttpTestingController;
  let elemDefault: ICampus;
  let expectedResult: ICampus | ICampus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CampusService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      campusName: 'AAAAAAA',
      campusAddress: 'AAAAAAA',
      campusState: State.ACTIVE,
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

    it('should create a Campus', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Campus()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Campus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          campusName: 'BBBBBB',
          campusAddress: 'BBBBBB',
          campusState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Campus', () => {
      const patchObject = Object.assign(
        {
          campusName: 'BBBBBB',
          campusAddress: 'BBBBBB',
        },
        new Campus()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Campus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          campusName: 'BBBBBB',
          campusAddress: 'BBBBBB',
          campusState: 'BBBBBB',
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

    it('should delete a Campus', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCampusToCollectionIfMissing', () => {
      it('should add a Campus to an empty array', () => {
        const campus: ICampus = { id: 123 };
        expectedResult = service.addCampusToCollectionIfMissing([], campus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(campus);
      });

      it('should not add a Campus to an array that contains it', () => {
        const campus: ICampus = { id: 123 };
        const campusCollection: ICampus[] = [
          {
            ...campus,
          },
          { id: 456 },
        ];
        expectedResult = service.addCampusToCollectionIfMissing(campusCollection, campus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Campus to an array that doesn't contain it", () => {
        const campus: ICampus = { id: 123 };
        const campusCollection: ICampus[] = [{ id: 456 }];
        expectedResult = service.addCampusToCollectionIfMissing(campusCollection, campus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(campus);
      });

      it('should add only unique Campus to an array', () => {
        const campusArray: ICampus[] = [{ id: 123 }, { id: 456 }, { id: 75222 }];
        const campusCollection: ICampus[] = [{ id: 123 }];
        expectedResult = service.addCampusToCollectionIfMissing(campusCollection, ...campusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const campus: ICampus = { id: 123 };
        const campus2: ICampus = { id: 456 };
        expectedResult = service.addCampusToCollectionIfMissing([], campus, campus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(campus);
        expect(expectedResult).toContain(campus2);
      });

      it('should accept null and undefined values', () => {
        const campus: ICampus = { id: 123 };
        expectedResult = service.addCampusToCollectionIfMissing([], null, campus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(campus);
      });

      it('should return initial array if no Campus is added', () => {
        const campusCollection: ICampus[] = [{ id: 123 }];
        expectedResult = service.addCampusToCollectionIfMissing(campusCollection, undefined, null);
        expect(expectedResult).toEqual(campusCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
