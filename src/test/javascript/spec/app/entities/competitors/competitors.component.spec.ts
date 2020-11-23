import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorsComponent } from 'app/entities/competitors/competitors.component';
import { CompetitorsService } from 'app/entities/competitors/competitors.service';
import { Competitors } from 'app/shared/model/competitors.model';

describe('Component Tests', () => {
  describe('Competitors Management Component', () => {
    let comp: CompetitorsComponent;
    let fixture: ComponentFixture<CompetitorsComponent>;
    let service: CompetitorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorsComponent],
      })
        .overrideTemplate(CompetitorsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitorsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitorsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Competitors(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.competitors && comp.competitors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
