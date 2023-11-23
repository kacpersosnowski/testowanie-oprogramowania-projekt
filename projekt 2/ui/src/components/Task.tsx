import { Link } from 'react-router-dom';

import { Badge } from '.';
import { EditIcon, TrashIcon } from '../assets';
import type { Task as ITask } from '../models/Task';

interface Props {
  task: ITask;
}

export const Task = ({ task }: Props) => {
  return (
    <div className="flex flex-col max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 mx-auto justify-between">
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
          <Badge label={`Due: ${task.endDate}`} />
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
            />
          </div>

          <div className="flex gap-3">
            <Link
              to={`/edit/${task.id}`}
              className="focus:outline-none text-white bg-gray-800 hover:bg-gray-900 focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700"
            >
              <EditIcon />
            </Link>
            <button
              type="button"
              className="focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-3 py-2 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-900"
            >
              <TrashIcon />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
