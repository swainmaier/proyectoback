import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { TargetComponent } from './target.component';
import { TargetDetailComponent } from './target-detail.component';
import { TargetUpdateComponent } from './target-update.component';
import { TargetDeleteDialogComponent } from './target-delete-dialog.component';
import { targetRoute } from './target.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(targetRoute)],
  declarations: [TargetComponent, TargetDetailComponent, TargetUpdateComponent, TargetDeleteDialogComponent],
  entryComponents: [TargetDeleteDialogComponent]
})
export class RedmProyectosTargetModule {}
