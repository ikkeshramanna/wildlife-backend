import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { TaggedAnimalDetailComponent } from 'app/entities/tagged-animal/tagged-animal-detail.component';
import { TaggedAnimal } from 'app/shared/model/tagged-animal.model';

describe('Component Tests', () => {
  describe('TaggedAnimal Management Detail Component', () => {
    let comp: TaggedAnimalDetailComponent;
    let fixture: ComponentFixture<TaggedAnimalDetailComponent>;
    const route = ({ data: of({ taggedAnimal: new TaggedAnimal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [TaggedAnimalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaggedAnimalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaggedAnimalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taggedAnimal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taggedAnimal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
