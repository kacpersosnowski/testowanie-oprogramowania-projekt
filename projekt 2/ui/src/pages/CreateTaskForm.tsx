import TaskForm from '../components/TaskForm';
import { Task } from '../models/Task';

export const CreateTaskForm = () => {
  const handleFormSubmit = (task: Task | Omit<Task, 'id'>) => {
    console.log(task);
  };

  return (
    <div className="py-8 px-4 mx-auto max-w-2xl lg:py-16">
      <h2 className="mb-4 text-xl font-bold text-gray-900 dark:text-white">
        Create task
      </h2>
      <TaskForm onSubmit={handleFormSubmit} />
    </div>
  );
};

export default CreateTaskForm;
