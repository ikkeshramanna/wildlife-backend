import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { BirdBehaviourDetailComponent } from 'app/entities/bird-behaviour/bird-behaviour-detail.component';
import { BirdBehaviour } from 'app/shared/model/bird-behaviour.model';

describe('Component Tests', () => {
  describe('BirdBehaviour Management Detail Component', () => {
    let comp: BirdBehaviourDetailComponent;
    let fixture: ComponentFixture<BirdBehaviourDetailComponent>;
    const route = ({ data: of({ birdBehaviour: new BirdBehaviour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [BirdBehaviourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BirdBehaviourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BirdBehaviourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load birdBehaviour on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.birdBehaviour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
