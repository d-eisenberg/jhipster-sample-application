export interface IUsers {
  id?: number;
  login?: string;
  password?: string;
}

export class Users implements IUsers {
  constructor(public id?: number, public login?: string, public password?: string) {}
}
