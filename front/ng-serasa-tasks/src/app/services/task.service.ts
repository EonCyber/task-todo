import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { AddTask, Task, TaskPageableList } from '../models/task';
import { AuthService } from './auth.service';
import { toDateStringInGMT3 } from '../shared/util/date/date-utils';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private baseUrl = environment.apiUrl;
  private apiUrl =  `${this.baseUrl}/api/task`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  addTask(newTask: AddTask): Observable<Task> {
      const token = this.authService.authToken;
      if (token) {
        const headers = this.authService.genBearerAuthHeader(token);
        return this.http.post<any>(this.apiUrl, newTask,{ headers });
      }
      throw new Error('No Auth Token Found.');
  }

  getTasksByDate(date: Date, page: number, size: number): Observable<TaskPageableList> {
    const token = this.authService.authToken;
    if (token) {
      const dateString = toDateStringInGMT3(date);

      const headers = this.authService.genBearerAuthHeader(token);

      const params = new HttpParams()
        .set('date', dateString)
        .set('page', page.toString())
        .set('size', size.toString());
      return this.http.get<TaskPageableList>(this.apiUrl, { headers, params });
    }
    throw new Error('No Auth Token Found.');
    
  }

  deleteTask(id: number) {
    const token = this.authService.authToken;
    const headers = this.authService.genBearerAuthHeader(token!);
    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }

  updateTask(id: number, updateData: Partial<Task>) {
    const token = this.authService.authToken;
    const headers = this.authService.genBearerAuthHeader(token!);
    return this.http.put(`${this.apiUrl}/${id}`, updateData, { headers });
  }

}
