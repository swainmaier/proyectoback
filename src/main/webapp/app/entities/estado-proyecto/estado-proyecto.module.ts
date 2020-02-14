import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { EstadoProyectoComponent } from './estado-proyecto.component';
import { EstadoProyectoDetailComponent } from './estado-proyecto-detail.component';
import { EstadoProyectoUpdateComponent } from './estado-proyecto-update.component';
import { EstadoProyectoDeleteDialogComponent } from './estado-proyecto-delete-dialog.component';
import { estadoProyectoRoute } from './estado-proyecto.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(estadoProyectoRoute)],
  declarations: [
    EstadoProyectoComponent,
    EstadoProyectoDetailComponent,
    EstadoProyectoUpdateComponent,
    EstadoProyectoDeleteDialogComponent
  ],
  entryComponents: [EstadoProyectoDeleteDialogComponent]
})
export class RedmProyectosEstadoProyectoModule {}
