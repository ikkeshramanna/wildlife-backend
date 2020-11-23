import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WildlifeTestModule } from '../../../test.module';
import { MaintenanceDetailComponent } from 'app/entities/maintenance/maintenance-detail.component';
import { Maintenance } from 'app/shared/model/maintenance.model';

describe('Component Tests', () => {
  describe('Maintenance Management Detail Component', () => {
    let comp: MaintenanceDetailComponent;
    let fixture: ComponentFixture<MaintenanceDetailComponent>;
    const route = ({ data: of({ maintenance: new Maintenance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [MaintenanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MaintenanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaintenanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load maintenance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.maintenance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});