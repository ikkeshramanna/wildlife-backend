import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { ChickUpdateComponent } from 'app/entities/chick/chick-update.component';
import { ChickService } from 'app/entities/chick/chick.service';
import { Chick } from 'app/shared/model/chick.model';

describe('Component Tests', () => {
  describe('Chick Management Update Component', () => {
    let comp: ChickUpdateComponent;
    let fixture: ComponentFixture<ChickUpdateComponent>;
    let service: ChickService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [ChickUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ChickUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChickUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChickService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Chick(123);
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
        const entity = new Chick();
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
