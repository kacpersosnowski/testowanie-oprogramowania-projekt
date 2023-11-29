import { ChangeEvent, FormEvent, useState } from 'react';

import { SearchIcon } from '../../assets';

interface Props {
  onSearch: (searchTerm: string) => void;
}

export function SearchInput({ onSearch }: Props) {
  const [searchTerm, setSearchTerm] = useState<string>('');

  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = (event: FormEvent) => {
    event.preventDefault();
    console.log(searchTerm);

    onSearch(searchTerm);
    setSearchTerm('');
  };

  return (
    <form
      onSubmit={handleSearchSubmit}
      className="flex items-center justify-center w-1/2 ml-3"
    >
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
          value={searchTerm}
          onChange={handleSearchChange}
        />
        <button
          type="submit"
          className="text-white absolute end-1 bottom-1 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-1 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          Search
        </button>
      </div>
    </form>
  );
}
