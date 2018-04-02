import {
    PART_HAS_FETCHED,
    PARTS_HAVE_FETCHED,
    TOGGLE_PART_CHECKBOX,
    PART_SEARCH_RESULTS_FETCHED,
    MODE_PARTS_ARE_SEARCHING,
    MODE_PARTS_ARE_NOT_SEARCHING
} from '../actions/index'
import { updateInList } from './index'

const parts = (state = {
    partList: [],
    selectedItems: new Set(),
},
    action) => {
    switch (action.type) {
        case PARTS_HAVE_FETCHED:
            return {
                ...state,
                partList: action.parts
            }
        case PART_HAS_FETCHED:
            const part = action.part
            return {
                ...state,
                partList: updateInList(state.partList, part)
            }
        case PART_SEARCH_RESULTS_FETCHED:
            return {
                ...state,
                partSearchResults: action.partSearchResults
            }
        case MODE_PARTS_ARE_SEARCHING:
            return {
                ...state,
                isSearching: true
            }
        case MODE_PARTS_ARE_NOT_SEARCHING:
            return {
                ...state,
                isSearching: false
            }
        case TOGGLE_PART_CHECKBOX:
            const selectedItems = state.selectedItems
            const itemId = action.partId

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
            return state;
    }
}

export default parts;