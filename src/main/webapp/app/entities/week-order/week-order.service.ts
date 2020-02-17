import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWeekOrder } from 'app/shared/model/week-order.model';

type EntityResponseType = HttpResponse<IWeekOrder>;
type EntityArrayResponseType = HttpResponse<IWeekOrder[]>;

@Injectable({ providedIn: 'root' })
export class WeekOrderService {
  public resourceUrl = SERVER_API_URL + 'api/week-orders';

  constructor(protected http: HttpClient) {}

  create(weekOrder: IWeekOrder): Observable<EntityResponseType> {
    return this.http.post<IWeekOrder>(this.resourceUrl, weekOrder, { observe: 'response' });
  }

  update(weekOrder: IWeekOrder): Observable<EntityResponseType> {
    return this.http.put<IWeekOrder>(this.resourceUrl, weekOrder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWeekOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWeekOrder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
