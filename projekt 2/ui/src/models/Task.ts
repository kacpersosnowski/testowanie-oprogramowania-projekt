export type Task = {
  id: number;
  title: string;
  description: string | null | undefined;
  deadline: string;
  priority: number;
  done: boolean;
};
