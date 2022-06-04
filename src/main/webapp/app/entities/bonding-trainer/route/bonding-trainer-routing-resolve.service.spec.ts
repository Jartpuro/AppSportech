import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IBondingTrainer, BondingTrainer } from '../bonding-trainer.model';
import { BondingTrainerService } from '../service/bonding-trainer.service';

import { BondingTrainerRoutingResolveService } from './bonding-trainer-routing-resolve.service';

describe('BondingTrainer routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: BondingTrainerRoutingResolveService;
  let service: BondingTrainerService;
  let resultBondingTrainer: IBondingTrainer | undefined;

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
    routingResolveService = TestBed.inject(BondingTrainerRoutingResolveService);
    service = TestBed.inject(BondingTrainerService);
    resultBondingTrainer = undefined;
  });

  describe('resolve', () => {
    it('should return IBondingTrainer returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBondingTrainer = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBondingTrainer).toEqual({ id: 123 });
    });

    it('should return new IBondingTrainer if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBondingTrainer = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultBondingTrainer).toEqual(new BondingTrainer());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BondingTrainer })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultBondingTrainer = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultBondingTrainer).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
