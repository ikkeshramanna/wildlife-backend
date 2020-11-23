import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorImpactOnMkDetailComponent } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk-detail.component';
import { CompetitorImpactOnMk } from 'app/shared/model/competitor-impact-on-mk.model';

describe('Component Tests', () => {
  describe('CompetitorImpactOnMk Management Detail Component', () => {
    let comp: CompetitorImpactOnMkDetailComponent;
    let fixture: ComponentFixture<CompetitorImpactOnMkDetailComponent>;
    const route = ({ data: of({ competitorImpactOnMk: new CompetitorImpactOnMk(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorImpactOnMkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompetitorImpactOnMkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitorImpactOnMkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load competitorImpactOnMk on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competitorImpactOnMk).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
