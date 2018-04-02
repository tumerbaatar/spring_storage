import React from 'react';
import ReactDOM from 'react-dom';
import 'semantic-ui-css/semantic.min.css';
import Root from './Root'
// import registerServiceWorker from './registerServiceWorker'
import partsApp from './reducers/index'
import {applyMiddleware, createStore} from 'redux'
import thunkMiddleware from 'redux-thunk'
import logger from 'redux-logger'
import {fetchBoxes, fetchParts} from './actions/acyncActionCreators'

// export const serverName = 'http://localhost:8080'
export const serverName = ''

// export const serverName = 'https://garantreset.herokuapp.com'


let store = createStore(partsApp, applyMiddleware(logger, thunkMiddleware))

store.dispatch(fetchBoxes()).then(() => console.log(store.getState().boxes))
store.dispatch(fetchParts())

ReactDOM.render(
    <Root store = {store}/>,
    document.getElementById('root')
);

// registerServiceWorker();

export { store }

