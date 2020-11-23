import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { BirdBehaviourComponent } from 'app/entities/bird-behaviour/bird-behaviour.component';
import { BirdBehaviourService } from 'app/entities/bird-behaviour/bird-behaviour.service';
import { BirdBehaviour } from 'app/shared/model/bird-behaviour.model';

describe('Component Tests', () => {
  describe('BirdBehaviour Management Component', () => {
    let comp: BirdBehaviourComponent;
    let fixture: ComponentFixture<BirdBehaviourComponent>;
    let service: BirdBehaviourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [BirdBehaviourComponent],
      })
        .overrideTemplate(BirdBehaviourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BirdBehaviourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BirdBehaviourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BirdBehaviour(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.birdBehaviours && comp.birdBehaviours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
