import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { EstadoContenidoComponent } from './estado-contenido.component';
import { EstadoContenidoDetailComponent } from './estado-contenido-detail.component';
import { EstadoContenidoUpdateComponent } from './estado-contenido-update.component';
import { EstadoContenidoDeleteDialogComponent } from './estado-contenido-delete-dialog.component';
import { estadoContenidoRoute } from './estado-contenido.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(estadoContenidoRoute)],
  declarations: [
    EstadoContenidoComponent,
    EstadoContenidoDetailComponent,
    EstadoContenidoUpdateComponent,
    EstadoContenidoDeleteDialogComponent
  ],
  entryComponents: [EstadoContenidoDeleteDialogComponent]
})
export class RedmProyectosEstadoContenidoModule {}
