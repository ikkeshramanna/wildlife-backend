import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { TaggedAnimalUpdateComponent } from 'app/entities/tagged-animal/tagged-animal-update.component';
import { TaggedAnimalService } from 'app/entities/tagged-animal/tagged-animal.service';
import { TaggedAnimal } from 'app/shared/model/tagged-animal.model';

describe('Component Tests', () => {
  describe('TaggedAnimal Management Update Component', () => {
    let comp: TaggedAnimalUpdateComponent;
    let fixture: ComponentFixture<TaggedAnimalUpdateComponent>;
    let service: TaggedAnimalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [TaggedAnimalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaggedAnimalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaggedAnimalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaggedAnimalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaggedAnimal(123);
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
        const entity = new TaggedAnimal();
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
