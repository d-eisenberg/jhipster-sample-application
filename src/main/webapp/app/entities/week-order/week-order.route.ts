import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWeekOrder, WeekOrder } from 'app/shared/model/week-order.model';
import { WeekOrderService } from './week-order.service';
import { WeekOrderComponent } from './week-order.component';
import { WeekOrderDetailComponent } from './week-order-detail.component';
import { WeekOrderUpdateComponent } from './week-order-update.component';

@Injectable({ providedIn: 'root' })
export class WeekOrderResolve implements Resolve<IWeekOrder> {
  constructor(private service: WeekOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWeekOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((weekOrder: HttpResponse<WeekOrder>) => {
          if (weekOrder.body) {
            return of(weekOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WeekOrder());
  }
}

export const weekOrderRoute: Routes = [
  {
    path: '',
    component: WeekOrderComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WeekOrderDetailComponent,
    resolve: {
      weekOrder: WeekOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WeekOrderUpdateComponent,
    resolve: {
      weekOrder: WeekOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekOrders'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WeekOrderUpdateComponent,
    resolve: {
      weekOrder: WeekOrderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekOrders'
    },
    canActivate: [UserRouteAccessService]
  }
];
