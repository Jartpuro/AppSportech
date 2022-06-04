import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ILogError, LogError } from '../log-error.model';
import { LogErrorService } from '../service/log-error.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';

@Component({
  selector: 'sportechapp-log-error-update',
  templateUrl: './log-error-update.component.html',
})
export class LogErrorUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    levelError: [null, [Validators.required, Validators.maxLength(400)]],
    logName: [null, [Validators.required, Validators.maxLength(400)]],
    messageError: [null, [Validators.required, Validators.maxLength(400)]],
    dateError: [null, [Validators.required]],
    customer: [null, Validators.required],
  });

  constructor(
    protected logErrorService: LogErrorService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logError }) => {
      if (logError.id === undefined) {
        const today = dayjs().startOf('day');
        logError.dateError = today;
      }

      this.updateForm(logError);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logError = this.createFromForm();
    if (logError.id !== undefined) {
      this.subscribeToSaveResponse(this.logErrorService.update(logError));
    } else {
      this.subscribeToSaveResponse(this.logErrorService.create(logError));
    }
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogError>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(logError: ILogError): void {
    this.editForm.patchValue({
      id: logError.id,
      levelError: logError.levelError,
      logName: logError.logName,
      messageError: logError.messageError,
      dateError: logError.dateError ? logError.dateError.format(DATE_TIME_FORMAT) : null,
      customer: logError.customer,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(
      this.customersSharedCollection,
      logError.customer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing(customers, this.editForm.get('customer')!.value)
        )
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));
  }

  protected createFromForm(): ILogError {
    return {
      ...new LogError(),
      id: this.editForm.get(['id'])!.value,
      levelError: this.editForm.get(['levelError'])!.value,
      logName: this.editForm.get(['logName'])!.value,
      messageError: this.editForm.get(['messageError'])!.value,
      dateError: this.editForm.get(['dateError'])!.value ? dayjs(this.editForm.get(['dateError'])!.value, DATE_TIME_FORMAT) : undefined,
      customer: this.editForm.get(['customer'])!.value,
    };
  }
}
