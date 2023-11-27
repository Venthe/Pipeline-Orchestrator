import { createPlugin, createRoutableExtension } from '@backstage/core-plugin-api';

import { rootRouteRef } from './routes';

export const mantleCicdPlugin = createPlugin({
  id: 'mantle-cicd',
  routes: {
    root: rootRouteRef,
  },
});

export const MantleCicdPage = mantleCicdPlugin.provide(
  createRoutableExtension({
    name: 'MantleCicdPage',
    component: () =>
      import('./components/ExampleComponent').then(m => m.ExampleComponent),
    mountPoint: rootRouteRef,
  }),
);
