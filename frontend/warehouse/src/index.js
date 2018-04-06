import React from 'react';
import ReactDOM from 'react-dom';
import 'semantic-ui-css/semantic.min.css';
import Root from './Root'
import partsApp from './reducers/index'
import { applyMiddleware, createStore } from 'redux'
import thunkMiddleware from 'redux-thunk'
import logger from 'redux-logger'
import { fetchBoxes, fetchParts, fetchStorages } from './actions/acyncActionCreators'
import { wipeBoxes } from './actions/index'
import registerServiceWorker from './registerServiceWorker'

export const server = 'http://localhost:8080'
export const store = createStore(partsApp, applyMiddleware(logger, thunkMiddleware))

store.dispatch(fetchStorages())

const initialItemsQuery = {
    query: "",
    page: 1,
    resultsOnPage: 100
}

let currentStorageSlug = ''
store.subscribe(
    () => {
        let previousValue = currentStorageSlug
        currentStorageSlug = store.getState().storages.selectedStorageSlug
        if (previousValue !== currentStorageSlug) {
            Promise.resolve(store.dispatch(wipeBoxes()))
                .then(() => {
                    store.dispatch(fetchBoxes({storageSlug: currentStorageSlug, ...initialItemsQuery}))
                    store.dispatch(fetchParts({storageSlug: currentStorageSlug, ...initialItemsQuery}))
                })
        }
    }
)

ReactDOM.render(
    <Root store={store} />,
    document.getElementById('root')
)

registerServiceWorker()

