import { ITemporada } from 'app/shared/model/temporada.model';
import { ITrailer } from 'app/shared/model/trailer.model';
import { ITeaser } from 'app/shared/model/teaser.model';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { IEstadoContenido } from 'app/shared/model/estado-contenido.model';
import { IFormato } from 'app/shared/model/formato.model';
import { IGenero } from 'app/shared/model/genero.model';
import { IPlataforma } from 'app/shared/model/plataforma.model';

export interface IContenido {
  id?: number;
  titulo?: string;
  cantTemporadas?: number;
  fechaPublicacion?: string;
  caratula?: string;
  temporadas?: ITemporada[];
  trailers?: ITrailer[];
  teasers?: ITeaser[];
  proyecto?: IProyecto;
  estadoContenido?: IEstadoContenido;
  formato?: IFormato;
  genero?: IGenero;
  plataforma?: IPlataforma;
}

export class Contenido implements IContenido {
  constructor(
    public id?: number,
    public titulo?: string,
    public cantTemporadas?: number,
    public fechaPublicacion?: string,
    public caratula?: string,
    public temporadas?: ITemporada[],
    public trailers?: ITrailer[],
    public teasers?: ITeaser[],
    public proyecto?: IProyecto,
    public estadoContenido?: IEstadoContenido,
    public formato?: IFormato,
    public genero?: IGenero,
    public plataforma?: IPlataforma
  ) {}
}
