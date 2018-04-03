import {server} from '../index'

export const STORAGES = `${server}/storages`

export const CREATE_PART_WILDCARD = '/storage/:storageSlug/parts/add'
export const CREATE_BOX_WILDCARD = '/storage/:storageSlug/boxes/add'
export const PART_PAGE_WILDCARD = '/storage/:storageSlug/parts/:partHash'
export const PART_APP_WILDCARD = '/storage/:storageSlug/parts(/?)(.*)'
export const BOX_APP_WILDCARD = '/storage/:storageSlug/boxes(/?)(.*)'
export const PART_INDEX_PAGE_WILDCARD = '/storage/:storageSlug/parts'

export let RELATIVE_PART_INDEX_PAGE
export let RELATIVE_BOX_INDEX_PAGE
export let RELATIVE_CREATE_PART
export let RELATIVE_CREATE_BOXES

export let PART_INDEX_PAGE
export let BOX_INDEX_PAGE

export let UPLOAD_IMAGES
export let DOWNLOAD_PART_STICKERS
export let DOWNLOAD_BOX_STICKERS

export let CREATE_PART
export let CREATE_BOXES

export let ADD_STOCK
export let REMOVE_STOCK
export let MOVE_STOCK

export let RELATIVE_PART_PAGE_INCOMPLETE
export let PART_PAGE_INCOMPLETE
export let RELATIVE_SEARCH_QUERY_INCOMPLETE
export let SEARCH_QUERY_INCOMPLETE

export const changeStorageInUrls = (storage) => {
  RELATIVE_PART_INDEX_PAGE = `/storage/${storage}/parts`
  PART_INDEX_PAGE = `${server}${RELATIVE_PART_INDEX_PAGE}`

  RELATIVE_BOX_INDEX_PAGE = `/storage/${storage}/boxes`
  BOX_INDEX_PAGE = `${server}${RELATIVE_BOX_INDEX_PAGE}`

  RELATIVE_CREATE_PART = `/storage/${storage}/parts/add`
  CREATE_PART = `${server}${RELATIVE_CREATE_PART}`

  RELATIVE_CREATE_BOXES = `/storage/${storage}/boxes/add`
  CREATE_BOXES = `${server}${RELATIVE_CREATE_BOXES}`

  UPLOAD_IMAGES = `${server}/storage/images/parts/upload`
  DOWNLOAD_PART_STICKERS = `${server}/storage/${storage}/download/part_stickers`
  DOWNLOAD_BOX_STICKERS = `${server}/storage/${storage}/download/box_stickers`

  ADD_STOCK = `${server}/stock/parts/add`
  REMOVE_STOCK = `${server}/stock/parts/remove`
  MOVE_STOCK = `${server}/stock/parts/move`

  RELATIVE_PART_PAGE_INCOMPLETE = `/storage/${storage}/parts/`
  PART_PAGE_INCOMPLETE = `${server}${RELATIVE_PART_PAGE_INCOMPLETE}`

  RELATIVE_SEARCH_QUERY_INCOMPLETE = `/search?storage=${storage}&query=`
  SEARCH_QUERY_INCOMPLETE = `${server}${RELATIVE_SEARCH_QUERY_INCOMPLETE}`

  console.log("*********************")
  console.log(PART_INDEX_PAGE, storage)
  console.log("*********************")
}