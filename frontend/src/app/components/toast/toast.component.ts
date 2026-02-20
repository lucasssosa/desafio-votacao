import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="toastService.toast$ | async as toast" 
        class="fixed top-8 left-8 z-[3000] animate-in slide-in-from-top-10 duration-300">
      
      <div [ngClass]="toast.tipo === 'erro' ? 'border-red-600' : 'border-emerald-500'"
          class="bg-white border-l-8 shadow-2xl rounded-xl p-6 min-w-[420px] flex items-center gap-6 border border-gray-100">
        
        <div [ngClass]="toast.tipo === 'erro' ? 'bg-red-50' : 'bg-emerald-50'" 
            class="h-14 w-14 rounded-full flex items-center justify-center shrink-0">
          
          <svg *ngIf="toast.tipo === 'erro'" class="h-8 w-8 text-red-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
          </svg>
          
          <svg *ngIf="toast.tipo === 'sucesso'" class="h-8 w-8 text-emerald-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
          </svg>
        </div>

        <div class="flex-1">
          <h3 class="text-xl font-black text-gray-900 uppercase tracking-tighter leading-tight">
            {{ toast.tipo === 'erro' ? 'Atenção' : 'Sucesso!' }}
          </h3>
          <p class="text-gray-600 mt-1 font-semibold text-base">
            {{ toast.msg }}
          </p>
        </div>

        <button (click)="toastService.fechar()" class="text-gray-400 hover:text-gray-600 transition-colors p-1">
          <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>
    </div>
  `
})
export class ToastComponent {
  constructor(public toastService: ToastService) {}
}