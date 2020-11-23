import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorImpactOnMkComponent } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk.component';
import { CompetitorImpactOnMkService } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk.service';
import { CompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';

describe('Component Tests', () => {
  describe('CompetitorImpactOnMk Management Component', () => {
    let comp: CompetitorImpactOnMkComponent;
    let fixture: ComponentFixture<CompetitorImpactOnMkComponent>;
    let service: CompetitorImpactOnMkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorImpactOnMkComponent],
      })
        .overrideTemplate(CompetitorImpactOnMkComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitorImpactOnMkComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitorImpactOnMkService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompetitorImpactOnMk(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.competitorImpactOnMks && comp.competitorImpactOnMks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
