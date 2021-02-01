import { IConfigFromPlugins } from "@/.umi/core/pluginConfig";

const routes: IConfigFromPlugins["routes"] = [
  {
    path: '/',
    component: '@/pages/index',
    name: '欢迎',
  },
  {
    path: 'tang',
    name: 'Tang',
    routes: [
      { path: '/', redirect: 'scan' },
      {
        path: 'scan',
        name: 'TangScan',
        component: '@/pages/tang/scan'
      }
    ],
  }
];
export default routes;