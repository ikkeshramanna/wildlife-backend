import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { RingingMorphsDetailComponent } from 'app/entities/ringing-morphs/ringing-morphs-detail.component';
import { RingingMorphs } from 'app/shared/model/ringing-morphs.model';

describe('Component Tests', () => {
  describe('RingingMorphs Management Detail Component', () => {
    let comp: RingingMorphsDetailComponent;
    let fixture: ComponentFixture<RingingMorphsDetailComponent>;
    const route = ({ data: of({ ringingMorphs: new RingingMorphs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [RingingMorphsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RingingMorphsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RingingMorphsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ringingMorphs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ringingMorphs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
