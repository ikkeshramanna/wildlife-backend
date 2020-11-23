import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WildlifeTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CompetitorImpactOnMkDeleteDialogComponent } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk-delete-dialog.component';
import { CompetitorImpactOnMkService } from 'app/entities/competitor-impact-on-mk/competitor-impact-on-mk.service';

describe('Component Tests', () => {
  describe('CompetitorImpactOnMk Management Delete Component', () => {
    let comp: CompetitorImpactOnMkDeleteDialogComponent;
    let fixture: ComponentFixture<CompetitorImpactOnMkDeleteDialogComponent>;
    let service: CompetitorImpactOnMkService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [CompetitorImpactOnMkDeleteDialogComponent],
      })
        .overrideTemplate(CompetitorImpactOnMkDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitorImpactOnMkDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetitorImpactOnMkService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
