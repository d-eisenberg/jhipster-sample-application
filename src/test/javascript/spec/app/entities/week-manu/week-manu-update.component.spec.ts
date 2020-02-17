import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrderingApplicationTestModule } from '../../../test.module';
import { WeekManuUpdateComponent } from 'app/entities/week-manu/week-manu-update.component';
import { WeekManuService } from 'app/entities/week-manu/week-manu.service';
import { WeekManu } from 'app/shared/model/week-manu.model';

describe('Component Tests', () => {
  describe('WeekManu Management Update Component', () => {
    let comp: WeekManuUpdateComponent;
    let fixture: ComponentFixture<WeekManuUpdateComponent>;
    let service: WeekManuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderingApplicationTestModule],
        declarations: [WeekManuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WeekManuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WeekManuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WeekManuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WeekManu(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new WeekManu();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
