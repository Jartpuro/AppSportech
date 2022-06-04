import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IModuleSchedule, ModuleSchedule } from '../module-schedule.model';
import { ModuleScheduleService } from '../service/module-schedule.service';

import { ModuleScheduleRoutingResolveService } from './module-schedule-routing-resolve.service';

describe('ModuleSchedule routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ModuleScheduleRoutingResolveService;
  let service: ModuleScheduleService;
  let resultModuleSchedule: IModuleSchedule | undefined;

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
    routingResolveService = TestBed.inject(ModuleScheduleRoutingResolveService);
    service = TestBed.inject(ModuleScheduleService);
    resultModuleSchedule = undefined;
  });

  describe('resolve', () => {
    it('should return IModuleSchedule returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultModuleSchedule = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultModuleSchedule).toEqual({ id: 123 });
    });

    it('should return new IModuleSchedule if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultModuleSchedule = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultModuleSchedule).toEqual(new ModuleSchedule());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ModuleSchedule })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultModuleSchedule = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultModuleSchedule).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
