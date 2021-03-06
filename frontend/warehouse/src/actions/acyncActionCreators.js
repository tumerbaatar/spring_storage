import {
  boxesHaveFetched,
  boxHasFetched,

  MODE_ADD_NEW_PART,
  MODE_WATCH_PART,
  modeAddNewPart,
  modeNewBoxesHaveAdded,
  modeWatchPart,
  modePartsAreSearching,
  modePartsAreNotSearching,

  partHasFetched,
  partsHaveFetched,
  stockHasAdded,
  stockHasMoved,
  stockHasRemoved,
  storagesHaveFetched,
  partSearchResultsFetched,
} from './index'
import { server } from '../index'

const username = "user"
const password = "user"
const hash = new Buffer(`${username}:${password}`).toString('base64')

export function fetchStorages() {
  return dispatch => {
    return fetch(
      `${server}/storages`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
        }
      })
      .then(response => response.json())
      .then(json => {
        if (json.length !== 0) {
          dispatch(storagesHaveFetched(json))
        } else {
          console.error("There is no storages on the server")
        }
      })
  }
}

export function fetchParts({ storageSlug, query, page, resultsOnPage }) {
  return dispatch => {
    return fetch(`${server}/storage/parts?storage=${storageSlug}&query=${query}&page=${page}&results_on_page=${resultsOnPage}`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
        }
      }
    )
      .then(
        response => response.json()
      )
      .then(
        json => dispatch(partsHaveFetched(json))
      )
  }
}

export function fetchBoxes({ storageSlug, query, page, resultsOnPage }) {
  return dispatch => {
    return fetch(
      `${server}/storage/boxes?storage=${storageSlug}&query=${query}&page=${page}&results_on_page=${resultsOnPage}`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
        }
      }
    )
      .then(
        response => response.json()
      )
      .then(
        json => {
          //todo move that logic to reducer
          if (json.length !== 0) {
            dispatch(boxesHaveFetched(json))
          } else {
            console.log("There are no boxex on the server")
          }
        }
      )
  }
}

export function fetchPartByHash(partHash) {
  return dispatch => {
    return fetch(`${server}/storage/parts/${partHash}`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
        }
      }
    )
      .then(
        response => response.json()
      )
      .then(json => {
        dispatch(partHasFetched(json))
        dispatch(modeWatchPart(json))
      }
      )
  }
}

export function searchPart({ storageSlug, query, page, resultsOnPage }) {
  return dispatch => {
    dispatch(modePartsAreSearching())
    return fetch(`${server}/search/parts?storage=${storageSlug}&query=${query}&page=${page}&results_on_page=${resultsOnPage}`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
        }
      }
    ).then(
      response => response.json()
    ).then(json => {
      dispatch(modePartsAreNotSearching())
      dispatch(partSearchResultsFetched(json))
    })
  }
}

export function downloadPartStickers(storageSlug, itemIds) {
  return dispatch => {
    return fetch(`${server}/storage/download/part_stickers?storage=${storageSlug}`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify([...itemIds])
      }
    )
      .then(response => response.blob())
      .then(blob => {
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = "partStickers.xlsx";
        a.click();
      })
  }
}

export function downloadBoxStickers(storageSlug, itemIds) {
  return dispatch => {
    return fetch(`${server}/storage/download/box_stickers?storage=${storageSlug}`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify([...itemIds])
      }
    )
      .then(response => response.blob())
      .then(blob => {
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = "boxStickers.xlsx";
        a.click();
      })
  }
}

