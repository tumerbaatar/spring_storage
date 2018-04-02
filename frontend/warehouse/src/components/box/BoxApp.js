import React from 'react'
import { Grid, Segment } from 'semantic-ui-react'
import { Route, Switch, withRouter } from 'react-router-dom'
import BoxMenuContainer from '../../containers/box/BoxMenuContainer'
import AddBoxContainer from '../../containers/box/AddBoxContainer'
import BoxListContainer from '../../containers/box/BoxListContainer'
import { BOX_INDEX_PAGE_WILDCARD, ADD_BOX_WILDCARD } from '../../constants/url'
import { connect } from 'react-redux';

const mapStateToProps = (state) => {
  return {
    storageSlug: state.storages.selectedStorageSlug
  }
}

const BoxApp = ({ storageSlug }) => (
  <Grid stackable>
    <BoxMenuContainer />
    <Grid.Row>
      <Grid.Column computer={12}>
        <Switch>
          <Route exact path={BOX_INDEX_PAGE_WILDCARD} component={BoxListContainer} />
          <Route exact path={ADD_BOX_WILDCARD} component={AddBoxContainer} />
          {/* TODO implement fetch box by hash and storage <Route path={'/:storageSlug/boxes/:partHash'} component={BoxInfoContainer} /> */}
        </Switch>
      </Grid.Column>

      <Grid.Column computer={4}>
        <Segment>
          <h3>История перемещения запчастей тут</h3>
        </Segment>
      </Grid.Column>
    </Grid.Row>

  </Grid>
)

const BoxAppContainer = withRouter(connect(mapStateToProps)(BoxApp))

export default BoxAppContainer