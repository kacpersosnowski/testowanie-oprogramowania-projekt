import { useState } from 'react';

import SearchIcon from '../assets/SearchIcon';
import { Task } from '../components';

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

  const resetFilters = () => {
    setFilter(TaskStateFilter.all);
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
        <form className="flex items-center justify-center grow mx-10">
          <label
            htmlFor="default-search"
            className="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white"
          >
            Search
          </label>
          <div className="relative w-full max-w-xl">
            <div className="absolute inset-y-0 start-0 bottom-0 flex items-center ps-3 pointer-events-none">
              <SearchIcon />
            </div>
            <input
              type="search"
              id="default-search"
              className="block w-full p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="Search Tasks"
            />
            <button
              type="submit"
              className="text-white absolute end-1 bottom-1 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-1 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              Search
            </button>
          </div>
        </form>
        <ul className="flex flex-wrap -mb-px">
          <li className="me-2">
            <a
              href="#"
              className="inline-block p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300"
              onClick={() => resetFilters()}
            >
              Sort by priority
            </a>
          </li>
          <li className="me-2">
            <a
              href="#"
              className="inline-block p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300"
              onClick={() => resetFilters()}
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
