import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { NestSiteOverviewUpdateComponent } from 'app/entities/nest-site-overview/nest-site-overview-update.component';
import { NestSiteOverviewService } from 'app/entities/nest-site-overview/nest-site-overview.service';
import { NestSiteOverview } from 'app/shared/model/nest-site-overview.model';

describe('Component Tests', () => {
  describe('NestSiteOverview Management Update Component', () => {
    let comp: NestSiteOverviewUpdateComponent;
    let fixture: ComponentFixture<NestSiteOverviewUpdateComponent>;
    let service: NestSiteOverviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [NestSiteOverviewUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NestSiteOverviewUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NestSiteOverviewUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NestSiteOverviewService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NestSiteOverview(123);
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
        const entity = new NestSiteOverview();
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
