import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITrainer, Trainer } from '../trainer.model';
import { TrainerService } from '../service/trainer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-trainer-update',
  templateUrl: './trainer-update.component.html',
})
export class TrainerUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  customersSharedCollection: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    trainerState: [null, [Validators.required]],
    customer: [null, Validators.required],
  });

  constructor(
    protected trainerService: TrainerService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainer }) => {
      this.updateForm(trainer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainer = this.createFromForm();
    if (trainer.id !== undefined) {
      this.subscribeToSaveResponse(this.trainerService.update(trainer));
    } else {
      this.subscribeToSaveResponse(this.trainerService.create(trainer));
    }
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainer>>): void {
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

  protected updateForm(trainer: ITrainer): void {
    this.editForm.patchValue({
      id: trainer.id,
      trainerState: trainer.trainerState,
      customer: trainer.customer,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(
      this.customersSharedCollection,
      trainer.customer
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

  protected createFromForm(): ITrainer {
    return {
      ...new Trainer(),
      id: this.editForm.get(['id'])!.value,
      trainerState: this.editForm.get(['trainerState'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }
}
