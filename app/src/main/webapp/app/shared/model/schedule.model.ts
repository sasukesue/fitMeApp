import { Moment } from 'moment';

export interface ISchedule {
  id?: number;
  dateTime?: Moment;
  availableSlots?: number;
  fitnessClassId?: number;
  locationId?: number;
}

export class Schedule implements ISchedule {
  constructor(
    public id?: number,
    public dateTime?: Moment,
    public availableSlots?: number,
    public fitnessClassId?: number,
    public locationId?: number
  ) {}
}
