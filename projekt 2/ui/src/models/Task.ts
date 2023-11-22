export type Task = {
  id: number;
  title: string;
  description: string | null | undefined;
  endDate: string;
  priority: number;
  isDone: boolean;
};
