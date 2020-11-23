import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaggedAnimal, TaggedAnimal } from 'app/shared/model/tagged-animal.model';
import { TaggedAnimalService } from './tagged-animal.service';
import { TaggedAnimalComponent } from './tagged-animal.component';
import { TaggedAnimalDetailComponent } from './tagged-animal-detail.component';
import { TaggedAnimalUpdateComponent } from './tagged-animal-update.component';

@Injectable({ providedIn: 'root' })
export class TaggedAnimalResolve implements Resolve<ITaggedAnimal> {
  constructor(private service: TaggedAnimalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaggedAnimal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taggedAnimal: HttpResponse<TaggedAnimal>) => {
          if (taggedAnimal.body) {
            return of(taggedAnimal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaggedAnimal());
  }
}

export const taggedAnimalRoute: Routes = [
  {
    path: '',
    component: TaggedAnimalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.taggedAnimal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaggedAnimalDetailComponent,
    resolve: {
      taggedAnimal: TaggedAnimalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.taggedAnimal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaggedAnimalUpdateComponent,
    resolve: {
      taggedAnimal: TaggedAnimalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.taggedAnimal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaggedAnimalUpdateComponent,
    resolve: {
      taggedAnimal: TaggedAnimalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'wildlifeApp.taggedAnimal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
