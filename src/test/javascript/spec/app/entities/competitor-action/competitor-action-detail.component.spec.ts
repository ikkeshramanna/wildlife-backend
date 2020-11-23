import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorActionDetailComponent } from 'app/entities/competitor-action/competitor-action-detail.component';
import { CompetitorAction } from 'app/shared/model/competitor-action.model';

describe('Component Tests', () => {
  describe('CompetitorAction Management Detail Component', () => {
    let comp: CompetitorActionDetailComponent;
    let fixture: ComponentFixture<CompetitorActionDetailComponent>;
    const route = ({ data: of({ competitorAction: new CompetitorAction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorActionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompetitorActionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitorActionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load competitorAction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competitorAction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
