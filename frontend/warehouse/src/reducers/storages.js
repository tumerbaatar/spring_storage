import {SELECT_STORAGE, STORAGES_HAVE_FETCHED} from '../actions/index'

const storages = (state = { storageList: [], selectedItems: new Set() }, action) => {
  switch (action.type) {
    case STORAGES_HAVE_FETCHED:
      const storages = [...state.storageList, ...action.storages]
      return {
        ...state,
        storageList: storages,
        selectedStorageSlug: storages[0].slug
      }
    case SELECT_STORAGE:
      return {
        ...state,
        selectedStorageSlug: action.selectedStorageSlug,
      }
    default:
      return state
  }
}

export default storages