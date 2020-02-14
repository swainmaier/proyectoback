import { IProyecto } from 'app/shared/model/proyecto.model';

export interface IEstadoProyecto {
  id?: number;
  titulo?: string;
  proyectos?: IProyecto[];
}

export class EstadoProyecto implements IEstadoProyecto {
  constructor(public id?: number, public titulo?: string, public proyectos?: IProyecto[]) {}
}
