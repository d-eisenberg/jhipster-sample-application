import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrderingApplicationSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [OrderingApplicationSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class OrderingApplicationHomeModule {}
