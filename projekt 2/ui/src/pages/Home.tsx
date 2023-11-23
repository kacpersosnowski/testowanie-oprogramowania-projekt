import { useEffect, useState } from 'react';

import Task from '../components/Task.tsx';

enum TaskStateFilter {
  all = 'all',
  done = 'done',
  undone = 'undone',
}

export default function App() {
  const [filter, setFilter] = useState<TaskStateFilter>(TaskStateFilter.all);

  const handleFilterChange = (filter: TaskStateFilter) => {
    setFilter(filter);
  };

  useEffect(() => {
    console.log(filter);
  }, [filter]);

  const task = {
    id: 1,
    title: 'Lorem ipsum',
    description:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
    endDate: '2021-06-30',
    isDone: false,
    priority: 2,
  };
  const task2 = {
    id: 2,
    title: 'Lorem ipsum Lorem ipsum',
    description:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
    endDate: '2021-06-30',
    isDone: false,
    priority: 2,
  };
  return (
    <>
      <div className="text-sm font-medium text-center bg-white border border-gray-200 shadow dark:bg-gray-800 dark:border-gray-700 flex justify-between">
        <ul className="flex flex-wrap -mb-px">
          <li className="me-2">
            <a
              href="#"
              className={`inline-block p-4 border-b-2 border-transparent rounded-t-lg ${
                filter === TaskStateFilter.all
                  ? 'text-blue-600 border-b-2 border-blue-600 rounded-t-lg active dark:text-blue-500 dark:border-blue-500'
                  : 'hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'
              }`}
              onClick={() => handleFilterChange(TaskStateFilter.all)}
            >
              All
            </a>
          </li>
          <li className="me-2">
            <a
              href="#"
              className={`inline-block p-4 border-b-2 border-transparent rounded-t-lg ${
                filter === TaskStateFilter.done
                  ? 'text-blue-600 border-b-2 border-blue-600 rounded-t-lg active dark:text-blue-500 dark:border-blue-500'
                  : 'hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'
              }`}
              onClick={() => handleFilterChange(TaskStateFilter.done)}
            >
              Done
            </a>
          </li>
          <li className="me-2">
            <a
              href="#"
              className={`inline-block p-4 border-b-2 border-transparent rounded-t-lg ${
                filter === TaskStateFilter.undone
                  ? 'text-blue-600 border-b-2 border-blue-600 rounded-t-lg active dark:text-blue-500 dark:border-blue-500'
                  : 'hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'
              }`}
              onClick={() => handleFilterChange(TaskStateFilter.undone)}
            >
              Undone
            </a>
          </li>
        </ul>
        <ul className="flex flex-wrap -mb-px">
          <li className="me-2">
            <a
              href="#"
              className="inline-block p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300"
              onClick={() => handleFilterChange(TaskStateFilter.all)}
            >
              Sort by priority
            </a>
          </li>
          <li className="me-2">
            <a
              href="#"
              className="inline-block p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300"
              onClick={() => handleFilterChange(TaskStateFilter.all)}
            >
              Sort by date
            </a>
          </li>
        </ul>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4 gap-4 m-3">
        <Task task={task} />
        <Task task={task2} />
        <Task task={task} />
        <Task task={task} />
        <Task task={task} />
        <Task task={task} />
      </div>
    </>
  );
}
