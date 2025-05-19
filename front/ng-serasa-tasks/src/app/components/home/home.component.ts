import { Component } from '@angular/core';
import { UiModule } from '../../shared/ui.module';
import { CommonModule } from '@angular/common';
import { TasklistComponent } from './tasklist/tasklist.component';
import { NavbarComponent } from './navbar/navbar.component';

@Component({
  selector: 'app-home',
  imports: [CommonModule,UiModule, NavbarComponent, TasklistComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
