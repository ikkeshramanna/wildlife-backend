import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { WildlifeTestModule } from '../../../test.module';
import { SpeciesCategoryDetailComponent } from 'app/entities/species-category/species-category-detail.component';
import { SpeciesCategory } from 'app/shared/model/species-category.model';

describe('Component Tests', () => {
  describe('SpeciesCategory Management Detail Component', () => {
    let comp: SpeciesCategoryDetailComponent;
    let fixture: ComponentFixture<SpeciesCategoryDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ speciesCategory: new SpeciesCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [SpeciesCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SpeciesCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpeciesCategoryDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load speciesCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.speciesCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
