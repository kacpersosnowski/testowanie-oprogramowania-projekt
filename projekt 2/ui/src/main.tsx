import React from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import { Layout } from './components';
import './index.css';
import CreateTaskForm from './pages/CreateTaskForm.tsx';
import Home from './pages/Home.tsx';
import ModifyTaskForm from './pages/ModifyTaskForm.tsx';
import TaskDetails from './pages/TaskDeatils.tsx';
import { TasksContextProvider } from './store/TasksContext.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { path: '/', element: <Home /> },
      { path: ':id', element: <TaskDetails /> },
      { path: '/create', element: <CreateTaskForm /> },
      {
        path: '/edit/:id',
        element: <ModifyTaskForm />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <TasksContextProvider>
      <RouterProvider router={router} />
    </TasksContextProvider>
  </React.StrictMode>,
);
