import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { EggUpdateComponent } from 'app/entities/egg/egg-update.component';
import { EggService } from 'app/entities/egg/egg.service';
import { Egg } from 'app/shared/model/egg.model';

describe('Component Tests', () => {
  describe('Egg Management Update Component', () => {
    let comp: EggUpdateComponent;
    let fixture: ComponentFixture<EggUpdateComponent>;
    let service: EggService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [EggUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EggUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EggUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EggService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Egg(123);
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
        const entity = new Egg();
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
