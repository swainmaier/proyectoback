import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { SubGeneroComponent } from './sub-genero.component';
import { SubGeneroDetailComponent } from './sub-genero-detail.component';
import { SubGeneroUpdateComponent } from './sub-genero-update.component';
import { SubGeneroDeleteDialogComponent } from './sub-genero-delete-dialog.component';
import { subGeneroRoute } from './sub-genero.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(subGeneroRoute)],
  declarations: [SubGeneroComponent, SubGeneroDetailComponent, SubGeneroUpdateComponent, SubGeneroDeleteDialogComponent],
  entryComponents: [SubGeneroDeleteDialogComponent]
})
export class RedmProyectosSubGeneroModule {}
