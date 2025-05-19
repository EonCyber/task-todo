import { Component, computed, effect, inject, OnInit, signal } from '@angular/core';
import { UiModule } from '../../../shared/ui.module';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../../services/task.service';
import { AddTask, Task } from '../../../models/task';
import { TaskCardComponent } from './task-card/task-card.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators  } from '@angular/forms';
import { CalendarService } from '../../../services/calendar.service';
import { formatDateToMonthDay, isItToday, meargeDateToTime } from '../../../shared/util/date/date-utils';
import { AuthService } from '../../../services/auth.service';
import { ToasterService } from '../../../services/toaster.service';
import { PaginatorState } from 'primeng/paginator';

@Component({
  selector: 'tasklist-component',
  imports: [CommonModule, UiModule,FormsModule,ReactiveFormsModule, TaskCardComponent],
  templateUrl: './tasklist.component.html',
  styleUrl: './tasklist.component.css',
  providers: [TaskService, ToasterService],
})
export class TasklistComponent implements OnInit {

  addFormVisible = false;

  displayText = signal<string>('');
  tasks = signal<Task[]>([]);
  // filter logic variables
  showFilterDialog = signal(false);
   keyword = signal('');
   tempKeyword: string = '';
   tempStatus = [];
   tempType = [];
   selectedStatuses = signal<string[]>([]);
   selectedTypes = signal<string[]>([]);
   filteredTasks = computed(() => {
    return this.tasks().filter(task => {
      const matchesKeyword = task.title.toLowerCase().includes(this.keyword().toLowerCase());
      const matchesStatus = this.selectedStatuses().length === 0 || this.selectedStatuses().includes(task.status);
      const matchesType = this.selectedTypes().length === 0 || this.selectedTypes().includes(task.type);
      return matchesKeyword && matchesStatus && matchesType;
    });
  });
  // data
  taskTypes: { desc: string, code: string }[] = [];
  taskStatus: { desc: string, code: string }[] = [];
  statusLabels: Record<string, string> = {
    PENDING: 'Pending',
    PROGRESS: 'In Progress',
    COMPLETED: 'Completed'
  };
  typeLabels: Record<string, string> = {
    C: 'Home/Chores',
    W: 'Work/Professional',
    E: 'Exercises/Health'
  };
  fb = inject(FormBuilder);

  first: number = 0;
  size: number = 5;
  totalRecords: number = 0;

  public form: FormGroup;

  constructor(
    private taskService: TaskService, 
    private calendarService: CalendarService, 
    private authService: AuthService, 
    private toasterService: ToasterService
  ) {
    effect(() => {
      console.log('fired')
      let date = this.calendarService.date();
      if (date) {
        this.dynamicDisplayText()
        this.fetchTaskListByDate(date);
      }
    });
    this.form = this.fb.group({
      title: ['', Validators.required],
      type: [null, Validators.required],
      status: [null, Validators.required],
      toCompleteAt: [null, Validators.required],
    });

    
  }
  ngOnInit(): void {
    this.taskTypes = [
      { desc: 'House / Chores', code: 'C'},
      { desc: 'Exercise / Health', code: 'E'},
      { desc: 'Work / Professional', code: 'W'},
    ]
    this.taskStatus = [
      { desc: 'Pending', code: 'PENDING'},
      { desc: 'Completed', code: 'COMPLETED'},
      { desc: 'In Progress', code: 'PROGRESS'},
    ]
  }

  fetchTaskListByDate(date: Date) {
    this.taskService.getTasksByDate(date, this.first, this.size).subscribe(response => {
      this.tasks.set(response.content);
      this.first = response.pageable.pageNumber;
      this.size = response.pageable.pageSize;
      this.totalRecords = response.totalElements;
    });
  }
  get tasksCompleted(): number {
    return this.tasks().filter(t => t.status === 'COMPLETED').length;
  }
  openAddTaskForm() {
    this.addFormVisible = !this.addFormVisible;
  }
  dynamicDisplayText() {
    if (isItToday(this.calendarService.dateSelected!)) this.displayText.set('for Today');
    else this.displayText.set(`on ${formatDateToMonthDay(this.calendarService.dateSelected!)}`)
    
  }
  getProfileName() {
    return this.authService.profileData?.fullName;
  }
  deleteTaskById(id: number) {
    this.taskService.deleteTask(id).subscribe(_ => {
      this.tasks.update(current => current.filter(task => task.id !== id));
      this.toasterService.warn(`Task Deleted.`)
    })
  }
  updateTaskById(event: { id: number; updatedFields: Partial<Task> }) {
    const { id, updatedFields} = event;
    this.taskService.updateTask(id, updatedFields).subscribe( _ => {
      this.toasterService.success(`Task Updated Success.`)
      this.tasks.update(current =>
        current.map(task =>
          task.id === id ? { ...task, ...updatedFields } : task
        )
      );
    })
    
  }
  onSubmit() { // for add task
    if (this.form.valid) {
      const newTask: AddTask = {
        status: this.form.value.status.code,
        type: this.form.value.type.code,
        title: this.form.value.title,
        toCompleteAt: meargeDateToTime(this.calendarService.dateSelected!, this.form.value.toCompleteAt)
      }
      console.log(newTask.toCompleteAt);
      this.taskService.addTask(newTask).subscribe(newTaskSaved => {
        if (isItToday(newTask.toCompleteAt)) {
          this.tasks.update(current => [...current, newTaskSaved]);
        }
      })
      this.form.reset();
      this.addFormVisible = !this.addFormVisible;
    } else {
      this.form.markAllAsTouched(); 
    }
  }
  onPageChange(e: PaginatorState) {
    this.first = e.first ?? 0;
    this.size = e.rows ?? 10;
    this.fetchTaskListByDate(this.calendarService.dateSelected!);
  }

  openFilter() {
    this.showFilterDialog.set(true);
  }

  applyFilter() {
    this.keyword.set(this.tempKeyword);
    this.updateStatusAndTypes();
    this.showFilterDialog.set(false);
  }
  updateStatusAndTypes() {
   
    this.selectedStatuses.set(this.tempStatus);
    this.selectedTypes.set(this.tempType);
     
  }

  resetFilter() {
    this.tempKeyword = ''
    this.tempStatus = [];
    this.tempType = [];
    this.keyword.set('');
    this.selectedStatuses.set([]);
    this.selectedTypes.set([]);
  }
}
