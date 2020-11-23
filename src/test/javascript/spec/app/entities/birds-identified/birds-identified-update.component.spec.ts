import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { BirdsIdentifiedUpdateComponent } from 'app/entities/birds-identified/birds-identified-update.component';
import { BirdsIdentifiedService } from 'app/entities/birds-identified/birds-identified.service';
import { BirdsIdentified } from 'app/shared/model/birds-identified.model';

describe('Component Tests', () => {
  describe('BirdsIdentified Management Update Component', () => {
    let comp: BirdsIdentifiedUpdateComponent;
    let fixture: ComponentFixture<BirdsIdentifiedUpdateComponent>;
    let service: BirdsIdentifiedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [BirdsIdentifiedUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BirdsIdentifiedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BirdsIdentifiedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BirdsIdentifiedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BirdsIdentified(123);
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
        const entity = new BirdsIdentified();
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
