import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrailer, Trailer } from 'app/shared/model/trailer.model';
import { TrailerService } from './trailer.service';
import { TrailerComponent } from './trailer.component';
import { TrailerDetailComponent } from './trailer-detail.component';
import { TrailerUpdateComponent } from './trailer-update.component';

@Injectable({ providedIn: 'root' })
export class TrailerResolve implements Resolve<ITrailer> {
  constructor(private service: TrailerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrailer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trailer: HttpResponse<Trailer>) => {
          if (trailer.body) {
            return of(trailer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Trailer());
  }
}

export const trailerRoute: Routes = [
  {
    path: '',
    component: TrailerComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trailers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrailerDetailComponent,
    resolve: {
      trailer: TrailerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trailers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrailerUpdateComponent,
    resolve: {
      trailer: TrailerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trailers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrailerUpdateComponent,
    resolve: {
      trailer: TrailerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Trailers'
    },
    canActivate: [UserRouteAccessService]
  }
];
