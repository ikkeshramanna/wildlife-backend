import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { EggDetailComponent } from 'app/entities/egg/egg-detail.component';
import { Egg } from 'app/shared/model/egg.model';

describe('Component Tests', () => {
  describe('Egg Management Detail Component', () => {
    let comp: EggDetailComponent;
    let fixture: ComponentFixture<EggDetailComponent>;
    const route = ({ data: of({ egg: new Egg(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [EggDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EggDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EggDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load egg on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.egg).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
