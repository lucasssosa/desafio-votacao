export interface Pauta {
  id?: number;
  titulo: string;
  descricao: string;
  status: string;
  votos?: any[];
  sessao?: {
    id: number;
    dataAbertura: string;
    dataFechamento: string;
    status: string;
  }
}