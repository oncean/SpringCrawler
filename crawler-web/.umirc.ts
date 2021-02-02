import { defineConfig } from 'umi';
import routes from './config/routes';
import proxy from './config/proxy';
export default defineConfig({
  layout: {
    name: 'Ant Design',
    locale: false,
    layout: 'top',
  },
  locale: {
    default: 'zh-CN',
    antd: true,
    title: false,
    baseNavigator: true,
    baseSeparator: '-',
  },
  fastRefresh:{},
  history:{
    type:"browser"
  },
  proxy,
  routes,
});
