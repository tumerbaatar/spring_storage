import {BOX_HAS_FETCHED, BOXES_HAVE_FETCHED, BOXES_WIPE, TOGGLE_BOX_CHECKBOX} from '../actions/index'
import {updateInList} from './index'

const boxes = (state = { boxList: [], selectedItems: new Set() }, action) => {
  switch (action.type) {
    case BOXES_WIPE:
      return {
        ...state,
        boxList: []
      }
    case BOXES_HAVE_FETCHED:
      return {
        ...state,
        boxList: [...state.boxList, ...action.boxes]
      }
    case BOX_HAS_FETCHED:
      const box = action.box
      return {
        ...state,
        boxList: updateInList(state.boxList, box)
      }
    case TOGGLE_BOX_CHECKBOX:
      const selectedItems = state.selectedItems
      const itemId = action.boxId

      if (selectedItems.has(itemId)) {
        selectedItems.delete(itemId)
      } else {
        selectedItems.add(itemId)
      }

      return {
        ...state,
        selectedItems: selectedItems
      }

    default:
      return state
  }
}

export default boxes