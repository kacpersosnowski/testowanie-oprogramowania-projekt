import { Link, Outlet } from 'react-router-dom';

import { Tooltip } from '.';
import { ListIcon, PlusIcon } from '../assets';

export const Layout = () => {
  return (
    <div className="antialiased flex min-w-max min-h-screen bg-gray-50 dark:bg-gray-900">
      <aside className="flex flex-col gap-3 p-3 bg-white border border-gray-200 shadow dark:bg-gray-800 dark:border-gray-700">
        <Link to="/">
          <Tooltip id="tooltip-list-tasks" tooltipText="List tasks">
            <ListIcon />
          </Tooltip>
        </Link>

        <Link to="/create">
          <Tooltip id="tooltip-create-task" tooltipText="Add a new task">
            <PlusIcon />
          </Tooltip>
        </Link>
      </aside>
      <main className="w-full">
        <Outlet />
      </main>
    </div>
  );
};
