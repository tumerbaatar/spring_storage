import React from 'react'
import TopBar from './components/TopBar'

import { Route, Switch } from 'react-router-dom'
import PartApp from './components/part/PartApp'
import Redirect from 'react-router-dom/Redirect'
import BoxApp from './components/box/BoxApp'
import * as url from './constants/url'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'
import { fetchStorages } from './actions/acyncActionCreators';

const mapStateToProps = (state) => {
  return {
    storageSlug: state.storages.selectedStorageSlug
  }
}

const App = ({ storageSlug, fetchStoragesInitially }) => {
  console.log("**********************")
  console.log("**********************")
  console.log("**********************")
  console.log(storageSlug)
  console.log("**********************")
  console.log("**********************")
  console.log("**********************")

  if (!storageSlug) {
    return <div>there is no storage slug{storageSlug}</div>
  }
  // else {
    // return <div>{storageSlug}</div>
  // }

  return (
    <div style={{ padding: "1em" }}>
      <TopBar />
      <Switch>
        <Route path={url.PART_APP_WILDCARD} component={PartApp} />
        {/* <Route path={url.BOX_APP_WILDCARD} component={BoxApp} /> */}
        {
          // <Redirect exact from='/' to={url.PART_INDEX_PAGE} />
        }
      </Switch>
    </div>
  )
}

const AppContainer = withRouter(connect(mapStateToProps)(App))

export default AppContainer
