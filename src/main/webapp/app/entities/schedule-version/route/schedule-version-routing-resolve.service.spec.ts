import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IScheduleVersion, ScheduleVersion } from '../schedule-version.model';
import { ScheduleVersionService } from '../service/schedule-version.service';

import { ScheduleVersionRoutingResolveService } from './schedule-version-routing-resolve.service';

describe('ScheduleVersion routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ScheduleVersionRoutingResolveService;
  let service: ScheduleVersionService;
  let resultScheduleVersion: IScheduleVersion | undefined;

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
    routingResolveService = TestBed.inject(ScheduleVersionRoutingResolveService);
    service = TestBed.inject(ScheduleVersionService);
    resultScheduleVersion = undefined;
  });

  describe('resolve', () => {
    it('should return IScheduleVersion returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultScheduleVersion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultScheduleVersion).toEqual({ id: 123 });
    });

    it('should return new IScheduleVersion if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultScheduleVersion = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultScheduleVersion).toEqual(new ScheduleVersion());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ScheduleVersion })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultScheduleVersion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultScheduleVersion).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
