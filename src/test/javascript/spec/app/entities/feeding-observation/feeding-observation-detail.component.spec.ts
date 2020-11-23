import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { FeedingObservationDetailComponent } from 'app/entities/feeding-observation/feeding-observation-detail.component';
import { FeedingObservation } from 'app/shared/model/feeding-observation.model';

describe('Component Tests', () => {
  describe('FeedingObservation Management Detail Component', () => {
    let comp: FeedingObservationDetailComponent;
    let fixture: ComponentFixture<FeedingObservationDetailComponent>;
    const route = ({ data: of({ feedingObservation: new FeedingObservation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [FeedingObservationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FeedingObservationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeedingObservationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feedingObservation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feedingObservation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
