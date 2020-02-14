import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { GeneroComponent } from './genero.component';
import { GeneroDetailComponent } from './genero-detail.component';
import { GeneroUpdateComponent } from './genero-update.component';
import { GeneroDeleteDialogComponent } from './genero-delete-dialog.component';
import { generoRoute } from './genero.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(generoRoute)],
  declarations: [GeneroComponent, GeneroDetailComponent, GeneroUpdateComponent, GeneroDeleteDialogComponent],
  entryComponents: [GeneroDeleteDialogComponent]
})
export class RedmProyectosGeneroModule {}
