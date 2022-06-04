import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { ICourseStatus, CourseStatus } from '../course-status.model';

import { CourseStatusService } from './course-status.service';

describe('CourseStatus Service', () => {
  let service: CourseStatusService;
  let httpMock: HttpTestingController;
  let elemDefault: ICourseStatus;
  let expectedResult: ICourseStatus | ICourseStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CourseStatusService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nameCourseStatus: 'AAAAAAA',
      stateCourse: State.ACTIVE,
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

    it('should create a CourseStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CourseStatus()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CourseStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameCourseStatus: 'BBBBBB',
          stateCourse: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CourseStatus', () => {
      const patchObject = Object.assign(
        {
          nameCourseStatus: 'BBBBBB',
        },
        new CourseStatus()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CourseStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nameCourseStatus: 'BBBBBB',
          stateCourse: 'BBBBBB',
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

    it('should delete a CourseStatus', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCourseStatusToCollectionIfMissing', () => {
      it('should add a CourseStatus to an empty array', () => {
        const courseStatus: ICourseStatus = { id: 123 };
        expectedResult = service.addCourseStatusToCollectionIfMissing([], courseStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courseStatus);
      });

      it('should not add a CourseStatus to an array that contains it', () => {
        const courseStatus: ICourseStatus = { id: 123 };
        const courseStatusCollection: ICourseStatus[] = [
          {
            ...courseStatus,
          },
          { id: 456 },
        ];
        expectedResult = service.addCourseStatusToCollectionIfMissing(courseStatusCollection, courseStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CourseStatus to an array that doesn't contain it", () => {
        const courseStatus: ICourseStatus = { id: 123 };
        const courseStatusCollection: ICourseStatus[] = [{ id: 456 }];
        expectedResult = service.addCourseStatusToCollectionIfMissing(courseStatusCollection, courseStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courseStatus);
      });

      it('should add only unique CourseStatus to an array', () => {
        const courseStatusArray: ICourseStatus[] = [{ id: 123 }, { id: 456 }, { id: 18263 }];
        const courseStatusCollection: ICourseStatus[] = [{ id: 123 }];
        expectedResult = service.addCourseStatusToCollectionIfMissing(courseStatusCollection, ...courseStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const courseStatus: ICourseStatus = { id: 123 };
        const courseStatus2: ICourseStatus = { id: 456 };
        expectedResult = service.addCourseStatusToCollectionIfMissing([], courseStatus, courseStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courseStatus);
        expect(expectedResult).toContain(courseStatus2);
      });

      it('should accept null and undefined values', () => {
        const courseStatus: ICourseStatus = { id: 123 };
        expectedResult = service.addCourseStatusToCollectionIfMissing([], null, courseStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courseStatus);
      });

      it('should return initial array if no CourseStatus is added', () => {
        const courseStatusCollection: ICourseStatus[] = [{ id: 123 }];
        expectedResult = service.addCourseStatusToCollectionIfMissing(courseStatusCollection, undefined, null);
        expect(expectedResult).toEqual(courseStatusCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
