import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { ChickDetailComponent } from 'app/entities/chick/chick-detail.component';
import { Chick } from 'app/shared/model/chick.model';

describe('Component Tests', () => {
  describe('Chick Management Detail Component', () => {
    let comp: ChickDetailComponent;
    let fixture: ComponentFixture<ChickDetailComponent>;
    const route = ({ data: of({ chick: new Chick(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [ChickDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ChickDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChickDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load chick on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chick).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
