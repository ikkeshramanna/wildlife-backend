import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorActionUpdateComponent } from 'app/entities/competitor-action/competitor-action-update.component';
import { CompetitorActionService } from 'app/entities/competitor-action/competitor-action.service';
import { CompetitorAction } from 'app/shared/model/competitor-action.model';

describe('Component Tests', () => {
  describe('CompetitorAction Management Update Component', () => {
    let comp: CompetitorActionUpdateComponent;
    let fixture: ComponentFixture<CompetitorActionUpdateComponent>;
    let service: CompetitorActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorActionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompetitorActionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitorActionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitorActionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompetitorAction(123);
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
        const entity = new CompetitorAction();
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
