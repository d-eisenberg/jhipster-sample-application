import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWeekOrder } from 'app/shared/model/week-order.model';
import { WeekOrderService } from './week-order.service';
import { WeekOrderDeleteDialogComponent } from './week-order-delete-dialog.component';

@Component({
  selector: 'jhi-week-order',
  templateUrl: './week-order.component.html'
})
export class WeekOrderComponent implements OnInit, OnDestroy {
  weekOrders?: IWeekOrder[];
  eventSubscriber?: Subscription;

  constructor(
    protected weekOrderService: WeekOrderService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.weekOrderService.query().subscribe((res: HttpResponse<IWeekOrder[]>) => (this.weekOrders = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWeekOrders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWeekOrder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInWeekOrders(): void {
    this.eventSubscriber = this.eventManager.subscribe('weekOrderListModification', () => this.loadAll());
  }

  delete(weekOrder: IWeekOrder): void {
    const modalRef = this.modalService.open(WeekOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.weekOrder = weekOrder;
  }
}
