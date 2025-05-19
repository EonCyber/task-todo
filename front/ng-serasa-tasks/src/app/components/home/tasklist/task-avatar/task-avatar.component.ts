
import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { UiModule } from '../../../../shared/ui.module';

@Component({
  selector: 'task-avatar',
  templateUrl: './task-avatar.component.html',
  imports: [CommonModule, UiModule],
  standalone: true,
})
export class TaskAvatarComponent {
  @Input() type: string = 'D';

  get avatarProps() {
    const map: Record<string, any> = {
      W: { // WORK
        icon: 'pi pi-briefcase',
        style: { backgroundColor: '#ece9fc', color: '#2a1261' }
      },
      E: { // Exercise
        icon: 'pi pi-heart',
        style: { backgroundColor: '#d1fae5', color: '#065f46' }
      },
      C: { // Chores
        icon: 'pi pi-home',
        style: { backgroundColor: '#fde9ec', color: '#571e26' }
      },
      D: { // Default
        icon: 'pi pi-question',
        style: {}
      }
    };
    return map[this.type] || map['default'];
  }
}
