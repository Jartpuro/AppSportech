import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITrainingProgram, TrainingProgram } from '../training-program.model';
import { TrainingProgramService } from '../service/training-program.service';

import { TrainingProgramRoutingResolveService } from './training-program-routing-resolve.service';

describe('TrainingProgram routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TrainingProgramRoutingResolveService;
  let service: TrainingProgramService;
  let resultTrainingProgram: ITrainingProgram | undefined;

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
    routingResolveService = TestBed.inject(TrainingProgramRoutingResolveService);
    service = TestBed.inject(TrainingProgramService);
    resultTrainingProgram = undefined;
  });

  describe('resolve', () => {
    it('should return ITrainingProgram returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrainingProgram = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTrainingProgram).toEqual({ id: 123 });
    });

    it('should return new ITrainingProgram if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrainingProgram = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTrainingProgram).toEqual(new TrainingProgram());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TrainingProgram })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrainingProgram = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTrainingProgram).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
