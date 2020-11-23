import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorImpactOnMkUpdateComponent } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk-update.component';
import { CompetitorImpactOnMkService } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk.service';
import { CompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';

describe('Component Tests', () => {
  describe('CompetitorImpactOnMk Management Update Component', () => {
    let comp: CompetitorImpactOnMkUpdateComponent;
    let fixture: ComponentFixture<CompetitorImpactOnMkUpdateComponent>;
    let service: CompetitorImpactOnMkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorImpactOnMkUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompetitorImpactOnMkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitorImpactOnMkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitorImpactOnMkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompetitorImpactOnMk(123);
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
        const entity = new CompetitorImpactOnMk();
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
