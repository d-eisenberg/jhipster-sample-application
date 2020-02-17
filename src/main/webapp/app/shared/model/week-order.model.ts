import { IUsers } from 'app/shared/model/users.model';

export interface IWeekOrder {
  id?: number;
  weeknum?: number;
  daynum?: number;
  userId?: number;
  userOrderContentType?: string;
  userOrder?: any;
  userId?: IUsers;
}

export class WeekOrder implements IWeekOrder {
  constructor(
    public id?: number,
    public weeknum?: number,
    public daynum?: number,
    public userId?: number,
    public userOrderContentType?: string,
    public userOrder?: any,
    public userId?: IUsers
  ) {}
}
