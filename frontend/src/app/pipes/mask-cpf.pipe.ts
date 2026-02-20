import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'maskCpf',
  standalone: true
})
export class MaskCpfPipe implements PipeTransform {
  transform(value: string): string {
    if (!value || value.length !== 11) return value;

    return `***.${value.substring(3, 6)}.${value.substring(6, 9)}-**`;
  }
}