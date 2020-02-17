import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWeekManu } from 'app/shared/model/week-manu.model';
import { WeekManuService } from './week-manu.service';

@Component({
  templateUrl: './week-manu-delete-dialog.component.html'
})
export class WeekManuDeleteDialogComponent {
  weekManu?: IWeekManu;

  constructor(protected weekManuService: WeekManuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.weekManuService.delete(id).subscribe(() => {
      this.eventManager.broadcast('weekManuListModification');
      this.activeModal.close();
    });
  }
}
