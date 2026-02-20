import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pauta } from '../models/pauta.model';
import { Observable } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { Voto } from '../models/voto.model';
import { PautaResultado } from '../models/pauta-resultado.model';

@Injectable({ providedIn: 'root' })
export class PautaService {
  private readonly API = 'http://localhost:8080/api/v1/pautas';

  constructor(private http: HttpClient) {}

  criarPauta(pauta: any) {
    return this.http.post(this.API, pauta);
  }

  public abrirSessao(pautaId: number, segundos: number): Observable<any> {
    return this.http.post(`${this.API}/${pautaId}/sessao`, { segundos });
  }

  votar(pautaId: number, voto: string, cpf: string): Observable<any> {
    console.log(pautaId,voto,cpf)
    return this.http.post(`${this.API}/${pautaId}/votos`, {cpf: cpf, decisao: voto});
  }

  apurarPauta(id: number): Observable<any> {
    return this.http.put<any>(`${this.API}/${id}/resultado`, {});
  }

  buscarVotos(pautaId: number): Observable<Voto[]> {
    return this.http.get<Voto[]>(`${this.API}/${pautaId}/votos`);
  }

  listarPautas(): Observable<Pauta[]> {
  // Adicione <Pauta[]> aqui
    return this.http.get<Pauta[]>(this.API);
  }
}