import { defineConfig } from 'umi';
import routes from './config/routes';
import proxy from './config/proxy';
export default defineConfig({
  layout: {
    name: 'Ant Design',
    locale: true,
    layout: 'top',
  },
  fastRefresh:{},
  history:{
    type:"browser"
  },
  proxy,
  routes,
});
