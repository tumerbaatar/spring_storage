import React from 'react'
import TopBar from './components/TopBar'
import { Loader } from 'semantic-ui-react'
import { Route, Switch } from 'react-router-dom'
import PartApp from './components/part/PartApp'
import Redirect from 'react-router-dom/Redirect'
import BoxApp from './components/box/BoxApp'
import { connect } from 'react-redux'
import { withRouter } from 'react-router-dom'

const mapStateToProps = (state) => {
  return {
    storageSlug: state.storages.selectedStorageSlug
  }
}

const App = ({ storageSlug }) => {
  if (!storageSlug) {
    return <Loader>Storages is not fetched yet</Loader>
  }

  return (
    <div style={{ padding: "1em" }}>
      <TopBar />
      <Switch>
        <Route path={'/storage/:storageSlug/parts(/?)(.*)'} component={PartApp} />
        <Route path={'/storage/:storageSlug/boxes(/?)(.*)'} component={BoxApp} />
        {
          <Redirect exact from='/' to={`/storage/${storageSlug}/parts`} />
        }
      </Switch>
    </div>
  )
}

const AppContainer = withRouter(connect(mapStateToProps)(App))

export default AppContainer
