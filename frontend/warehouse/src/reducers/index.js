import {combineReducers} from 'redux'
import storages from './storages'
import parts from './parts'
import boxes from './boxes'

import {MODE_ADD_NEW_BOX, MODE_ADD_NEW_PART, MODE_NEW_BOXES_HAVE_ADDED, MODE_WATCH_PART,} from '../actions/index'

const defaultMode = {
    name: 'DEFAULT_APPLICATION_MODE'
}

const applicationMode = (state = {...defaultMode}, action) => {
    switch (action.type) {
        case MODE_ADD_NEW_BOX:
        case MODE_WATCH_PART:
        case MODE_ADD_NEW_PART:
        case MODE_NEW_BOXES_HAVE_ADDED:
            return {
                ...state,
                name: action.type,
                modePayload: action.modePayload
            }
        default:
            return state
    }
}

export const updateInList = (list, item) => {
    console.log("************updateInList*********************")
    console.log(list)
    console.log(item)
    console.log("************updateInList*********************")
    const presentItemIndex = list.findIndex(i => i.id === item.id)
    if (presentItemIndex >= 0) {
        list[presentItemIndex] = item
        return [...list]
    } else {
        return [item, ...list]
    }
}

const partsApp = combineReducers({
    applicationMode,
    storages,
    parts,
    boxes,
});

export default partsApp;