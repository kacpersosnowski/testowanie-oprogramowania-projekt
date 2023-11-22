import React from "react";
import {
  RequestResult,
  TasksAction,
  TasksActionKind,
  TasksContextState,
} from "./TasksContext.types";
import axios, { AxiosError } from "axios";
import { Task } from "../models/Task";

export const TasksContext = React.createContext<TasksContextState | null>(null);

const tasksReducer = (state: Task[], action: TasksAction) => {
  switch (action.type) {
    case TasksActionKind.SET_TASKS:
      return action.payload;
    case TasksActionKind.ADD_TASK:
      return [...state, action.payload];
    case TasksActionKind.UPDATE_TASK: {
      const oldTaskIndex = state.findIndex(
        (task) => task.id === action.payload.id
      );
      const updatedTasks = [...state];
      updatedTasks[oldTaskIndex] = {
        ...updatedTasks[oldTaskIndex],
        ...action.payload.newTask,
      };
      return updatedTasks;
    }
    case TasksActionKind.DELETE_TASK:
      return state.filter((task) => task.id !== action.payload);
    default:
      return state;
  }
};

export const TasksContextProvider: React.FC<React.PropsWithChildren> = ({
  children,
}) => {
  const [tasks, dispatch] = React.useReducer(tasksReducer, []);

  const handleError = (error: unknown): RequestResult => {
    if (axios.isAxiosError(error)) {
      return { isSuccess: false, error: error as AxiosError };
    } else {
      return {
        isSuccess: false,
        error: new AxiosError("Unknown error occurred"),
      };
    }
  };

  const fetchData = async (url: string) => {
    try {
      const response = await axios.get<Task[]>(url);
      dispatch({ type: TasksActionKind.SET_TASKS, payload: response.data });
      return { isSuccess: true, error: null };
    } catch (error) {
      return handleError(error);
    }
  };

  const getAllTasks = async () => {
    return fetchData("/tasks");
  };

  const getTasksSortedByDate = async () => {
    return fetchData("/tasks/filter/date");
  };

  const getTasksSortedByPriority = async () => {
    return fetchData("/tasks/filter/priority");
  };

  const getCompletedTasks = async () => {
    return fetchData("/tasks/completed");
  };

  const getUncompletedTasks = async () => {
    return fetchData("/tasks/uncompleted");
  };

  const getTaskDetails = async (id: number) => {
    try {
      const response = await axios.get<Task>(`/tasks/${id}`);
      return { isSuccess: true, error: null, task: response.data };
    } catch (error) {
      return handleError(error);
    }
  };

  const createTask = async (taskData: Omit<Task, "id">) => {
    try {
      const response = await axios.post<Task>("/tasks", taskData);
      dispatch({ type: TasksActionKind.ADD_TASK, payload: response.data });
      return { isSuccess: true, error: null };
    } catch (error) {
      return handleError(error);
    }
  };

  const updateTask = async (id: number, newTask: Task) => {
    try {
      const response = await axios.put<Task>(`/tasks/${id}`, newTask);
      dispatch({
        type: TasksActionKind.UPDATE_TASK,
        payload: { id, newTask: response.data },
      });
      return { isSuccess: true, error: null };
    } catch (error) {
      return handleError(error);
    }
  };

  const partialUpdateTask = async (id: number, taskData: Partial<Task>) => {
    try {
      const response = await axios.patch<Task>(`/tasks/${id}`, taskData);
      dispatch({
        type: TasksActionKind.UPDATE_TASK,
        payload: { id, newTask: response.data },
      });
      return { isSuccess: true, error: null };
    } catch (error) {
      return handleError(error);
    }
  };

  const getTaskByTitle = async (titleData: Pick<Task, "title">) => {
    try {
      const response = await axios.post<Task>("/tasks/get-by-title", titleData);
      return { isSuccess: true, error: null, task: response.data };
    } catch (error) {
      return handleError(error);
    }
  };

  const deleteTask = async (id: number) => {
    try {
      await axios.delete(`/tasks/${id}`);
      dispatch({ type: TasksActionKind.DELETE_TASK, payload: id });
      return { isSuccess: true, error: null };
    } catch (error) {
      return handleError(error);
    }
  };

  const value: TasksContextState = {
    tasks,
    getAllTasks,
    getTaskDetails,
    getTasksSortedByDate,
    getTasksSortedByPriority,
    getCompletedTasks,
    getUncompletedTasks,
    createTask,
    updateTask,
    partialUpdateTask,
    deleteTask,
    getTaskByTitle,
  };

  return (
    <TasksContext.Provider value={value}>{children}</TasksContext.Provider>
  );
};