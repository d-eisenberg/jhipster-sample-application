import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { OrderingApplicationTestModule } from '../../../test.module';
import { WeekManuDetailComponent } from 'app/entities/week-manu/week-manu-detail.component';
import { WeekManu } from 'app/shared/model/week-manu.model';

describe('Component Tests', () => {
  describe('WeekManu Management Detail Component', () => {
    let comp: WeekManuDetailComponent;
    let fixture: ComponentFixture<WeekManuDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ weekManu: new WeekManu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderingApplicationTestModule],
        declarations: [WeekManuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WeekManuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WeekManuDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load weekManu on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.weekManu).toEqual(jasmine.objectContaining({ id: 123 }));
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
