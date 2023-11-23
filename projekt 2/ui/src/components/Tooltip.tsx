interface Props {
  id: string;
  tooltipText: string;
  children: React.ReactNode;
}

const Tooltip = ({ id, tooltipText, children }: Props) => {
  return (
    <>
      <button
        data-tooltip-target={id}
        data-tooltip-placement="right"
        type="button"
        className="mb-2 md:mb-0 hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-700 dark:focus:ring-gray-700 "
      >
        {children}
      </button>

      <div
        id={id}
        role="tooltip"
        className="absolute z-10 invisible inline-block px-2 py-1 text-sm font-medium text-white bg-gray-900 rounded-lg shadow-sm opacity-0 tooltip dark:bg-gray-700"
      >
        {tooltipText}
      </div>
    </>
  );
};

export default Tooltip;
