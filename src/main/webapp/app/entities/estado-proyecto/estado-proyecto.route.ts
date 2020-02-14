import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEstadoProyecto, EstadoProyecto } from 'app/shared/model/estado-proyecto.model';
import { EstadoProyectoService } from './estado-proyecto.service';
import { EstadoProyectoComponent } from './estado-proyecto.component';
import { EstadoProyectoDetailComponent } from './estado-proyecto-detail.component';
import { EstadoProyectoUpdateComponent } from './estado-proyecto-update.component';

@Injectable({ providedIn: 'root' })
export class EstadoProyectoResolve implements Resolve<IEstadoProyecto> {
  constructor(private service: EstadoProyectoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoProyecto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((estadoProyecto: HttpResponse<EstadoProyecto>) => {
          if (estadoProyecto.body) {
            return of(estadoProyecto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoProyecto());
  }
}

export const estadoProyectoRoute: Routes = [
  {
    path: '',
    component: EstadoProyectoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoProyectos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoProyectoDetailComponent,
    resolve: {
      estadoProyecto: EstadoProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoProyectos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoProyectoUpdateComponent,
    resolve: {
      estadoProyecto: EstadoProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoProyectos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoProyectoUpdateComponent,
    resolve: {
      estadoProyecto: EstadoProyectoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoProyectos'
    },
    canActivate: [UserRouteAccessService]
  }
];
