import React from 'react'
import ReactDOM from 'react-dom/client'

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import { ApplicationContext } from './common/applicationContext';
import { Root } from './layout';
import './index.css'
import { Home } from './home/page';
import * as projects from './projects/routing';

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    children: [
      { path: "/", element: <Home /> },
      projects.routing
    ]
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <ApplicationContext>
    <RouterProvider router={router} />
  </ApplicationContext>
)
