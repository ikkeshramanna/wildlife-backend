import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { EggComponent } from 'app/entities/egg/egg.component';
import { EggService } from 'app/entities/egg/egg.service';
import { Egg } from 'app/shared/model/egg.model';

describe('Component Tests', () => {
  describe('Egg Management Component', () => {
    let comp: EggComponent;
    let fixture: ComponentFixture<EggComponent>;
    let service: EggService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [EggComponent],
      })
        .overrideTemplate(EggComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EggComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EggService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Egg(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eggs && comp.eggs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
