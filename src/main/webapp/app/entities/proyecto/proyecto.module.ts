import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { ProyectoComponent } from './proyecto.component';
import { ProyectoDetailComponent } from './proyecto-detail.component';
import { ProyectoUpdateComponent } from './proyecto-update.component';
import { ProyectoDeleteDialogComponent } from './proyecto-delete-dialog.component';
import { proyectoRoute } from './proyecto.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(proyectoRoute)],
  declarations: [ProyectoComponent, ProyectoDetailComponent, ProyectoUpdateComponent, ProyectoDeleteDialogComponent],
  entryComponents: [ProyectoDeleteDialogComponent]
})
export class RedmProyectosProyectoModule {}
