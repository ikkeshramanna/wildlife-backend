import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WildlifeTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { BirdsIdentifiedDeleteDialogComponent } from 'app/entities/birds-identified/birds-identified-delete-dialog.component';
import { BirdsIdentifiedService } from 'app/entities/birds-identified/birds-identified.service';

describe('Component Tests', () => {
  describe('BirdsIdentified Management Delete Component', () => {
    let comp: BirdsIdentifiedDeleteDialogComponent;
    let fixture: ComponentFixture<BirdsIdentifiedDeleteDialogComponent>;
    let service: BirdsIdentifiedService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WildlifeTestModule],
        declarations: [BirdsIdentifiedDeleteDialogComponent],
      })
        .overrideTemplate(BirdsIdentifiedDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BirdsIdentifiedDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BirdsIdentifiedService);
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
