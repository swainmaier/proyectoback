import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { TeaserComponent } from './teaser.component';
import { TeaserDetailComponent } from './teaser-detail.component';
import { TeaserUpdateComponent } from './teaser-update.component';
import { TeaserDeleteDialogComponent } from './teaser-delete-dialog.component';
import { teaserRoute } from './teaser.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(teaserRoute)],
  declarations: [TeaserComponent, TeaserDetailComponent, TeaserUpdateComponent, TeaserDeleteDialogComponent],
  entryComponents: [TeaserDeleteDialogComponent]
})
export class RedmProyectosTeaserModule {}
