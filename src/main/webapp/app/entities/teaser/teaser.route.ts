import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITeaser, Teaser } from 'app/shared/model/teaser.model';
import { TeaserService } from './teaser.service';
import { TeaserComponent } from './teaser.component';
import { TeaserDetailComponent } from './teaser-detail.component';
import { TeaserUpdateComponent } from './teaser-update.component';

@Injectable({ providedIn: 'root' })
export class TeaserResolve implements Resolve<ITeaser> {
  constructor(private service: TeaserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeaser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((teaser: HttpResponse<Teaser>) => {
          if (teaser.body) {
            return of(teaser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Teaser());
  }
}

export const teaserRoute: Routes = [
  {
    path: '',
    component: TeaserComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Teasers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TeaserDetailComponent,
    resolve: {
      teaser: TeaserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Teasers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TeaserUpdateComponent,
    resolve: {
      teaser: TeaserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Teasers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TeaserUpdateComponent,
    resolve: {
      teaser: TeaserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Teasers'
    },
    canActivate: [UserRouteAccessService]
  }
];
