import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { ObservationLocationDetailComponent } from 'app/entities/observation-location/observation-location-detail.component';
import { ObservationLocation } from 'app/shared/model/observation-location.model';

describe('Component Tests', () => {
  describe('ObservationLocation Management Detail Component', () => {
    let comp: ObservationLocationDetailComponent;
    let fixture: ComponentFixture<ObservationLocationDetailComponent>;
    const route = ({ data: of({ observationLocation: new ObservationLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [ObservationLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ObservationLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObservationLocationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load observationLocation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.observationLocation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
