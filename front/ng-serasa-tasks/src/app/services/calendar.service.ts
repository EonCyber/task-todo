import { Injectable, signal, Signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  date = signal<Date | undefined>(undefined)
  constructor() { 
    this.date.set(new Date())
  }

  setDateSelected(dateSeleceted: Date) {    
    this.date.set(dateSeleceted);
  }

  get dateSelected() {
    return this.date();
  }
}
