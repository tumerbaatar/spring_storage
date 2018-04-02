export const SELECT_STORAGE = 'SELECT_STORAGE'
export function selectStorage(selectedSlug) {
    return {
        type: SELECT_STORAGE,
        selectedStorageSlug: selectedSlug
    }
}

// fetchParts
export const PARTS_HAVE_FETCHED = 'PARTS_HAVE_FETCHED'
export function partsHaveFetched(parts) {
    return {
        type: PARTS_HAVE_FETCHED,
        parts: parts
    }
}

export const PARTS_ARE_FETCHING = 'PARTS_ARE_FETCHING'
export function partsAreFetching() {
    return {
        type: PARTS_ARE_FETCHING,
    }
}

export const PARTS_FETCH_ERROR = 'PARTS_FETCH_ERROR'
export function partsFetchError(error) {
    return {
        type: PARTS_FETCH_ERROR,
        error: error
    }
}


// fetchPartByHash
export const PART_HAS_FETCHED = 'PART_HAS_FETCHED'
export function partHasFetched(part) {
    return {
        type: PART_HAS_FETCHED,
        part: part
    }
}

export const PART_IS_FETCHING = 'PART_IS_FETCHING'
export function partIsFetching() {
    return {
        type: PART_IS_FETCHING,
    }
}

export const PART_FETCH_ERROR = 'PART_FETCH_ERROR'
export function partFetchError(error) {
    return {
        type: PART_FETCH_ERROR,
        error: PART_FETCH_ERROR
    }
}

export const STORAGES_HAVE_FETCHED = 'STORAGES_HAVE_FETCHED'
export function storagesHaveFetched(storages) {
    return {
        type: STORAGES_HAVE_FETCHED,
        storages: storages
    }
}

// fetchBoxByHash
export const BOX_HAS_FETCHED = 'BOX_HAS_FETCHED'
export function boxHasFetched(box) {
    return {
        type: BOX_HAS_FETCHED,
        box: box
    }
}
export const BOX_IS_FETCHING = 'BOX_IS_FETCHING'
export const BOX_FETCH_ERROR = 'BOX_FETCH_ERROR'

// fetchBoxes and postBoxes callback the same
export const BOXES_HAVE_FETCHED = 'BOXES_HAVE_FETCHED'
export function boxesHaveFetched(boxes) {
    return {
        type: BOXES_HAVE_FETCHED,
        boxes: boxes
    }
}

export const BOXES_ARE_FETCHING = 'BOXES_ARE_FETCHING'
export function boxesAreFetching() {
    return {
        type: BOXES_ARE_FETCHING,
    }
}

export const BOXES_FETCH_ERROR = 'BOXES_FETCH_ERROR'
export function boxesFetchError(error) {
    return {
        type: BOXES_FETCH_ERROR,
        error: error
    }
}

// Режимы рабоыт приложения (modes):

export const MODE_ADD_NEW_BOX = 'MODE_ADD_NEW_BOX'
export function modeAddNewBox() {
    return {
        type: MODE_ADD_NEW_BOX,
        modePayload: null
    }
}

export const MODE_NEW_BOXES_HAVE_ADDED = 'MODE_NEW_BOXES_HAVE_ADDED'
export function modeNewBoxesHaveAdded(boxes) {
    return {
        type: MODE_NEW_BOXES_HAVE_ADDED,
        modePayload: boxes
    }
}

export const MODE_ADD_NEW_PART = 'MODE_ADD_NEW_PART'
export function modeAddNewPart() {
    return {
        type: MODE_ADD_NEW_PART,
        modePayload: null
    }
}

export const MODE_WATCH_PART = 'MODE_WATCH_PART'
export function modeWatchPart(part) {
    return {
        type: MODE_WATCH_PART,
        modePayload: part
    }
}

// stockAdd
export const STOCK_HAS_ADDED = 'STOCK_HAS_ADDED'
export function stockHasAdded(stockAddDTO) {
    return {
        type: STOCK_HAS_ADDED,
        payload: stockAddDTO
    }
}
export const STOCK_IS_ADDING = 'STOCK_IS_ADDING'
export function stockIsAdding() {
    return {
        type: STOCK_IS_ADDING
    }
}

export const STOCK_ADD_ERROR = 'STOCK_ADD_ERROR'
export function stockAddError() {
    return {
        type: STOCK_ADD_ERROR
    }
}

// stockMove
export const STOCK_HAS_MOVED = 'STOCK_HAS_MOVED'
export function stockHasMoved(stockMoveDTO) {
    return {
        type: STOCK_HAS_REMOVED,
    }
}
export const STOCK_IS_MOVING = 'STOCK_IS_MOVING'
export const STOCK_MOVE_ERROR = 'STOCK_MOVE_ERROR'

// stockRemove
export const STOCK_HAS_REMOVED = 'STOCK_HAS_REMOVED'
export function stockHasRemoved(stockRemoveDTO) {
    return {
        type: STOCK_HAS_REMOVED,
    }
}
export const STOCK_IS_REMOVING = 'STOCK_IS_REMOVING'
export const STOCK_REMOVE_ERROR = 'STOCK_REMOVE_ERROR'


export const TOGGLE_PART_CHECKBOX = 'TOGGLE_PART_CHECKBOX'
export const TOGGLE_BOX_CHECKBOX = 'TOGGLE_BOX_CHECKBOX'
export function toggleItemCheckbox(itemType, itemId) {
    switch (itemType) {
        case 'PART':
            return {
                type: TOGGLE_PART_CHECKBOX,
                partId: itemId
            }
        case 'BOX':
            return {
                type: TOGGLE_BOX_CHECKBOX,
                boxId: itemId
            }
        default:
            return new Error(itemType + " " + itemId + " is not present in this handler")
    }
}

export const PART_SEARCH_RESULTS_FETCHED = 'PART_SEARCH_RESULTS_FETCHED'
export function partSearchResultsFetched(searchResults) {
    return {
        type: PART_SEARCH_RESULTS_FETCHED,
        partSearchResults: searchResults
    }
}

export const MODE_PARTS_ARE_SEARCHING = 'MODE_PARTS_ARE_SEARCHING'
export function modePartsAreSearching() {
    return {
        type: MODE_PARTS_ARE_SEARCHING,
    }
}

export const MODE_PARTS_ARE_NOT_SEARCHING = 'MODE_PARTS_ARE_NOT_SEARCHING'
export function modePartsAreNotSearching() {
    return {
        type: MODE_PARTS_ARE_NOT_SEARCHING,
    }
}

export const BOXES_WIPE = 'BOXES_WIPE'
export function wipeBoxes() {
    return {
        type: BOXES_WIPE
    }
}