import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { FeedingObservationUpdateComponent } from 'app/entities/feeding-observation/feeding-observation-update.component';
import { FeedingObservationService } from 'app/entities/feeding-observation/feeding-observation.service';
import { FeedingObservation } from 'app/shared/model/feeding-observation.model';

describe('Component Tests', () => {
  describe('FeedingObservation Management Update Component', () => {
    let comp: FeedingObservationUpdateComponent;
    let fixture: ComponentFixture<FeedingObservationUpdateComponent>;
    let service: FeedingObservationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [FeedingObservationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FeedingObservationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeedingObservationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeedingObservationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FeedingObservation(123);
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
        const entity = new FeedingObservation();
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
