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
import { changeStorageInUrls } from './constants/url'

export const store = createStore(partsApp, applyMiddleware(logger, thunkMiddleware))

store.dispatch(fetchStorages())

let currentStorageSlug = null
store.subscribe(
    () => {
        let previousValue = currentStorageSlug
        currentStorageSlug = store.getState().storages.selectedStorageSlug
        if (previousValue !== currentStorageSlug) {
            Promise.resolve(store.dispatch(wipeBoxes()))
                .then(() => {
                    changeStorageInUrls(currentStorageSlug)
                    setTimeout(() => (console.log("timeout")), 1000)
                }).then(() => {
                    store.dispatch(fetchBoxes())
                    store.dispatch(fetchParts())
                })
        }
    }
)

ReactDOM.render(
    <Root store={store} />,
    document.getElementById('root')
)

registerServiceWorker()

