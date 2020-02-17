import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrderingApplicationSharedModule } from 'app/shared/shared.module';
import { WeekManuComponent } from './week-manu.component';
import { WeekManuDetailComponent } from './week-manu-detail.component';
import { WeekManuUpdateComponent } from './week-manu-update.component';
import { WeekManuDeleteDialogComponent } from './week-manu-delete-dialog.component';
import { weekManuRoute } from './week-manu.route';

@NgModule({
  imports: [OrderingApplicationSharedModule, RouterModule.forChild(weekManuRoute)],
  declarations: [WeekManuComponent, WeekManuDetailComponent, WeekManuUpdateComponent, WeekManuDeleteDialogComponent],
  entryComponents: [WeekManuDeleteDialogComponent]
})
export class OrderingApplicationWeekManuModule {}
