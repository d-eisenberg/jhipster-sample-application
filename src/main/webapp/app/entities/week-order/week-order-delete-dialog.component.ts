import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWeekOrder } from 'app/shared/model/week-order.model';
import { WeekOrderService } from './week-order.service';

@Component({
  templateUrl: './week-order-delete-dialog.component.html'
})
export class WeekOrderDeleteDialogComponent {
  weekOrder?: IWeekOrder;

  constructor(protected weekOrderService: WeekOrderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.weekOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('weekOrderListModification');
      this.activeModal.close();
    });
  }
}
