import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { SpeciesCategoryComponent } from 'app/entities/species-category/species-category.component';
import { SpeciesCategoryService } from 'app/entities/species-category/species-category.service';
import { SpeciesCategory } from 'app/shared/model/species-category.model';

describe('Component Tests', () => {
  describe('SpeciesCategory Management Component', () => {
    let comp: SpeciesCategoryComponent;
    let fixture: ComponentFixture<SpeciesCategoryComponent>;
    let service: SpeciesCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [SpeciesCategoryComponent],
      })
        .overrideTemplate(SpeciesCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpeciesCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpeciesCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SpeciesCategory(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.speciesCategories && comp.speciesCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
