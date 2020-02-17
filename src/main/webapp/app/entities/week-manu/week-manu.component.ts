import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWeekManu } from 'app/shared/model/week-manu.model';
import { WeekManuService } from './week-manu.service';
import { WeekManuDeleteDialogComponent } from './week-manu-delete-dialog.component';

@Component({
  selector: 'jhi-week-manu',
  templateUrl: './week-manu.component.html'
})
export class WeekManuComponent implements OnInit, OnDestroy {
  weekManus?: IWeekManu[];
  eventSubscriber?: Subscription;

  constructor(
    protected weekManuService: WeekManuService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.weekManuService.query().subscribe((res: HttpResponse<IWeekManu[]>) => (this.weekManus = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWeekManus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWeekManu): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInWeekManus(): void {
    this.eventSubscriber = this.eventManager.subscribe('weekManuListModification', () => this.loadAll());
  }

  delete(weekManu: IWeekManu): void {
    const modalRef = this.modalService.open(WeekManuDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.weekManu = weekManu;
  }
}
