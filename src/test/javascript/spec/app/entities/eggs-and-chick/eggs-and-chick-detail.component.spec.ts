import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { EggsAndChickDetailComponent } from 'app/entities/eggs-and-chick/eggs-and-chick-detail.component';
import { EggsAndChick } from 'app/shared/model/eggs-and-chick.model';

describe('Component Tests', () => {
  describe('EggsAndChick Management Detail Component', () => {
    let comp: EggsAndChickDetailComponent;
    let fixture: ComponentFixture<EggsAndChickDetailComponent>;
    const route = ({ data: of({ eggsAndChick: new EggsAndChick(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [EggsAndChickDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EggsAndChickDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EggsAndChickDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eggsAndChick on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eggsAndChick).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
