import { FormEvent, useContext, useEffect, useState } from 'react';

import { DropDownIcon } from '../../assets';
import { Task } from '../../components';
import { TaskFilter } from '../../models/TaskFilter';
import { TaskSort } from '../../models/TaskSort';
import { TasksContext } from '../../store/TasksContext';
import FilterModal from './FilterModal.tsx';
import { SearchInput } from './SearchInput.tsx';
import { SortModal } from './SortModal.tsx';

const FILTER_MODAL_ID = 'filter-modal';
const SORT_MODAL_ID = 'sort-modal';

export default function Home() {
  const tasksContext = useContext(TasksContext);

  const [filterModalVisible, setFilterModalVisible] = useState<boolean>(false);
  const [taskFilter, setTaskFilter] = useState<TaskFilter>(TaskFilter.all);
  const [taskPriorityFilter, setTaskPriorityFilter] = useState<number | null>(
    null,
  );

  const [sortModalVisible, setSortModalVisible] = useState<boolean>(false);
  const [taskSort, setTaskSort] = useState<TaskSort>(TaskSort.default);

  if (!tasksContext) {
    throw new Error('TasksContext is null');
  }

  const {
    tasks,
    getAllTasks,
    getTasksSortedByDate,
    getTasksFilteredByPriority,
    getTasksFilteredByTitle,
    deleteTask,
    getCompletedTasks,
    getUncompletedTasks,
  } = tasksContext;

  useEffect(() => {
    getAllTasks();
  }, []);

  const resetFilters = () => {
    setTaskFilter(TaskFilter.all);
    setTaskPriorityFilter(null);
  };

  const handleFilterChange = (event: FormEvent) => {
    event.preventDefault();

    if (taskPriorityFilter) {
      console.log(taskPriorityFilter);
      getTasksFilteredByPriority(taskPriorityFilter);
      return;
    }

    switch (taskFilter) {
      case TaskFilter.all:
        getAllTasks();
        break;
      case TaskFilter.done:
        getCompletedTasks();
        break;
      case TaskFilter.undone:
        getUncompletedTasks();
        break;
      default:
        break;
    }
  };

  const handleSortChange = (event: FormEvent) => {
    event.preventDefault();
    resetFilters();

    switch (taskSort) {
      case TaskSort.default:
        getAllTasks();
        break;
      case TaskSort.date:
        getTasksSortedByDate();
        break;
      default:
        break;
    }
  };

  const handleSearch = (searchTerm: string) => {
    getTasksFilteredByTitle(searchTerm);
  };

  return (
    <>
      <div className="text-sm font-medium text-center bg-white border border-gray-200 shadow dark:bg-gray-800 dark:border-gray-700 flex justify-between">
        <SearchInput onSearch={handleSearch} />
        <ul className="flex flex-wrap -mb-px">
          <li>
            <a
              className="flex p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300 cursor-pointer items-center"
              onClick={() => setSortModalVisible(true)}
            >
              Sort
              <DropDownIcon />
            </a>
          </li>
          <li>
            <a
              className="flex p-4 border-b-2 border-transparent rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300 cursor-pointer items-center"
              onClick={() => setFilterModalVisible(true)}
            >
              Filter
              <DropDownIcon />
            </a>
          </li>
        </ul>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4 gap-x-32 gap-y-8 m-3">
        {tasks &&
          tasks.map((task) => {
            return <Task key={task.id} task={task} onDelete={deleteTask} />;
          })}
      </div>

      <FilterModal
        id={FILTER_MODAL_ID}
        visible={filterModalVisible}
        setVisible={setFilterModalVisible}
        taskFilter={taskFilter}
        setTaskFilter={setTaskFilter}
        taskPriorityFilter={taskPriorityFilter}
        setTaskPriorityFilter={setTaskPriorityFilter}
        onSubmit={handleFilterChange}
      />

      <SortModal
        id={SORT_MODAL_ID}
        visible={sortModalVisible}
        setVisible={setSortModalVisible}
        taskSort={taskSort}
        setTaskSort={setTaskSort}
        onSubmit={handleSortChange}
      />
    </>
  );
}
