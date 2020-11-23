import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { RingingMorphsUpdateComponent } from 'app/entities/ringing-morphs/ringing-morphs-update.component';
import { RingingMorphsService } from 'app/entities/ringing-morphs/ringing-morphs.service';
import { RingingMorphs } from 'app/shared/model/ringing-morphs.model';

describe('Component Tests', () => {
  describe('RingingMorphs Management Update Component', () => {
    let comp: RingingMorphsUpdateComponent;
    let fixture: ComponentFixture<RingingMorphsUpdateComponent>;
    let service: RingingMorphsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [RingingMorphsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RingingMorphsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RingingMorphsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RingingMorphsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RingingMorphs(123);
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
        const entity = new RingingMorphs();
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
