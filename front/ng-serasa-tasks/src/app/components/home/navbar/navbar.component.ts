import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { UiModule } from '../../../shared/ui.module';
import { MenuItem } from 'primeng/api';
import { AuthService } from '../../../services/auth.service';
import { CalendarService } from '../../../services/calendar.service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, UiModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
 
  public items: MenuItem[] | undefined = [];
  public avatarMenuItems: MenuItem[] | undefined = [];
  avatarUrl = signal('');
  constructor(
    private authService: AuthService, 
    private calendarService: CalendarService) {
      
    }
  ngOnInit(): void {
    this.avatarMenuItems = [
      {
          label: 'Profile',
          items: [
              {
                  label: 'Settings',
                  icon: 'pi pi-cog'
              },
              {
                  label: 'Logout',
                  icon: 'pi pi-sign-out',
                  command: () => this.onLogout(),
              }
          ]
      }
  ];
    this.avatarUrl.set(this.authService.profileData?.avatarUrl!);
  }

  onDatePicked(date: Date) {
    this.calendarService.setDateSelected(date);
  }

  onLogout() {
    this.authService.handleLogout();
  }
}
