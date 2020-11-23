import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { BirdsIdentifiedComponent } from 'app/entities/birds-identified/birds-identified.component';
import { BirdsIdentifiedService } from 'app/entities/birds-identified/birds-identified.service';
import { BirdsIdentified } from 'app/shared/model/birds-identified.model';

describe('Component Tests', () => {
  describe('BirdsIdentified Management Component', () => {
    let comp: BirdsIdentifiedComponent;
    let fixture: ComponentFixture<BirdsIdentifiedComponent>;
    let service: BirdsIdentifiedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [BirdsIdentifiedComponent],
      })
        .overrideTemplate(BirdsIdentifiedComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BirdsIdentifiedComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BirdsIdentifiedService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BirdsIdentified(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.birdsIdentifieds && comp.birdsIdentifieds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
