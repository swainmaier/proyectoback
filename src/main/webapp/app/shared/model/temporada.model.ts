import { ICapitulo } from 'app/shared/model/capitulo.model';
import { IContenido } from 'app/shared/model/contenido.model';

export interface ITemporada {
  id?: number;
  titulo?: string;
  numero?: number;
  cantCapitulos?: number;
  duracionTotal?: number;
  capitulos?: ICapitulo[];
  contenido?: IContenido;
}

export class Temporada implements ITemporada {
  constructor(
    public id?: number,
    public titulo?: string,
    public numero?: number,
    public cantCapitulos?: number,
    public duracionTotal?: number,
    public capitulos?: ICapitulo[],
    public contenido?: IContenido
  ) {}
}
