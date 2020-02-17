import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWeekManu } from 'app/shared/model/week-manu.model';

type EntityResponseType = HttpResponse<IWeekManu>;
type EntityArrayResponseType = HttpResponse<IWeekManu[]>;

@Injectable({ providedIn: 'root' })
export class WeekManuService {
  public resourceUrl = SERVER_API_URL + 'api/week-manus';

  constructor(protected http: HttpClient) {}

  create(weekManu: IWeekManu): Observable<EntityResponseType> {
    return this.http.post<IWeekManu>(this.resourceUrl, weekManu, { observe: 'response' });
  }

  update(weekManu: IWeekManu): Observable<EntityResponseType> {
    return this.http.put<IWeekManu>(this.resourceUrl, weekManu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWeekManu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWeekManu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
