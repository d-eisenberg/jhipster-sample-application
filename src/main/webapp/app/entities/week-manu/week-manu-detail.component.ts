import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IWeekManu } from 'app/shared/model/week-manu.model';

@Component({
  selector: 'jhi-week-manu-detail',
  templateUrl: './week-manu-detail.component.html'
})
export class WeekManuDetailComponent implements OnInit {
  weekManu: IWeekManu | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekManu }) => (this.weekManu = weekManu));
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
