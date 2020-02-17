import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrderingApplicationTestModule } from '../../../test.module';
import { WeekOrderUpdateComponent } from 'app/entities/week-order/week-order-update.component';
import { WeekOrderService } from 'app/entities/week-order/week-order.service';
import { WeekOrder } from 'app/shared/model/week-order.model';

describe('Component Tests', () => {
  describe('WeekOrder Management Update Component', () => {
    let comp: WeekOrderUpdateComponent;
    let fixture: ComponentFixture<WeekOrderUpdateComponent>;
    let service: WeekOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderingApplicationTestModule],
        declarations: [WeekOrderUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(WeekOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WeekOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WeekOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WeekOrder(123);
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
        const entity = new WeekOrder();
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
