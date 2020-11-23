import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { EggsAndChickUpdateComponent } from 'app/entities/eggs-and-chick/eggs-and-chick-update.component';
import { EggsAndChickService } from 'app/entities/eggs-and-chick/eggs-and-chick.service';
import { EggsAndChick } from 'app/shared/model/eggs-and-chick.model';

describe('Component Tests', () => {
  describe('EggsAndChick Management Update Component', () => {
    let comp: EggsAndChickUpdateComponent;
    let fixture: ComponentFixture<EggsAndChickUpdateComponent>;
    let service: EggsAndChickService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [EggsAndChickUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EggsAndChickUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EggsAndChickUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EggsAndChickService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EggsAndChick(123);
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
        const entity = new EggsAndChick();
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
