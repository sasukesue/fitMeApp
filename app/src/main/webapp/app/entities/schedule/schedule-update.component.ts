import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';

@Component({
  selector: 'jhi-schedule-update',
  templateUrl: './schedule-update.component.html'
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    dateTime: [null, [Validators.required]],
    availableSlots: [null, [Validators.required]],
    fitnessClassId: [null, [Validators.required]],
    locationId: [null, [Validators.required]]
  });

  constructor(protected scheduleService: ScheduleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schedule }) => {
      if (!schedule.id) {
        const today = moment().startOf('day');
        schedule.dateTime = today;
      }

      this.updateForm(schedule);
    });
  }

  updateForm(schedule: ISchedule): void {
    this.editForm.patchValue({
      id: schedule.id,
      dateTime: schedule.dateTime ? schedule.dateTime.format(DATE_TIME_FORMAT) : null,
      availableSlots: schedule.availableSlots,
      fitnessClassId: schedule.fitnessClassId,
      locationId: schedule.locationId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  private createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id'])!.value,
      dateTime: this.editForm.get(['dateTime'])!.value ? moment(this.editForm.get(['dateTime'])!.value, DATE_TIME_FORMAT) : undefined,
      availableSlots: this.editForm.get(['availableSlots'])!.value,
      fitnessClassId: this.editForm.get(['fitnessClassId'])!.value,
      locationId: this.editForm.get(['locationId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>): void {
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
}
