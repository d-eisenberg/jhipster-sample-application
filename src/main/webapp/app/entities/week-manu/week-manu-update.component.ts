import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IWeekManu, WeekManu } from 'app/shared/model/week-manu.model';
import { WeekManuService } from './week-manu.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-week-manu-update',
  templateUrl: './week-manu-update.component.html'
})
export class WeekManuUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    menu: [],
    menuContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected weekManuService: WeekManuService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ weekManu }) => {
      this.updateForm(weekManu);
    });
  }

  updateForm(weekManu: IWeekManu): void {
    this.editForm.patchValue({
      id: weekManu.id,
      menu: weekManu.menu,
      menuContentType: weekManu.menuContentType
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
    const weekManu = this.createFromForm();
    if (weekManu.id !== undefined) {
      this.subscribeToSaveResponse(this.weekManuService.update(weekManu));
    } else {
      this.subscribeToSaveResponse(this.weekManuService.create(weekManu));
    }
  }

  private createFromForm(): IWeekManu {
    return {
      ...new WeekManu(),
      id: this.editForm.get(['id'])!.value,
      menuContentType: this.editForm.get(['menuContentType'])!.value,
      menu: this.editForm.get(['menu'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWeekManu>>): void {
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
}
