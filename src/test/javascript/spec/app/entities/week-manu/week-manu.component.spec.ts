import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrderingApplicationTestModule } from '../../../test.module';
import { WeekManuComponent } from 'app/entities/week-manu/week-manu.component';
import { WeekManuService } from 'app/entities/week-manu/week-manu.service';
import { WeekManu } from 'app/shared/model/week-manu.model';

describe('Component Tests', () => {
  describe('WeekManu Management Component', () => {
    let comp: WeekManuComponent;
    let fixture: ComponentFixture<WeekManuComponent>;
    let service: WeekManuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderingApplicationTestModule],
        declarations: [WeekManuComponent]
      })
        .overrideTemplate(WeekManuComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WeekManuComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WeekManuService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new WeekManu(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.weekManus && comp.weekManus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
