import { IContenido } from 'app/shared/model/contenido.model';

export interface IFormato {
  id?: number;
  titulo?: string;
  contenidos?: IContenido[];
}

export class Formato implements IFormato {
  constructor(public id?: number, public titulo?: string, public contenidos?: IContenido[]) {}
}
