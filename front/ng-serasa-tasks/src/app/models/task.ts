export interface Task {
  id: number;
  title: string;
  type: 'W' | 'E' | 'C' | 'D';
  status: 'PENDING' | 'PROGRESS' | 'COMPLETED';
  createdAt: Date;
  toCompleteAt: Date;
  completedAt: Date | undefined;
}

export interface AddTask {
  title: string;
  type: 'W' | 'E' | 'C' | 'D';
  status: 'PENDING' | 'PROGRESS' | 'COMPLETED';
  toCompleteAt: Date;
}
export interface TaskPageableList {
  content: Task[];
  empty: boolean;
  pageable:{ 
    pageNumber: number,
    pageSize: number,
    paged: boolean,
  }
  totalElements: number;
  totalPages: number;

}