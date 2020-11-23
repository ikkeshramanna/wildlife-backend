import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { NestSiteOverviewComponent } from 'app/entities/nest-site-overview/nest-site-overview.component';
import { NestSiteOverviewService } from 'app/entities/nest-site-overview/nest-site-overview.service';
import { NestSiteOverview } from 'app/shared/model/nest-site-overview.model';

describe('Component Tests', () => {
  describe('NestSiteOverview Management Component', () => {
    let comp: NestSiteOverviewComponent;
    let fixture: ComponentFixture<NestSiteOverviewComponent>;
    let service: NestSiteOverviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [NestSiteOverviewComponent],
      })
        .overrideTemplate(NestSiteOverviewComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NestSiteOverviewComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NestSiteOverviewService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NestSiteOverview(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.nestSiteOverviews && comp.nestSiteOverviews[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
