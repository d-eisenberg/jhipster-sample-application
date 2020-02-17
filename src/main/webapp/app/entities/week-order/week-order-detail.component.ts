import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IWeekOrder } from 'app/shared/model/week-order.model';

@Component({
  selector: 'jhi-week-order-detail',
  templateUrl: './week-order-detail.component.html'
})
export class WeekOrderDetailComponent implements OnInit {
  weekOrder: IWeekOrder | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekOrder }) => (this.weekOrder = weekOrder));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
