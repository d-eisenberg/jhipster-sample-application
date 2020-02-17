export interface IWeekManu {
  id?: number;
  menuContentType?: string;
  menu?: any;
}

export class WeekManu implements IWeekManu {
  constructor(public id?: number, public menuContentType?: string, public menu?: any) {}
}
