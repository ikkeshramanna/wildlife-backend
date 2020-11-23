import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { ObservationLocationUpdateComponent } from 'app/entities/observation-location/observation-location-update.component';
import { ObservationLocationService } from 'app/entities/observation-location/observation-location.service';
import { ObservationLocation } from 'app/shared/model/observation-location.model';

describe('Component Tests', () => {
  describe('ObservationLocation Management Update Component', () => {
    let comp: ObservationLocationUpdateComponent;
    let fixture: ComponentFixture<ObservationLocationUpdateComponent>;
    let service: ObservationLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [ObservationLocationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ObservationLocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObservationLocationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservationLocationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ObservationLocation(123);
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
        const entity = new ObservationLocation();
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
