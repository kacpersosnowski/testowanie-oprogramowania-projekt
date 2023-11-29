import { useContext, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { TaskForm } from '../components';
import { Task } from '../models/Task';
import { TasksContext } from '../store/TasksContext';

export default function ModifyTaskForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const tasksContext = useContext(TasksContext);

  if (!tasksContext) {
    throw new Error('TasksContext is null');
  }

  const { tasks, getAllTasks, updateTask, deleteTask } = tasksContext;

  useEffect(() => {
    getAllTasks();
  }, []);

  const taskId = parseInt(id ?? '-1', 10);
  const task = tasks.find((task) => task.id === taskId);

  const handleFormSubmit = async (task: Task | Omit<Task, 'id'>) => {
    const { isSuccess } = await updateTask(taskId, { ...task, id: taskId });
    if (isSuccess) {
      navigate('/');
    } else {
      console.error('Error occurred while modifying task');
    }
  };

  const handleTaskDelete = async () => {
    const { isSuccess } = await deleteTask(taskId);
    if (isSuccess) {
      navigate('/');
    } else {
      console.error('Error occurred while deleting task');
    }
  };

  return (
    <div className="my-8 p-4 mx-auto max-w-2xl lg:my-16 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <h2 className="mb-4 text-xl font-bold ">Edit task</h2>
      <TaskForm
        onSubmit={handleFormSubmit}
        initialTask={task}
        onDelete={handleTaskDelete}
      />
    </div>
  );
}