export function postPart(partCreationState) {
  console.log("----------------")
  console.log("----------------")
  console.log(partCreationState.part)
  console.log("----------------")
  console.log("----------------")
  console.log("----------------")
  return dispatch => {
    return fetch(`${server}/storage/parts/create`,
      {
        headers: {
          'Authorization': `Basic ${hash}`,
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(partCreationState.part)
      }
    ).then(
      response => response.json()
    ).then(json => {
      /*
      if (json.indexOf("duplicated") >= 0) {
        console.error("------------------------------")
        console.error("Запчасть с таким парт-номером уже была добавлена на данный склад")
        console.error("------------------------------")
        throw new Error("It is duplicate")
      }
      */
      dispatch(partHasFetched(json))
      switch (partCreationState.afterCreation) {
        case MODE_WATCH_PART:
          dispatch(modeWatchPart(json))
          break
        case MODE_ADD_NEW_PART:
          dispatch(modeAddNewPart())
          break
        default:
          console.log("Default action for after creation still is not defined")
      }
    }
    )
    /* 
    I can handle exceptions by that way when will implement partFetchError(error) handler. 
    But now it omits all the exceptional behavior in browser. Logs in console still present.
    .catch(
      error => {
        console.log("partFetch error")
        dispatch(partFetchError(error))
      }
    )
    */
  }
}

export function postBoxes(boxes) {
  return dispatch => {
    fetch(`${server}/storage/boxes/create`, {
      headers: {
        'Authorization': `Basic ${hash}`,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(boxes)
    }).then(
      response => response.json()
    ).then(json => {
      dispatch(modeNewBoxesHaveAdded())
      dispatch(boxesHaveFetched(json))
    })
  }
}

export function stockAdd(stockAddState) {
  return dispatch => {
    fetch(`${server}/storage/stock/add`, {
      headers: {
        'Authorization': `Basic ${hash}`,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(
        stockAddState
        // {
          // part: stockAddState.part,
          // box: stockAddState.box,
          // quantity: stockAddState.quantity,
          // price: stockAddState.price,
          // comment: stockAddState.comment,
          // storage: stockAddState.storage
        // }
      )
    }).then(
      response => response.json()
    ).then(json => {
      const { part } = json
      const { box } = json
      dispatch(stockHasAdded(json))
      dispatch(boxHasFetched(box))
      dispatch(partHasFetched(part))
    })
  }
}

export function stockRemove(stockRemoveState) {
  return dispatch => {
    fetch(`${server}/storage/stock/remove`, {
      headers: {
        'Authorization': `Basic ${hash}`,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(
        stockRemoveState
        // {
        //   part: stockRemoveState.part,
        //   fromBox: stockRemoveState.fromBox,
        //   quantity: stockRemoveState.quantity,
        //   comment: stockRemoveState.comment,
        //   storage: stockRemoveState.storage
        // }
      )
    }).then(
      response => response.json()
    ).then(json => {
      const { part } = json
      const { fromBox } = json
      console.log("stockHasRemoved part", part)
      console.log("stockHasRemoved box", fromBox)
      dispatch(stockHasRemoved(json))
      dispatch(partHasFetched(part))
      dispatch(boxHasFetched(fromBox))
    })
  }
}

export function stockMove(stockMoveState) {
  return dispatch => {
    fetch(`${server}/storage/stock/move`, {
      headers: {
        'Authorization': `Basic ${hash}`,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(
        stockMoveState
        // {
        //   part: stockMoveState.part,
        //   boxFrom: stockMoveState.boxFrom,
        //   boxTo: stockMoveState.boxTo,
        //   quantity: stockMoveState.quantity,
        //   comment: stockMoveState.comment,
        //   storage: stockMoveState.storage
        // }
      )
    }).then(
      response => response.json()
    ).then(json => {
      const { part } = json
      const { boxFrom } = json
      const { boxTo } = json
      console.log("stockHasMoved part", part)
      console.log("stockHasMoved box", boxFrom)
      dispatch(stockHasMoved(json))
      dispatch(partHasFetched(part))
      dispatch(boxHasFetched(boxFrom))
      dispatch(boxHasFetched(boxTo))
    })
  }
}

export function uploadImage(partId, images) {
  return dispatch => {
    let formData = new FormData()
    for (let i = 0; i < images.length; i++) {
      formData.append("files[]", images[i])
    }
    formData.append("part_id", partId)
    fetch(`${server}/storage/parts/images/upload`, {
      headers: {
        'Authorization': `Basic ${hash}`,
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: formData
    })
      .then(response => response.json())
      .then(json => dispatch(partHasFetched(json)))
      .catch(e => console.error(e))
  }
}
