import { IContenido } from 'app/shared/model/contenido.model';
import { IEstadoProyecto } from 'app/shared/model/estado-proyecto.model';
import { ITarget } from 'app/shared/model/target.model';

export interface IProyecto {
  id?: number;
  titulo?: string;
  sumary?: string;
  slogan?: string;
  code?: string;
  fechaPub?: string;
  sinopsis?: string;
  storyline?: string;
  logline?: string;
  imagenContentType?: string;
  imagen?: any;
  contenidos?: IContenido[];
  estadoProyecto?: IEstadoProyecto;
  target?: ITarget;
}

export class Proyecto implements IProyecto {
  constructor(
    public id?: number,
    public titulo?: string,
    public sumary?: string,
    public slogan?: string,
    public code?: string,
    public fechaPub?: string,
    public sinopsis?: string,
    public storyline?: string,
    public logline?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public contenidos?: IContenido[],
    public estadoProyecto?: IEstadoProyecto,
    public target?: ITarget
  ) {}
}
