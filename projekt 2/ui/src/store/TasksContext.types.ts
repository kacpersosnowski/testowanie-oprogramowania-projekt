import { Task } from "../models/Task";

export type TasksContextState = {
  tasks: Task[];
  getAllTasks: () => Promise<boolean>;
  getTasksSortedByDate: () => Promise<boolean>;
  getTasksSortedByPriority: () => Promise<boolean>;
  getCompletedTasks: () => Promise<boolean>;
  getUncompletedTasks: () => Promise<boolean>;
  getTaskDetails: (id: number) => Promise<Task | null>;
  createTask: (taskData: Omit<Task, "id">) => Promise<boolean>;
  updateTask: (id: number, newTask: Task) => Promise<boolean>;
  partialUpdateTask: (id: number, taskData: Partial<Task>) => Promise<boolean>;
  getTaskByTitle: (titleData: Pick<Task, "title">) => Promise<Task | null>;
  deleteTask: (id: number) => Promise<boolean>;
};

export enum TasksActionKind {
  SET_TASKS = "SET_TASKS",
  ADD_TASK = "ADD_TASK",
  UPDATE_TASK = "UPDATE_TASK",
  DELETE_TASK = "DELETE_TASK",
}

export type TasksAction = {
  type: TasksActionKind;
  payload: any;
};
