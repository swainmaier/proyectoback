import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { TrailerComponent } from './trailer.component';
import { TrailerDetailComponent } from './trailer-detail.component';
import { TrailerUpdateComponent } from './trailer-update.component';
import { TrailerDeleteDialogComponent } from './trailer-delete-dialog.component';
import { trailerRoute } from './trailer.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(trailerRoute)],
  declarations: [TrailerComponent, TrailerDetailComponent, TrailerUpdateComponent, TrailerDeleteDialogComponent],
  entryComponents: [TrailerDeleteDialogComponent]
})
export class RedmProyectosTrailerModule {}
