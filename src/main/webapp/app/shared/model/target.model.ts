import { IProyecto } from 'app/shared/model/proyecto.model';

export interface ITarget {
  id?: number;
  titulo?: string;
  proyectos?: IProyecto[];
}

export class Target implements ITarget {
  constructor(public id?: number, public titulo?: string, public proyectos?: IProyecto[]) {}
}
