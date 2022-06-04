import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICourseModule, CourseModule } from '../course-module.model';

import { CourseModuleService } from './course-module.service';

describe('CourseModule Service', () => {
  let service: CourseModuleService;
  let httpMock: HttpTestingController;
  let elemDefault: ICourseModule;
  let expectedResult: ICourseModule | ICourseModule[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CourseModuleService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a CourseModule', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CourseModule()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CourseModule', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CourseModule', () => {
      const patchObject = Object.assign({}, new CourseModule());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CourseModule', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a CourseModule', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCourseModuleToCollectionIfMissing', () => {
      it('should add a CourseModule to an empty array', () => {
        const courseModule: ICourseModule = { id: 123 };
        expectedResult = service.addCourseModuleToCollectionIfMissing([], courseModule);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courseModule);
      });

      it('should not add a CourseModule to an array that contains it', () => {
        const courseModule: ICourseModule = { id: 123 };
        const courseModuleCollection: ICourseModule[] = [
          {
            ...courseModule,
          },
          { id: 456 },
        ];
        expectedResult = service.addCourseModuleToCollectionIfMissing(courseModuleCollection, courseModule);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CourseModule to an array that doesn't contain it", () => {
        const courseModule: ICourseModule = { id: 123 };
        const courseModuleCollection: ICourseModule[] = [{ id: 456 }];
        expectedResult = service.addCourseModuleToCollectionIfMissing(courseModuleCollection, courseModule);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courseModule);
      });

      it('should add only unique CourseModule to an array', () => {
        const courseModuleArray: ICourseModule[] = [{ id: 123 }, { id: 456 }, { id: 77521 }];
        const courseModuleCollection: ICourseModule[] = [{ id: 123 }];
        expectedResult = service.addCourseModuleToCollectionIfMissing(courseModuleCollection, ...courseModuleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const courseModule: ICourseModule = { id: 123 };
        const courseModule2: ICourseModule = { id: 456 };
        expectedResult = service.addCourseModuleToCollectionIfMissing([], courseModule, courseModule2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courseModule);
        expect(expectedResult).toContain(courseModule2);
      });

      it('should accept null and undefined values', () => {
        const courseModule: ICourseModule = { id: 123 };
        expectedResult = service.addCourseModuleToCollectionIfMissing([], null, courseModule, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courseModule);
      });

      it('should return initial array if no CourseModule is added', () => {
        const courseModuleCollection: ICourseModule[] = [{ id: 123 }];
        expectedResult = service.addCourseModuleToCollectionIfMissing(courseModuleCollection, undefined, null);
        expect(expectedResult).toEqual(courseModuleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
