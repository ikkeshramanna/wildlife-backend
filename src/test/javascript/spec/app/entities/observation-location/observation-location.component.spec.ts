import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { ObservationLocationComponent } from 'app/entities/observation-location/observation-location.component';
import { ObservationLocationService } from 'app/entities/observation-location/observation-location.service';
import { ObservationLocation } from 'app/shared/model/observation-location.model';

describe('Component Tests', () => {
  describe('ObservationLocation Management Component', () => {
    let comp: ObservationLocationComponent;
    let fixture: ComponentFixture<ObservationLocationComponent>;
    let service: ObservationLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [ObservationLocationComponent],
      })
        .overrideTemplate(ObservationLocationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObservationLocationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservationLocationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ObservationLocation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.observationLocations && comp.observationLocations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
