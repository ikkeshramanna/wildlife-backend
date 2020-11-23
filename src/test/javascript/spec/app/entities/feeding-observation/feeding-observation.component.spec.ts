import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { FeedingObservationComponent } from 'app/entities/feeding-observation/feeding-observation.component';
import { FeedingObservationService } from 'app/entities/feeding-observation/feeding-observation.service';
import { FeedingObservation } from 'app/shared/model/feeding-observation.model';

describe('Component Tests', () => {
  describe('FeedingObservation Management Component', () => {
    let comp: FeedingObservationComponent;
    let fixture: ComponentFixture<FeedingObservationComponent>;
    let service: FeedingObservationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [FeedingObservationComponent],
      })
        .overrideTemplate(FeedingObservationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeedingObservationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeedingObservationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FeedingObservation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.feedingObservations && comp.feedingObservations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
