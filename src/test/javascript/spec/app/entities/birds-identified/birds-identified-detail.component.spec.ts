import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { BirdsIdentifiedDetailComponent } from 'app/entities/birds-identified/birds-identified-detail.component';
import { BirdsIdentified } from 'app/shared/model/birds-identified.model';

describe('Component Tests', () => {
  describe('BirdsIdentified Management Detail Component', () => {
    let comp: BirdsIdentifiedDetailComponent;
    let fixture: ComponentFixture<BirdsIdentifiedDetailComponent>;
    const route = ({ data: of({ birdsIdentified: new BirdsIdentified(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [BirdsIdentifiedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BirdsIdentifiedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BirdsIdentifiedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load birdsIdentified on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.birdsIdentified).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
