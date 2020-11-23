import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorsDetailComponent } from 'app/entities/competitors/competitors-detail.component';
import { Competitors } from 'app/shared/model/competitors.model';

describe('Component Tests', () => {
  describe('Competitors Management Detail Component', () => {
    let comp: CompetitorsDetailComponent;
    let fixture: ComponentFixture<CompetitorsDetailComponent>;
    const route = ({ data: of({ competitors: new Competitors(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompetitorsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitorsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load competitors on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competitors).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
