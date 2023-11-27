import { useContext, useEffect, useState } from 'react';

import SearchIcon from '../assets/SearchIcon';
import { Task } from '../components';
import { TasksContext } from '../store/TasksContext';

enum TaskStateFilter {
  all = 'all',
  done = 'done',
  undone = 'undone',
}

enum TaskSortFilter {
  priority = 'priority',
  date = 'date',
}

export default function Home() {
  const [filter, setFilter] = useState<TaskStateFilter>(TaskStateFilter.all);
  const tasksContext = useContext(TasksContext);

  if (!tasksContext) {
    throw new Error('TasksContext is null');
  }

  const {
    tasks,
    getAllTasks,
    getCompletedTasks,
    getUncompletedTasks,
    getTasksSortedByPriority,
    getTasksSortedByDate,
    deleteTask,
  } = tasksContext;

  useEffect(() => {
    getAllTasks();
  }, []);

  const handleFilterChange = (filter: TaskStateFilter) => {
    setFilter(filter);

    switch (filter) {
      case TaskStateFilter.all:
        getAllTasks();
        break;
      case TaskStateFilter.done:
        getCompletedTasks();
        break;
      case TaskStateFilter.undone:
        getUncompletedTasks();
        break;
      default:
        break;
    }
  };

  const resetFilters = () => {
    setFilter(TaskStateFilter.all);
  };

  const handleSortChange = (filter: TaskSortFilter) => {
    resetFilters();

    switch (filter) {
      case TaskSortFilter.priority:
        getTasksSortedByPriority();
        break;
      case TaskSortFilter.date:
        getTasksSortedByDate();
        break;
      default:
        break;
    }
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
              onClick={() => handleSortChange(TaskSortFilter.priority)}
            >
              Sort by priority
            </a>
          </li>
          <li className="me-2">
            <a
              href="#"
              className="inline-block p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300"
              onClick={() => handleSortChange(TaskSortFilter.date)}
            >
              Sort by date
            </a>
          </li>
        </ul>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4 gap-x-32 gap-y-8 m-3">
        {tasks.map((task) => {
          return <Task key={task.id} task={task} onDelete={deleteTask} />;
        })}
      </div>
    </>
  );
}
