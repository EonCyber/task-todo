import { Component, EventEmitter, Input, Output } from "@angular/core";
import { UiModule } from "../../../../shared/ui.module";
import { CommonModule } from "@angular/common";
import { TaskAvatarComponent } from "../task-avatar/task-avatar.component";
import { Task } from "../../../../models/task";
import { ConfirmationService } from "primeng/api";

@Component({
    selector: 'task-card',
    templateUrl: './task-card.component.html',
    imports: [CommonModule, UiModule, TaskAvatarComponent],
    providers: [ConfirmationService],
    standalone: true,
})
export class TaskCardComponent {
    @Input() task!: Task;

    @Output() delete = new EventEmitter<number>();
    @Output() update = new EventEmitter<{ id: number; updatedFields: Partial<Task> }>();
    visible = false;
    loading = false;
    constructor(private confirmationService: ConfirmationService) {

    }
    // DATA DISPLAY ---------------------------------
    get displayTime(): string {
        const date =
          this.task.status === 'COMPLETED'
            ? new Date(this.task.completedAt!)
            : new Date(this.task.toCompleteAt);
    
            if (!date) return '';

            const hours = date.getHours() % 12 || 12;
            const minutes = date.getMinutes().toString().padStart(2, '0');
            const period = date.getHours() >= 12 ? 'pm' : 'am';
          
            return `${hours}h${minutes} ${period}`;
      }
    
    get labelPrefix(): string {
        return this.task.status === 'COMPLETED' ? 'Completed' : 'Due';
    }

    get statusData(): any {
        const statusSeverityMap: Record<string, any> = {
            PROGRESS:{ severity: 'warn', text: 'In Progress'},
            PENDING: { severity: 'info', text: 'Pending'},
            COMPLETED: { severity: 'success', text: 'Completed'},
          };
          
        return statusSeverityMap[this.task.status || 'PENDING'];
    }
    // OPTIONS ---------------------------------
    showEdit() {
        this.visible = true;
    }

    updateStatus(newStatus: "PENDING" | "PROGRESS" | "COMPLETED" | undefined) {
        this.loading = true;
        this.update.emit({ id: this.task.id, updatedFields:{ status: newStatus} })
        setTimeout(()=>{
            this.loading = false;
            this.visible = false;
        }
        , 2000);
    }

    onEditTask() {
        
    }

    onDeleteTask() {
        this.delete.emit(this.task.id);
    }

    //  CONFIRMATION DELETE DIALOG ---------------------------------
    checkDeletion(event: Event){
        this.confirmationService.confirm({
            target: event.target as EventTarget,
            message: 'Do you really want to?',
            header: 'You are deleting this Task',
            icon: 'pi pi-info-circle',
            rejectLabel: 'Cancel',
            rejectButtonProps: {
                label: 'Cancel',
                severity: 'secondary',
                outlined: true,
            },
            acceptButtonProps: {
                label: 'Delete',
                severity: 'danger',
            },

            accept: () => {
                this.visible = false;
                this.onDeleteTask();
            },
            reject: () => {
                this.visible = false;
            },
        });
    }
}