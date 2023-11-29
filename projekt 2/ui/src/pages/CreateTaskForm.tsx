import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';

import { TaskForm } from '../components';
import { Task } from '../models/Task';
import { TasksContext } from '../store/TasksContext';

export default function CreateTaskForm() {
  const navigate = useNavigate();
  const tasksContext = useContext(TasksContext);

  if (!tasksContext) {
    throw new Error('TasksContext is null');
  }

  const handleFormSubmit = async (task: Task | Omit<Task, 'id'>) => {
    const { createTask } = tasksContext;
    const { isSuccess } = await createTask(task);
    if (isSuccess) {
      navigate('/');
    } else {
      console.error('Error occurred while creating task');
    }
  };

  return (
    <div className="my-8 p-4 mx-auto max-w-2xl lg:my-16 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <h2 className="mb-4 text-xl font-bold ">Create task</h2>
      <TaskForm onSubmit={handleFormSubmit} />
    </div>
  );
}
