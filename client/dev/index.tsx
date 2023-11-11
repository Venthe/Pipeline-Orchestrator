import React from 'react';
import { createDevApp } from '@backstage/dev-utils';
import { mantleCicdPlugin, MantleCicdPage } from '../src/plugin';

createDevApp()
  .registerPlugin(mantleCicdPlugin)
  .addPage({
    element: <MantleCicdPage />,
    title: 'Root Page',
    path: '/mantle-cicd'
  })
  .render();
