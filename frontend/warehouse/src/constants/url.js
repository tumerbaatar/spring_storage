
export const serverName = 'http://localhost:8080'

export const SERVER_NAME = `${serverName}/`

export const STORAGES = `${serverName}/storages`

export const ADD_PART_WILDCARD = '/storage/:storageSlug/parts/add'
export const ADD_BOX_WILDCARD = '/storage/:storageSlug/boxes/add'
export const PART_PAGE_WILDCARD = '/storage/:storageSlug/parts/:partHash'
export const PART_APP_WILDCARD = '/storage/:storageSlug/parts(/?)(.*)'
export const BOX_APP_WILDCARD = '/storage/:storageSlug/boxes(/?)(.*)'
export const PART_INDEX_PAGE_WILDCARD = '/storage/:storageSlug/parts'
export const BOX_INDEX_PAGE_WILDCARD = '/storage/:storageSlug/boxes'

export let RELATIVE_PART_INDEX_PAGE
export let RELATIVE_BOX_INDEX_PAGE
export let PART_INDEX_PAGE
export let BOX_INDEX_PAGE
export let BOX_ADD_PAGE

export let UPLOAD_IMAGES
export let DOWNLOAD_PART_STICKERS
export let DOWNLOAD_BOX_STICKERS

export let CREATE_PART
export let CREATE_BOXES

export let ADD_STOCK
export let REMOVE_STOCK
export let MOVE_STOCK

export let PART_PAGE_INCOMPLETE
export let SEARCH_QUERY_INCOMPLETE

export const changeStorageInUrls = (storage) => {
  console.log("*********************")
  console.log("*********************")
  console.log("*********************")
  console.log(storage)
  console.log("*********************")
  console.log("*********************")
  console.log("*********************")

  RELATIVE_PART_INDEX_PAGE = `/storage/${storage}/parts`
  RELATIVE_BOX_INDEX_PAGE = `/storage/${storage}/boxes`
  PART_INDEX_PAGE = `${serverName}${RELATIVE_PART_INDEX_PAGE}`
  BOX_INDEX_PAGE = `${serverName}${RELATIVE_BOX_INDEX_PAGE}`

  BOX_ADD_PAGE = `${serverName}/storage/${storage}/boxes/add`

  UPLOAD_IMAGES = `${serverName}/storage/images/parts/upload`
  DOWNLOAD_PART_STICKERS = `${serverName}/storage/${storage}/download/part_stickers`
  DOWNLOAD_BOX_STICKERS = `${serverName}/storage/${storage}/download/box_stickers`

  CREATE_PART = `${serverName}/storage/${storage}/parts/add`
  CREATE_BOXES = `${serverName}/storage/${storage}/boxes/add`

  ADD_STOCK = `${serverName}/stock/parts/add`
  REMOVE_STOCK = `${serverName}/stock/parts/remove`
  MOVE_STOCK = `${serverName}/stock/parts/move`

  PART_PAGE_INCOMPLETE = `${serverName}/storage/${storage}/parts/`
  SEARCH_QUERY_INCOMPLETE = `${serverName}/search?storage=${storage}&query=`

  console.log("*********************")
  console.log("*********************")
  console.log("*********************")
  console.log(PART_INDEX_PAGE, storage)
  console.log("*********************")
  console.log("*********************")
  console.log("*********************")
}