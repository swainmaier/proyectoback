import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEstadoContenido, EstadoContenido } from 'app/shared/model/estado-contenido.model';
import { EstadoContenidoService } from './estado-contenido.service';
import { EstadoContenidoComponent } from './estado-contenido.component';
import { EstadoContenidoDetailComponent } from './estado-contenido-detail.component';
import { EstadoContenidoUpdateComponent } from './estado-contenido-update.component';

@Injectable({ providedIn: 'root' })
export class EstadoContenidoResolve implements Resolve<IEstadoContenido> {
  constructor(private service: EstadoContenidoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoContenido> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((estadoContenido: HttpResponse<EstadoContenido>) => {
          if (estadoContenido.body) {
            return of(estadoContenido.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoContenido());
  }
}

export const estadoContenidoRoute: Routes = [
  {
    path: '',
    component: EstadoContenidoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoContenidos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoContenidoDetailComponent,
    resolve: {
      estadoContenido: EstadoContenidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoContenidos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoContenidoUpdateComponent,
    resolve: {
      estadoContenido: EstadoContenidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoContenidos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoContenidoUpdateComponent,
    resolve: {
      estadoContenido: EstadoContenidoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoContenidos'
    },
    canActivate: [UserRouteAccessService]
  }
];
