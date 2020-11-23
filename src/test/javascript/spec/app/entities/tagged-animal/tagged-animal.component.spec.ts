import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WildlifeTestModule } from '../../../test.module';
import { TaggedAnimalComponent } from 'app/entities/tagged-animal/tagged-animal.component';
import { TaggedAnimalService } from 'app/entities/tagged-animal/tagged-animal.service';
import { TaggedAnimal } from 'app/shared/model/tagged-animal.model';

describe('Component Tests', () => {
  describe('TaggedAnimal Management Component', () => {
    let comp: TaggedAnimalComponent;
    let fixture: ComponentFixture<TaggedAnimalComponent>;
    let service: TaggedAnimalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [TaggedAnimalComponent],
      })
        .overrideTemplate(TaggedAnimalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaggedAnimalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaggedAnimalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TaggedAnimal(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taggedAnimals && comp.taggedAnimals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
