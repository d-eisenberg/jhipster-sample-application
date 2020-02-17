import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'users',
        loadChildren: () => import('./users/users.module').then(m => m.OrderingApplicationUsersModule)
      },
      {
        path: 'week-manu',
        loadChildren: () => import('./week-manu/week-manu.module').then(m => m.OrderingApplicationWeekManuModule)
      },
      {
        path: 'week-order',
        loadChildren: () => import('./week-order/week-order.module').then(m => m.OrderingApplicationWeekOrderModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class OrderingApplicationEntityModule {}
