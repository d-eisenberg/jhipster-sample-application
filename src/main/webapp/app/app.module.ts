import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { OrderingApplicationSharedModule } from 'app/shared/shared.module';
import { OrderingApplicationCoreModule } from 'app/core/core.module';
import { OrderingApplicationAppRoutingModule } from './app-routing.module';
import { OrderingApplicationHomeModule } from './home/home.module';
import { OrderingApplicationEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    OrderingApplicationSharedModule,
    OrderingApplicationCoreModule,
    OrderingApplicationHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    OrderingApplicationEntityModule,
    OrderingApplicationAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class OrderingApplicationAppModule {}
