import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ToastService {
  private toastSubject = new BehaviorSubject<{msg: string, tipo: 'erro' | 'sucesso'} | null>(null);
  public toast$ = this.toastSubject.asObservable();
  private timeout: any;

  public exibir(mensagem: string, tipo: 'erro' | 'sucesso' = 'sucesso'): void {
    if (this.timeout) clearTimeout(this.timeout);
    
    this.toastSubject.next({ msg: mensagem, tipo });
    this.timeout = setTimeout(() => this.fechar(), 5000); // Aumentei para 5s já que é maior
  }

  public fechar(): void {
    this.toastSubject.next(null);
  }
}