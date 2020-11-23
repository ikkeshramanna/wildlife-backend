import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { BirdBehaviourUpdateComponent } from 'app/entities/bird-behaviour/bird-behaviour-update.component';
import { BirdBehaviourService } from 'app/entities/bird-behaviour/bird-behaviour.service';
import { BirdBehaviour } from 'app/shared/model/bird-behaviour.model';

describe('Component Tests', () => {
  describe('BirdBehaviour Management Update Component', () => {
    let comp: BirdBehaviourUpdateComponent;
    let fixture: ComponentFixture<BirdBehaviourUpdateComponent>;
    let service: BirdBehaviourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [BirdBehaviourUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BirdBehaviourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BirdBehaviourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BirdBehaviourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BirdBehaviour(123);
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
        const entity = new BirdBehaviour();
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
