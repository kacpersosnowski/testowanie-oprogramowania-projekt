import TaskForm from '../components/TaskForm';
import { Task } from '../models/Task';

const ModifyTaskForm = () => {
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
    <div className="py-8 px-4 mx-auto max-w-2xl lg:py-16">
      <h2 className="mb-4 text-xl font-bold text-gray-900 dark:text-white">
        Edit task
      </h2>
      <TaskForm onSubmit={handleFormSubmit} initialTask={task} />
    </div>
  );
};

export default ModifyTaskForm;
