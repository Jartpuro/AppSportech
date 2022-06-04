import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IClassroomType, ClassroomType } from '../classroom-type.model';

import { ClassroomTypeService } from './classroom-type.service';

describe('ClassroomType Service', () => {
  let service: ClassroomTypeService;
  let httpMock: HttpTestingController;
  let elemDefault: IClassroomType;
  let expectedResult: IClassroomType | IClassroomType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ClassroomTypeService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      typeClassroom: 'AAAAAAA',
      classroomDescription: 'AAAAAAA',
      classroomState: State.ACTIVE,
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

    it('should create a ClassroomType', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ClassroomType()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ClassroomType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeClassroom: 'BBBBBB',
          classroomDescription: 'BBBBBB',
          classroomState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ClassroomType', () => {
      const patchObject = Object.assign({}, new ClassroomType());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ClassroomType', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          typeClassroom: 'BBBBBB',
          classroomDescription: 'BBBBBB',
          classroomState: 'BBBBBB',
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

    it('should delete a ClassroomType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addClassroomTypeToCollectionIfMissing', () => {
      it('should add a ClassroomType to an empty array', () => {
        const classroomType: IClassroomType = { id: 123 };
        expectedResult = service.addClassroomTypeToCollectionIfMissing([], classroomType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(classroomType);
      });

      it('should not add a ClassroomType to an array that contains it', () => {
        const classroomType: IClassroomType = { id: 123 };
        const classroomTypeCollection: IClassroomType[] = [
          {
            ...classroomType,
          },
          { id: 456 },
        ];
        expectedResult = service.addClassroomTypeToCollectionIfMissing(classroomTypeCollection, classroomType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ClassroomType to an array that doesn't contain it", () => {
        const classroomType: IClassroomType = { id: 123 };
        const classroomTypeCollection: IClassroomType[] = [{ id: 456 }];
        expectedResult = service.addClassroomTypeToCollectionIfMissing(classroomTypeCollection, classroomType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(classroomType);
      });

      it('should add only unique ClassroomType to an array', () => {
        const classroomTypeArray: IClassroomType[] = [{ id: 123 }, { id: 456 }, { id: 40414 }];
        const classroomTypeCollection: IClassroomType[] = [{ id: 123 }];
        expectedResult = service.addClassroomTypeToCollectionIfMissing(classroomTypeCollection, ...classroomTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const classroomType: IClassroomType = { id: 123 };
        const classroomType2: IClassroomType = { id: 456 };
        expectedResult = service.addClassroomTypeToCollectionIfMissing([], classroomType, classroomType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(classroomType);
        expect(expectedResult).toContain(classroomType2);
      });

      it('should accept null and undefined values', () => {
        const classroomType: IClassroomType = { id: 123 };
        expectedResult = service.addClassroomTypeToCollectionIfMissing([], null, classroomType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(classroomType);
      });

      it('should return initial array if no ClassroomType is added', () => {
        const classroomTypeCollection: IClassroomType[] = [{ id: 123 }];
        expectedResult = service.addClassroomTypeToCollectionIfMissing(classroomTypeCollection, undefined, null);
        expect(expectedResult).toEqual(classroomTypeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
