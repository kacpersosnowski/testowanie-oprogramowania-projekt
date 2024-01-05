import { ChangeEvent, FormEvent } from 'react';

import { CloseIcon } from '../../assets';
import { TaskSort } from '../../models/TaskSort.ts';

interface Props {
  id: string;
  visible: boolean;
  setVisible: (visible: boolean) => void;
  taskSort: TaskSort;
  setTaskSort: (sort: TaskSort) => void;
  onSubmit: (event: FormEvent) => void;
}

export function SortModal({
  id,
  taskSort,
  visible,
  setVisible,
  setTaskSort,
  onSubmit,
}: Props) {
  const handleSortRadioChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTaskSort(event.target.value as TaskSort);
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
              Sort tasks
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
              {Object.values(TaskSort).map((sort) => {
                return (
                  <div
                    key={`${sort}-tasks-sort`}
                    className="flex items-center mb-4"
                  >
                    <input
                      id={`${sort}-tasks-sort`}
                      type="radio"
                      value={sort}
                      name={`${sort}-tasks-sort`}
                      className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                      checked={taskSort === sort}
                      onChange={handleSortRadioChange}
                    />
                    <label
                      htmlFor={`${sort}-tasks-sort`}
                      className="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300"
                    >
                      {sort}
                    </label>
                  </div>
                );
              })}
            </div>
            <div className="flex items-center p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
              <button
                id="sort-submit"
                type="submit"
                className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                onClick={() => setVisible(false)}
              >
                Sort
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
