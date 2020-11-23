import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { CompetitorActionComponent } from 'app/entities/competitor-action/competitor-action.component';
import { CompetitorActionService } from 'app/entities/competitor-action/competitor-action.service';
import { CompetitorAction } from 'app/shared/model/competitor-action.model';

describe('Component Tests', () => {
  describe('CompetitorAction Management Component', () => {
    let comp: CompetitorActionComponent;
    let fixture: ComponentFixture<CompetitorActionComponent>;
    let service: CompetitorActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorActionComponent],
      })
        .overrideTemplate(CompetitorActionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetitorActionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitorActionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CompetitorAction(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.competitorActions && comp.competitorActions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
