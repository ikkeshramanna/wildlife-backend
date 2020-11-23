import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { SightingComponent } from 'app/entities/sighting/sighting.component';
import { SightingService } from 'app/entities/sighting/sighting.service';
import { Sighting } from 'app/shared/model/sighting.model';

describe('Component Tests', () => {
  describe('Sighting Management Component', () => {
    let comp: SightingComponent;
    let fixture: ComponentFixture<SightingComponent>;
    let service: SightingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [SightingComponent],
      })
        .overrideTemplate(SightingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SightingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SightingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sighting(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sightings && comp.sightings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
