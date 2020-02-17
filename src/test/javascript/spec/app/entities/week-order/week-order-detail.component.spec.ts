import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { OrderingApplicationTestModule } from '../../../test.module';
import { WeekOrderDetailComponent } from 'app/entities/week-order/week-order-detail.component';
import { WeekOrder } from 'app/shared/model/week-order.model';

describe('Component Tests', () => {
  describe('WeekOrder Management Detail Component', () => {
    let comp: WeekOrderDetailComponent;
    let fixture: ComponentFixture<WeekOrderDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ weekOrder: new WeekOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderingApplicationTestModule],
        declarations: [WeekOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WeekOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WeekOrderDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load weekOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.weekOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
