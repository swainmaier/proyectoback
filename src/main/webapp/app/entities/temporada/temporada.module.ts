import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { TemporadaComponent } from './temporada.component';
import { TemporadaDetailComponent } from './temporada-detail.component';
import { TemporadaUpdateComponent } from './temporada-update.component';
import { TemporadaDeleteDialogComponent } from './temporada-delete-dialog.component';
import { temporadaRoute } from './temporada.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(temporadaRoute)],
  declarations: [TemporadaComponent, TemporadaDetailComponent, TemporadaUpdateComponent, TemporadaDeleteDialogComponent],
  entryComponents: [TemporadaDeleteDialogComponent]
})
export class RedmProyectosTemporadaModule {}
