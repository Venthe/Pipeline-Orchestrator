import React from 'react'
import ReactDOM from 'react-dom/client'

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import { ApplicationContext } from './common/applicationContext';
import { RootLayout } from './RootLayout';
import './index.css'
import { Home } from './home/page';
// import * as projects from './projects/routing';
import * as catalog from './catalog/routing';

const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      { path: "/", element: <Home /> },
      catalog.routes
    ]
  }
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <ApplicationContext>
    <RouterProvider router={router} />
  </ApplicationContext>
)
