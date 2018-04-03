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
import * as urls from '../constants/url'

export function fetchStorages() {
  return dispatch => {
    return fetch(urls.STORAGES)
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

export function fetchParts() {
  return dispatch => {
    return fetch(urls.PART_INDEX_PAGE)
      .then(
        response => response.json()
      )
      .then(
        json => dispatch(partsHaveFetched(json))
      )
  }
}

export function fetchBoxes() {
  return dispatch => {
    return fetch(urls.BOX_INDEX_PAGE)
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
    return fetch(urls.PART_PAGE_INCOMPLETE + partHash)
      .then(
        response => response.json()
      )
      .then(json => {
        dispatch(modeWatchPart(json))
      }
      )
  }
}

export function searchPart(query) {
  return dispatch => {
    dispatch(modePartsAreSearching())
    return fetch(urls.SEARCH_QUERY_INCOMPLETE + query).then(
      response => response.json()
    ).then(json => {
      dispatch(modePartsAreNotSearching())
      dispatch(partSearchResultsFetched(json))
    })
  }
}

export function downloadPartsStickers(itemIds) {
  return dispatch => {
    console.log(itemIds)
    return fetch(urls.DOWNLOAD_PART_STICKERS,
      {
        headers: {
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

export function downloadBoxesStickers(itemIds) {
  return dispatch => {
    return fetch(urls.DOWNLOAD_BOX_STICKERS,
      {
        headers: {
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
  return dispatch => {
    return fetch(urls.CREATE_PART,
      {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(partCreationState.part)
      }
    ).then(
      response => response.json()
    ).then(json => {
      if (json.indexOf("duplicated") >= 0) {
        console.error("------------------------------")
        console.error("Запчасть с таким парт-номером уже была добавлена на данный склад")
        console.error("------------------------------")
        throw new Error("It is duplicate")
      }

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
    fetch(urls.CREATE_BOXES, {
      headers: {
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
    fetch(urls.ADD_STOCK, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(
        {
          part: stockAddState.part,
          box: stockAddState.box,
          quantity: stockAddState.quantity,
          price: stockAddState.price,
          comment: stockAddState.comment
        }
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
    fetch(urls.REMOVE_STOCK, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(
        {
          part: stockRemoveState.part,
          fromBox: stockRemoveState.fromBox,
          quantity: stockRemoveState.quantity,
          comment: stockRemoveState.comment,
        }
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
    fetch(urls.MOVE_STOCK, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(
        {
          part: stockMoveState.part,
          boxFrom: stockMoveState.boxFrom,
          boxTo: stockMoveState.boxTo,
          quantity: stockMoveState.quantity,
          comment: stockMoveState.comment,
        }
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

    fetch(urls.UPLOAD_IMAGES, {
      method: 'POST',
      body: formData
    })
      .then(response => response.json())
      .then(json => dispatch(partHasFetched(json)))
      .catch(e => console.error(e))
  }
}
