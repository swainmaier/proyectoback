import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'proyecto',
        loadChildren: () => import('./proyecto/proyecto.module').then(m => m.RedmProyectosProyectoModule)
      },
      {
        path: 'capitulo',
        loadChildren: () => import('./capitulo/capitulo.module').then(m => m.RedmProyectosCapituloModule)
      },
      {
        path: 'contenido',
        loadChildren: () => import('./contenido/contenido.module').then(m => m.RedmProyectosContenidoModule)
      },
      {
        path: 'temporada',
        loadChildren: () => import('./temporada/temporada.module').then(m => m.RedmProyectosTemporadaModule)
      },
      {
        path: 'trailer',
        loadChildren: () => import('./trailer/trailer.module').then(m => m.RedmProyectosTrailerModule)
      },
      {
        path: 'teaser',
        loadChildren: () => import('./teaser/teaser.module').then(m => m.RedmProyectosTeaserModule)
      },
      {
        path: 'estado-proyecto',
        loadChildren: () => import('./estado-proyecto/estado-proyecto.module').then(m => m.RedmProyectosEstadoProyectoModule)
      },
      {
        path: 'target',
        loadChildren: () => import('./target/target.module').then(m => m.RedmProyectosTargetModule)
      },
      {
        path: 'estado-contenido',
        loadChildren: () => import('./estado-contenido/estado-contenido.module').then(m => m.RedmProyectosEstadoContenidoModule)
      },
      {
        path: 'plataforma',
        loadChildren: () => import('./plataforma/plataforma.module').then(m => m.RedmProyectosPlataformaModule)
      },
      {
        path: 'formato',
        loadChildren: () => import('./formato/formato.module').then(m => m.RedmProyectosFormatoModule)
      },
      {
        path: 'genero',
        loadChildren: () => import('./genero/genero.module').then(m => m.RedmProyectosGeneroModule)
      },
      {
        path: 'sub-genero',
        loadChildren: () => import('./sub-genero/sub-genero.module').then(m => m.RedmProyectosSubGeneroModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class RedmProyectosEntityModule {}
