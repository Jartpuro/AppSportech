import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IAreaTrainer, AreaTrainer } from '../area-trainer.model';
import { AreaTrainerService } from '../service/area-trainer.service';

import { AreaTrainerRoutingResolveService } from './area-trainer-routing-resolve.service';

describe('AreaTrainer routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: AreaTrainerRoutingResolveService;
  let service: AreaTrainerService;
  let resultAreaTrainer: IAreaTrainer | undefined;

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
    routingResolveService = TestBed.inject(AreaTrainerRoutingResolveService);
    service = TestBed.inject(AreaTrainerService);
    resultAreaTrainer = undefined;
  });

  describe('resolve', () => {
    it('should return IAreaTrainer returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAreaTrainer = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAreaTrainer).toEqual({ id: 123 });
    });

    it('should return new IAreaTrainer if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAreaTrainer = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAreaTrainer).toEqual(new AreaTrainer());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as AreaTrainer })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultAreaTrainer = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultAreaTrainer).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
