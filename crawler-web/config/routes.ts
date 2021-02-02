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
      },
      {
        path: 'scanTask',
        name: 'TangScanTask',
        component: '@/pages/tang/scan/task',
        hideInMenu: true,
      },
      {
        path: 'scanResult',
        name: 'scanResult',
        component: '@/pages/tang/scan/result',
        hideInMenu: true,
      }
    ],
  },
  {
    path: 'bendi',
    name: 'bendi',
    component: '@/pages/bendi'
  },
  {
    path: 'picManage',
    name: 'picManage',
    component: '@/pages/picManage'
  }
];
export default routes;