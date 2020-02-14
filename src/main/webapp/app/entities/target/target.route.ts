import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITarget, Target } from 'app/shared/model/target.model';
import { TargetService } from './target.service';
import { TargetComponent } from './target.component';
import { TargetDetailComponent } from './target-detail.component';
import { TargetUpdateComponent } from './target-update.component';

@Injectable({ providedIn: 'root' })
export class TargetResolve implements Resolve<ITarget> {
  constructor(private service: TargetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITarget> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((target: HttpResponse<Target>) => {
          if (target.body) {
            return of(target.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Target());
  }
}

export const targetRoute: Routes = [
  {
    path: '',
    component: TargetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Targets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TargetDetailComponent,
    resolve: {
      target: TargetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Targets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TargetUpdateComponent,
    resolve: {
      target: TargetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Targets'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TargetUpdateComponent,
    resolve: {
      target: TargetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Targets'
    },
    canActivate: [UserRouteAccessService]
  }
];
