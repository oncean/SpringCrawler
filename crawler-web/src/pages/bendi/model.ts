import { AnyAction, Reducer } from 'redux';

import { EffectsCommandMap } from 'dva';
import { CardListItemDataType } from './data.d';
import { queryList } from './service';

export interface StateType {
  list: CardListItemDataType[];
}

export type Effect = (
  action: AnyAction,
  effects: EffectsCommandMap & { select: <T>(func: (state: StateType) => T) => T },
) => void;

export interface ModelType {
  namespace: string;
  state: StateType;
  effects: {
    fetch: Effect;
  };
  reducers: {
    queryList: Reducer<StateType>;
  };
}

const Model: ModelType = {
  namespace: 'listCardList2',

  state: {
    list: [],
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      let response = yield call(queryList)
      response = response.data
      response.forEach((element:any,index:any) => {
        element.id = index
      });
      yield put({
        type: 'queryList',
        payload: Array.isArray(response) ? response : [],
      });
    },
  },

  reducers: {
    queryList(state, action) {
      return {
        ...state,
        list: action.payload,
      };
    },
  },
};

export default Model;
