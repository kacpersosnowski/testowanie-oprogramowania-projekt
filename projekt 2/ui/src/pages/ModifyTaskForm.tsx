import { TaskForm } from '../components';
import { Task } from '../models/Task';

export default function ModifyTaskForm() {
  const handleFormSubmit = (task: Task | Omit<Task, 'id'>) => {
    console.log(task);
  };

  const task = {
    id: 1,
    title: 'Lorem ipsum',
    description:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    endDate: '2021-06-30',
    isDone: false,
    priority: 2,
  };

  return (
    <div className="my-8 p-4 mx-auto max-w-2xl lg:my-16 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <h2 className="mb-4 text-xl font-bold ">Edit task</h2>
      <TaskForm onSubmit={handleFormSubmit} initialTask={task} />
    </div>
  );
}
