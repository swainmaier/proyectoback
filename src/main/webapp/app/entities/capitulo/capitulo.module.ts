import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { CapituloComponent } from './capitulo.component';
import { CapituloDetailComponent } from './capitulo-detail.component';
import { CapituloUpdateComponent } from './capitulo-update.component';
import { CapituloDeleteDialogComponent } from './capitulo-delete-dialog.component';
import { capituloRoute } from './capitulo.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(capituloRoute)],
  declarations: [CapituloComponent, CapituloDetailComponent, CapituloUpdateComponent, CapituloDeleteDialogComponent],
  entryComponents: [CapituloDeleteDialogComponent]
})
export class RedmProyectosCapituloModule {}
