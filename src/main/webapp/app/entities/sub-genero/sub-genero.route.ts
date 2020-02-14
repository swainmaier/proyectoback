import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubGenero, SubGenero } from 'app/shared/model/sub-genero.model';
import { SubGeneroService } from './sub-genero.service';
import { SubGeneroComponent } from './sub-genero.component';
import { SubGeneroDetailComponent } from './sub-genero-detail.component';
import { SubGeneroUpdateComponent } from './sub-genero-update.component';

@Injectable({ providedIn: 'root' })
export class SubGeneroResolve implements Resolve<ISubGenero> {
  constructor(private service: SubGeneroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubGenero> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subGenero: HttpResponse<SubGenero>) => {
          if (subGenero.body) {
            return of(subGenero.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubGenero());
  }
}

export const subGeneroRoute: Routes = [
  {
    path: '',
    component: SubGeneroComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SubGeneros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubGeneroDetailComponent,
    resolve: {
      subGenero: SubGeneroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SubGeneros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubGeneroUpdateComponent,
    resolve: {
      subGenero: SubGeneroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SubGeneros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubGeneroUpdateComponent,
    resolve: {
      subGenero: SubGeneroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SubGeneros'
    },
    canActivate: [UserRouteAccessService]
  }
];
