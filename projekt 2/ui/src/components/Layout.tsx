import { Link, Outlet } from 'react-router-dom';

import ListIcon from '../assets/ListIcon';
import AddIcon from '../assets/PlusIcon';
import Tooltip from './Tooltip';

const Layout = () => {
  return (
    <div className="antialiased flex min-w-max min-h-screen bg-gray-50 dark:bg-gray-900">
      <aside className="flex flex-col gap-3 p-3 bg-white border border-gray-200 shadow dark:bg-gray-800 dark:border-gray-700">
        <Tooltip id="tooltip-list-tasks" tooltipText="List tasks">
          <Link to="/">
            <ListIcon />
          </Link>
        </Tooltip>
        <Tooltip id="tooltip-create-task" tooltipText="Add a new task">
          <Link to="/create">
            <AddIcon />
          </Link>
        </Tooltip>
      </aside>
      <main className="w-full">
        <Outlet />
      </main>
    </div>
  );
};

export default Layout;
