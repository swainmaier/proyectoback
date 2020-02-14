import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { PlataformaComponent } from './plataforma.component';
import { PlataformaDetailComponent } from './plataforma-detail.component';
import { PlataformaUpdateComponent } from './plataforma-update.component';
import { PlataformaDeleteDialogComponent } from './plataforma-delete-dialog.component';
import { plataformaRoute } from './plataforma.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(plataformaRoute)],
  declarations: [PlataformaComponent, PlataformaDetailComponent, PlataformaUpdateComponent, PlataformaDeleteDialogComponent],
  entryComponents: [PlataformaDeleteDialogComponent]
})
export class RedmProyectosPlataformaModule {}
