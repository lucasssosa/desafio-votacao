import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-sessao-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sessao-modal.component.html'
})
export class SessaoModalComponent {
  @Input() pauta: any;
  @Input() mensagemErro: string | null = null;
  @Output() fechar = new EventEmitter<void>();
  @Output() confirmar = new EventEmitter<number>();

  horas: number = 0;
  minutos: number = 1;

  validarTempo() {
    if (this.horas > 24) this.horas = 24;
    if (this.horas < 0) this.horas = 0;

    if (this.horas === 24) {
      this.minutos = 0;
    } else {
      if (this.minutos > 59) this.minutos = 59;
      if (this.minutos < 0) this.minutos = 0;

      if (this.horas === 0 && this.minutos === 0) {
        this.minutos = 1;
      }
    }
  }

  aoConfirmar() {
    const totalSegundos = (this.horas * 3600) + (this.minutos * 60);
    this.confirmar.emit(totalSegundos);
  }

  aoCancelar() {
    this.fechar.emit();
  }
}