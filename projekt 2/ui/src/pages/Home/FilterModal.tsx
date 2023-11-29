import { ChangeEvent, FormEvent } from 'react';

import { CloseIcon } from '../../assets';
import { TaskFilter } from '../../models/TaskFilter';

interface Props {
  id: string;
  visible: boolean;
  setVisible: (visible: boolean) => void;
  taskFilter: TaskFilter;
  setTaskFilter: (filter: TaskFilter) => void;
  taskPriorityFilter: number | null;
  setTaskPriorityFilter: (priority: number | null) => void;
  onSubmit: (event: FormEvent) => void;
}

const FilterModal = ({
  id,
  visible,
  setVisible,
  taskFilter,
  setTaskFilter,
  taskPriorityFilter,
  setTaskPriorityFilter,
  onSubmit,
}: Props) => {
  const handleFilterRadioChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTaskFilter(event.target.value as TaskFilter);
  };

  const handlePriorityFilterChange = (event: ChangeEvent<HTMLInputElement>) => {
    const priority = parseInt(event.target.value, 10);
    if (isNaN(priority)) {
      setTaskPriorityFilter(null);
      return;
    }

    setTaskPriorityFilter(priority);
  };

  return (
    <div
      id={id}
      tabIndex={-1}
      aria-hidden="true"
      className={`${
        visible ? 'flex' : 'hidden'
      } overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full`}
    >
      <div className="relative p-4 w-full max-w-2xl max-h-full">
        <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">
          <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
            <h3 className="text-xl font-semibold text-gray-900 dark:text-white">
              Filter tasks
            </h3>
            <button
              type="button"
              className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm dark:hover:bg-gray-600 dark:hover:text-white"
              onClick={() => setVisible(false)}
            >
              <CloseIcon />
            </button>
          </div>
          <form onSubmit={onSubmit}>
            <div className="p-4 md:p-5 space-y-4">
              {Object.values(TaskFilter).map((filter) => {
                return (
                  <div
                    key={`${filter}-tasks-filter`}
                    className="flex items-center mb-4"
                  >
                    <input
                      id={`${filter}-tasks-filter`}
                      type="radio"
                      value={filter}
                      name={`${filter}-tasks-filter`}
                      className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      checked={taskFilter === filter}
                      onChange={handleFilterRadioChange}
                    />
                    <label
                      htmlFor={`${filter}-tasks-filter`}
                      className="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300"
                    >
                      {filter}
                    </label>
                  </div>
                );
              })}
              {/* Do tego nie ma contextu
              <div>
                <label
                  htmlFor="number-input"
                  className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                  Filter by priority
                </label>
                <input
                  type="number"
                  id="number-input"
                  aria-describedby="helper-text-explanation"
                  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  placeholder="1"
                  value={taskPriorityFilter || ''}
                  onChange={handlePriorityFilterChange}
                />
              </div> */}
            </div>
            <div className="flex items-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
              <button
                type="submit"
                className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={() => setVisible(false)}
              >
                Filter
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default FilterModal;
