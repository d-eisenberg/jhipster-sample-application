import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrderingApplicationTestModule } from '../../../test.module';
import { WeekOrderComponent } from 'app/entities/week-order/week-order.component';
import { WeekOrderService } from 'app/entities/week-order/week-order.service';
import { WeekOrder } from 'app/shared/model/week-order.model';

describe('Component Tests', () => {
  describe('WeekOrder Management Component', () => {
    let comp: WeekOrderComponent;
    let fixture: ComponentFixture<WeekOrderComponent>;
    let service: WeekOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderingApplicationTestModule],
        declarations: [WeekOrderComponent]
      })
        .overrideTemplate(WeekOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WeekOrderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WeekOrderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WeekOrder(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.weekOrders && comp.weekOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
