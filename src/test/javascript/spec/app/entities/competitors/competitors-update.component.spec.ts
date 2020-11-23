import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorsUpdateComponent } from 'app/entities/competitors/competitors-update.component';
import { CompetitorsService } from 'app/entities/competitors/competitors.service';
import { Competitors } from 'app/shared/model/competitors.model';

describe('Component Tests', () => {
  describe('Competitors Management Update Component', () => {
    let comp: CompetitorsUpdateComponent;
    let fixture: ComponentFixture<CompetitorsUpdateComponent>;
    let service: CompetitorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompetitorsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitorsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitorsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Competitors(123);
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
        const entity = new Competitors();
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
