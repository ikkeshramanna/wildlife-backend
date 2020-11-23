import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { RingingMorphsComponent } from 'app/entities/ringing-morphs/ringing-morphs.component';
import { RingingMorphsService } from 'app/entities/ringing-morphs/ringing-morphs.service';
import { RingingMorphs } from 'app/shared/model/ringing-morphs.model';

describe('Component Tests', () => {
  describe('RingingMorphs Management Component', () => {
    let comp: RingingMorphsComponent;
    let fixture: ComponentFixture<RingingMorphsComponent>;
    let service: RingingMorphsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [RingingMorphsComponent],
      })
        .overrideTemplate(RingingMorphsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RingingMorphsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RingingMorphsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RingingMorphs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ringingMorphs && comp.ringingMorphs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
