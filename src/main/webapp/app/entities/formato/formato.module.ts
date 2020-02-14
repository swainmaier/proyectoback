import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { FormatoComponent } from './formato.component';
import { FormatoDetailComponent } from './formato-detail.component';
import { FormatoUpdateComponent } from './formato-update.component';
import { FormatoDeleteDialogComponent } from './formato-delete-dialog.component';
import { formatoRoute } from './formato.route';

@NgModule({
  imports: [RedmProyectosSharedModule, RouterModule.forChild(formatoRoute)],
  declarations: [FormatoComponent, FormatoDetailComponent, FormatoUpdateComponent, FormatoDeleteDialogComponent],
  entryComponents: [FormatoDeleteDialogComponent]
})
export class RedmProyectosFormatoModule {}
