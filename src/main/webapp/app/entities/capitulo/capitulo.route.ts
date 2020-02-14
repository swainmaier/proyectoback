import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICapitulo, Capitulo } from 'app/shared/model/capitulo.model';
import { CapituloService } from './capitulo.service';
import { CapituloComponent } from './capitulo.component';
import { CapituloDetailComponent } from './capitulo-detail.component';
import { CapituloUpdateComponent } from './capitulo-update.component';

@Injectable({ providedIn: 'root' })
export class CapituloResolve implements Resolve<ICapitulo> {
  constructor(private service: CapituloService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICapitulo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((capitulo: HttpResponse<Capitulo>) => {
          if (capitulo.body) {
            return of(capitulo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Capitulo());
  }
}

export const capituloRoute: Routes = [
  {
    path: '',
    component: CapituloComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Capitulos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CapituloDetailComponent,
    resolve: {
      capitulo: CapituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Capitulos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CapituloUpdateComponent,
    resolve: {
      capitulo: CapituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Capitulos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CapituloUpdateComponent,
    resolve: {
      capitulo: CapituloResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Capitulos'
    },
    canActivate: [UserRouteAccessService]
  }
];
