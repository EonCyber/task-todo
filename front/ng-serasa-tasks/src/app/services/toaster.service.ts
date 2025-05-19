import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
@Injectable({
  providedIn: 'root'
})
export class ToasterService {

  constructor(private messageService: MessageService) { }

  error(message: string) {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: message });
  }

  warn(message: string) {
    this.messageService.add({ severity: 'warn', summary: 'Attention', detail: message });
  }

  info(message: string) {
    this.messageService.add({ severity: 'info', summary: 'Info', detail: message });
  }
  success(message: string) {
    this.messageService.add({ severity: 'success', summary: 'Success', detail: message });
  }
}
