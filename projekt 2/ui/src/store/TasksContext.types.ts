import { AxiosError } from 'axios';

import { Task } from '../models/Task';

export type TasksContextState = {
  tasks: Task[];
  getAllTasks: () => Promise<RequestResult>;
  getTasksSortedByDate: () => Promise<RequestResult>;
  getTasksSortedByPriority: () => Promise<RequestResult>;
  getCompletedTasks: () => Promise<RequestResult>;
  getUncompletedTasks: () => Promise<RequestResult>;
  getTaskDetails: (id: number) => Promise<RequestResult>;
  createTask: (taskData: Omit<Task, 'id'>) => Promise<RequestResult>;
  updateTask: (id: number, newTask: Task) => Promise<RequestResult>;
  partialUpdateTask: (
    id: number,
    taskData: Partial<Task>,
  ) => Promise<RequestResult>;
  getTaskByTitle: (titleData: Pick<Task, 'title'>) => Promise<RequestResult>;
  deleteTask: (id: number) => Promise<RequestResult>;
};

export type RequestResult = {
  isSuccess: boolean;
  error: AxiosError | null;
  task?: Task;
};

export enum TasksActionKind {
  SET_TASKS = 'SET_TASKS',
  ADD_TASK = 'ADD_TASK',
  UPDATE_TASK = 'UPDATE_TASK',
  DELETE_TASK = 'DELETE_TASK',
}

export type TasksAction = {
  type: TasksActionKind;
  payload: any;
};
