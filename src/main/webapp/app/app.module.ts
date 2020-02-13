import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RedmProyectosSharedModule } from 'app/shared/shared.module';
import { RedmProyectosCoreModule } from 'app/core/core.module';
import { RedmProyectosAppRoutingModule } from './app-routing.module';
import { RedmProyectosHomeModule } from './home/home.module';
import { RedmProyectosEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RedmProyectosSharedModule,
    RedmProyectosCoreModule,
    RedmProyectosHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RedmProyectosEntityModule,
    RedmProyectosAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class RedmProyectosAppModule {}
