import { Link } from 'react-router-dom';

import { Badge } from '.';
import type { Task as ITask } from '../models/Task';
import { RequestResult } from '../store/TasksContext.types';

interface Props {
  task: ITask;
  onDelete: (id: number) => Promise<RequestResult>;
}

export const Task = ({ task }: Props) => {
  return (
    <Link
      to={`/edit/${task.id}`}
      className="flex flex-col max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700 mx-auto justify-between cursor-pointer min-w-full"
    >
      <div>
        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          {task.title}
        </h5>

        {task.description && (
          <p className="font-normal text-gray-700 dark:text-gray-400">
            {task.description}
          </p>
        )}
      </div>

      <div>
        <div className="flex justify-between mt-4">
          <Badge label={`Priority: ${task.priority.toString()}`} />
          <Badge label={`Due: ${task.deadline}`} />
        </div>

        <div className="flex justify-between items-center mt-4">
          <div className="flex gap-3 items-center">
            <label
              htmlFor={`task-${task.id}`}
              className="font-normal text-gray-700 dark:text-gray-400 flex-grow hover:cursor-pointer"
            >
              {'Done?'}
            </label>
            <input
              id={`task-${task.id}`}
              type="checkbox"
              value=""
              className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600 hover:cursor-pointer"
              defaultChecked={task.done}
              readOnly
              disabled
            />
          </div>
        </div>
      </div>
    </Link>
  );
};
