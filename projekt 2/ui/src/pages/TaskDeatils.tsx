import { Link } from 'react-router-dom';

import { EditIcon, TrashIcon } from '../assets';

export default function TaskDetails() {
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
      <h2 className="mb-2 text-xl font-semibold leading-none text-gray-900 md:text-2xl dark:text-white">
        {task.title}
      </h2>
      <dl>
        <dt className="mb-2 font-semibold leading-none text-gray-900 dark:text-white">
          Description
        </dt>
        <dd className="mb-4 font-light text-gray-500 sm:mb-5 dark:text-gray-400">
          {task.description}
        </dd>
      </dl>
      <dl className="flex items-center space-x-6">
        <div>
          <dt className="mb-2 font-semibold leading-none text-gray-900 dark:text-white">
            Due date:
          </dt>
          <dd className="mb-4 font-light text-gray-500 sm:mb-5 dark:text-gray-400">
            {task.endDate}
          </dd>
        </div>
        <div>
          <dt className="mb-2 font-semibold leading-none text-gray-900 dark:text-white">
            Priority:
          </dt>
          <dd className="mb-4 font-light text-gray-500 sm:mb-5 dark:text-gray-400">
            {task.priority}
          </dd>
        </div>
      </dl>
      <div className="flex items-center space-x-4">
        <Link
          to={`/edit/${task.id}`}
          className="inline-flex items-center text-white bg-gray-800 hover:bg-gray-900 focus:outline-none focus:ring-4 focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-gray-800 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700"
        >
          <EditIcon />
          Edit
        </Link>
        <Link
          to="/"
          className="inline-flex items-center text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 dark:bg-red-500 dark:hover:bg-red-600 dark:focus:ring-red-900"
        >
          <TrashIcon />
          Delete
        </Link>
      </div>
    </div>
  );
}
