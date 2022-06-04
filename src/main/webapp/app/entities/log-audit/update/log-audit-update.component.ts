import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { ILogAudit, LogAudit } from '../log-audit.model';
import { LogAuditService } from '../service/log-audit.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';

@Component({
  selector: 'sportechapp-log-audit-update',
  templateUrl: './log-audit-update.component.html',
})
export class LogAuditUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    levelAudit: [null, [Validators.required, Validators.maxLength(400)]],
    logName: [null, [Validators.required, Validators.maxLength(400)]],
    messageAudit: [null, [Validators.required, Validators.maxLength(400)]],
    dateAudit: [null, [Validators.required]],
    customer: [null, Validators.required],
  });

  constructor(
    protected logAuditService: LogAuditService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logAudit }) => {
      if (logAudit.id === undefined) {
        const today = dayjs().startOf('day');
        logAudit.dateAudit = today;
      }

      this.updateForm(logAudit);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const logAudit = this.createFromForm();
    if (logAudit.id !== undefined) {
      this.subscribeToSaveResponse(this.logAuditService.update(logAudit));
    } else {
      this.subscribeToSaveResponse(this.logAuditService.create(logAudit));
    }
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogAudit>>): void {
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

  protected updateForm(logAudit: ILogAudit): void {
    this.editForm.patchValue({
      id: logAudit.id,
      levelAudit: logAudit.levelAudit,
      logName: logAudit.logName,
      messageAudit: logAudit.messageAudit,
      dateAudit: logAudit.dateAudit ? logAudit.dateAudit.format(DATE_TIME_FORMAT) : null,
      customer: logAudit.customer,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(
      this.customersSharedCollection,
      logAudit.customer
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

  protected createFromForm(): ILogAudit {
    return {
      ...new LogAudit(),
      id: this.editForm.get(['id'])!.value,
      levelAudit: this.editForm.get(['levelAudit'])!.value,
      logName: this.editForm.get(['logName'])!.value,
      messageAudit: this.editForm.get(['messageAudit'])!.value,
      dateAudit: this.editForm.get(['dateAudit'])!.value ? dayjs(this.editForm.get(['dateAudit'])!.value, DATE_TIME_FORMAT) : undefined,
      customer: this.editForm.get(['customer'])!.value,
    };
  }
}
