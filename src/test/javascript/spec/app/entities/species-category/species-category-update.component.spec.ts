import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { SpeciesCategoryUpdateComponent } from 'app/entities/species-category/species-category-update.component';
import { SpeciesCategoryService } from 'app/entities/species-category/species-category.service';
import { SpeciesCategory } from 'app/shared/model/species-category.model';

describe('Component Tests', () => {
  describe('SpeciesCategory Management Update Component', () => {
    let comp: SpeciesCategoryUpdateComponent;
    let fixture: ComponentFixture<SpeciesCategoryUpdateComponent>;
    let service: SpeciesCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [SpeciesCategoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SpeciesCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpeciesCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpeciesCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpeciesCategory(123);
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
        const entity = new SpeciesCategory();
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
