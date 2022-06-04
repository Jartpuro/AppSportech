import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IClassroomType, ClassroomType } from '../classroom-type.model';
import { ClassroomTypeService } from '../service/classroom-type.service';

import { ClassroomTypeRoutingResolveService } from './classroom-type-routing-resolve.service';

describe('ClassroomType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ClassroomTypeRoutingResolveService;
  let service: ClassroomTypeService;
  let resultClassroomType: IClassroomType | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ClassroomTypeRoutingResolveService);
    service = TestBed.inject(ClassroomTypeService);
    resultClassroomType = undefined;
  });

  describe('resolve', () => {
    it('should return IClassroomType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultClassroomType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultClassroomType).toEqual({ id: 123 });
    });

    it('should return new IClassroomType if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultClassroomType = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultClassroomType).toEqual(new ClassroomType());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ClassroomType })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultClassroomType = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultClassroomType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
