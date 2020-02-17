import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWeekManu, WeekManu } from 'app/shared/model/week-manu.model';
import { WeekManuService } from './week-manu.service';
import { WeekManuComponent } from './week-manu.component';
import { WeekManuDetailComponent } from './week-manu-detail.component';
import { WeekManuUpdateComponent } from './week-manu-update.component';

@Injectable({ providedIn: 'root' })
export class WeekManuResolve implements Resolve<IWeekManu> {
  constructor(private service: WeekManuService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWeekManu> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((weekManu: HttpResponse<WeekManu>) => {
          if (weekManu.body) {
            return of(weekManu.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WeekManu());
  }
}

export const weekManuRoute: Routes = [
  {
    path: '',
    component: WeekManuComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekManus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WeekManuDetailComponent,
    resolve: {
      weekManu: WeekManuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekManus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WeekManuUpdateComponent,
    resolve: {
      weekManu: WeekManuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekManus'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WeekManuUpdateComponent,
    resolve: {
      weekManu: WeekManuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'WeekManus'
    },
    canActivate: [UserRouteAccessService]
  }
];
