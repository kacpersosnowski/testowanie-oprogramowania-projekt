import { TaskForm } from '../components';
import { Task } from '../models/Task';

export default function CreateTaskForm() {
  const handleFormSubmit = (task: Task | Omit<Task, 'id'>) => {
    console.log(task);
  };

  return (
    <div className="my-8 p-4 mx-auto max-w-2xl lg:my-16 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <h2 className="mb-4 text-xl font-bold ">Create task</h2>
      <TaskForm onSubmit={handleFormSubmit} />
    </div>
  );
}
