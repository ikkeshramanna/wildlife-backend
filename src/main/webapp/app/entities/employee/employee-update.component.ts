import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  countries: ICountry[] = [];
  dobDp: any;
  addDateDp: any;
  updateDateDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    surname: [null, [Validators.required]],
    dob: [null, [Validators.required]],
    address: [null, [Validators.required]],
    email: [null, [Validators.required]],
    addDate: [null, [Validators.required]],
    updateDate: [null, [Validators.required]],
    mobileNumber: [null, [Validators.required]],
    country: [],
  });

  constructor(
    protected employeeService: EmployeeService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));
    });
  }

  updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      name: employee.name,
      surname: employee.surname,
      dob: employee.dob,
      address: employee.address,
      email: employee.email,
      addDate: employee.addDate,
      updateDate: employee.updateDate,
      mobileNumber: employee.mobileNumber,
      country: employee.country,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      address: this.editForm.get(['address'])!.value,
      email: this.editForm.get(['email'])!.value,
      addDate: this.editForm.get(['addDate'])!.value,
      updateDate: this.editForm.get(['updateDate'])!.value,
      mobileNumber: this.editForm.get(['mobileNumber'])!.value,
      country: this.editForm.get(['country'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICountry): any {
    return item.id;
  }
}
