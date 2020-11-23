import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { NestSiteOverviewDetailComponent } from 'app/entities/nest-site-overview/nest-site-overview-detail.component';
import { NestSiteOverview } from 'app/shared/model/nest-site-overview.model';

describe('Component Tests', () => {
  describe('NestSiteOverview Management Detail Component', () => {
    let comp: NestSiteOverviewDetailComponent;
    let fixture: ComponentFixture<NestSiteOverviewDetailComponent>;
    const route = ({ data: of({ nestSiteOverview: new NestSiteOverview(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [NestSiteOverviewDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NestSiteOverviewDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NestSiteOverviewDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nestSiteOverview on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nestSiteOverview).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
