import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrderingApplicationSharedModule } from 'app/shared/shared.module';
import { WeekOrderComponent } from './week-order.component';
import { WeekOrderDetailComponent } from './week-order-detail.component';
import { WeekOrderUpdateComponent } from './week-order-update.component';
import { WeekOrderDeleteDialogComponent } from './week-order-delete-dialog.component';
import { weekOrderRoute } from './week-order.route';

@NgModule({
  imports: [OrderingApplicationSharedModule, RouterModule.forChild(weekOrderRoute)],
  declarations: [WeekOrderComponent, WeekOrderDetailComponent, WeekOrderUpdateComponent, WeekOrderDeleteDialogComponent],
  entryComponents: [WeekOrderDeleteDialogComponent]
})
export class OrderingApplicationWeekOrderModule {}
