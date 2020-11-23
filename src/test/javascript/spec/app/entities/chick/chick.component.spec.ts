import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { ChickComponent } from 'app/entities/chick/chick.component';
import { ChickService } from 'app/entities/chick/chick.service';
import { Chick } from 'app/shared/model/chick.model';

describe('Component Tests', () => {
  describe('Chick Management Component', () => {
    let comp: ChickComponent;
    let fixture: ComponentFixture<ChickComponent>;
    let service: ChickService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [ChickComponent],
      })
        .overrideTemplate(ChickComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChickComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChickService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Chick(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.chicks && comp.chicks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
