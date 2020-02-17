import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IWeekOrder, WeekOrder } from 'app/shared/model/week-order.model';
import { WeekOrderService } from './week-order.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users/users.service';

@Component({
  selector: 'jhi-week-order-update',
  templateUrl: './week-order-update.component.html'
})
export class WeekOrderUpdateComponent implements OnInit {
  isSaving = false;
  users: IUsers[] = [];

  editForm = this.fb.group({
    id: [],
    weeknum: [],
    daynum: [],
    userId: [],
    userOrder: [],
    userOrderContentType: [],
    userId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected weekOrderService: WeekOrderService,
    protected usersService: UsersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekOrder }) => {
      this.updateForm(weekOrder);

      this.usersService.query().subscribe((res: HttpResponse<IUsers[]>) => (this.users = res.body || []));
    });
  }

  updateForm(weekOrder: IWeekOrder): void {
    this.editForm.patchValue({
      id: weekOrder.id,
      weeknum: weekOrder.weeknum,
      daynum: weekOrder.daynum,
      userId: weekOrder.userId,
      userOrder: weekOrder.userOrder,
      userOrderContentType: weekOrder.userOrderContentType,
      userId: weekOrder.userId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('orderingApplicationApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const weekOrder = this.createFromForm();
    if (weekOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.weekOrderService.update(weekOrder));
    } else {
      this.subscribeToSaveResponse(this.weekOrderService.create(weekOrder));
    }
  }

  private createFromForm(): IWeekOrder {
    return {
      ...new WeekOrder(),
      id: this.editForm.get(['id'])!.value,
      weeknum: this.editForm.get(['weeknum'])!.value,
      daynum: this.editForm.get(['daynum'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      userOrderContentType: this.editForm.get(['userOrderContentType'])!.value,
      userOrder: this.editForm.get(['userOrder'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeekOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUsers): any {
    return item.id;
  }
}
