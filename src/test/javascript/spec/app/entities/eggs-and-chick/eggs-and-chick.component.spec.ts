import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { EggsAndChickComponent } from 'app/entities/eggs-and-chick/eggs-and-chick.component';
import { EggsAndChickService } from 'app/entities/eggs-and-chick/eggs-and-chick.service';
import { EggsAndChick } from 'app/shared/model/eggs-and-chick.model';

describe('Component Tests', () => {
  describe('EggsAndChick Management Component', () => {
    let comp: EggsAndChickComponent;
    let fixture: ComponentFixture<EggsAndChickComponent>;
    let service: EggsAndChickService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [EggsAndChickComponent],
      })
        .overrideTemplate(EggsAndChickComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EggsAndChickComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EggsAndChickService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EggsAndChick(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eggsAndChicks && comp.eggsAndChicks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
