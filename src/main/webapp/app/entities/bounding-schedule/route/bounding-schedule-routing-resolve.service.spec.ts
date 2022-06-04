import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBoundingSchedule, BoundingSchedule } from '../bounding-schedule.model';
import { BoundingScheduleService } from '../service/bounding-schedule.service';

import { BoundingScheduleRoutingResolveService } from './bounding-schedule-routing-resolve.service';

describe('BoundingSchedule routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BoundingScheduleRoutingResolveService;
  let service: BoundingScheduleService;
  let resultBoundingSchedule: IBoundingSchedule | undefined;

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
    routingResolveService = TestBed.inject(BoundingScheduleRoutingResolveService);
    service = TestBed.inject(BoundingScheduleService);
    resultBoundingSchedule = undefined;
  });

  describe('resolve', () => {
    it('should return IBoundingSchedule returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBoundingSchedule = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBoundingSchedule).toEqual({ id: 123 });
    });

    it('should return new IBoundingSchedule if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBoundingSchedule = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBoundingSchedule).toEqual(new BoundingSchedule());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BoundingSchedule })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBoundingSchedule = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBoundingSchedule).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
