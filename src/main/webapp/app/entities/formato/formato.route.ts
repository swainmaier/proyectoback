import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFormato, Formato } from 'app/shared/model/formato.model';
import { FormatoService } from './formato.service';
import { FormatoComponent } from './formato.component';
import { FormatoDetailComponent } from './formato-detail.component';
import { FormatoUpdateComponent } from './formato-update.component';

@Injectable({ providedIn: 'root' })
export class FormatoResolve implements Resolve<IFormato> {
  constructor(private service: FormatoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormato> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((formato: HttpResponse<Formato>) => {
          if (formato.body) {
            return of(formato.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Formato());
  }
}

export const formatoRoute: Routes = [
  {
    path: '',
    component: FormatoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Formatoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormatoDetailComponent,
    resolve: {
      formato: FormatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Formatoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormatoUpdateComponent,
    resolve: {
      formato: FormatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Formatoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormatoUpdateComponent,
    resolve: {
      formato: FormatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Formatoes'
    },
    canActivate: [UserRouteAccessService]
  }
];
