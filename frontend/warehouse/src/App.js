import React from 'react'
import TopBar from './components/TopBar'

import { Route, Switch, withRouter } from 'react-router-dom'
import PartApp from './components/part/PartApp'
import Redirect from 'react-router-dom/Redirect'

import BoxApp from './components/box/BoxApp'
import { connect } from 'react-redux'

import * as url from './constants/url'

const mapStateToProps = (state) => {
  return {
    storageSlug: state.storages.selectedStorageSlug
  }
}

const App = ({ storageSlug }) => (
  <div style={{ padding: "1em" }}>
    <TopBar />
    <Switch>
      <Route path={url.PART_APP_WILDCARD} component={PartApp} />
      <Route path={url.BOX_APP_WILDCARD} component={BoxApp} />
      {
        storageSlug ?
          <Redirect exact from='/' to={url.PART_INDEX_PAGE} />
          : null
      }
    </Switch>
  </div>
)

const AppContainer = withRouter(connect(mapStateToProps)(App))

export default AppContainer
