import React from 'react'
import { Grid, Segment } from 'semantic-ui-react'
import { Route, Switch } from 'react-router-dom'
import BoxMenuContainer from '../../containers/box/BoxMenuContainer'
import AddBoxContainer from '../../containers/box/AddBoxContainer'
import BoxListContainer from '../../containers/box/BoxListContainer'
import * as urls from '../../constants/url'

const BoxApp = () => (
  <Grid stackable>
    <BoxMenuContainer />
    <Grid.Row>
      <Grid.Column computer={12}>
        <Switch>
          <Route exact path={urls.BOX_INDEX_PAGE_WILDCARD} component={BoxListContainer} />
          <Route exact path={urls.CREATE_BOX_WILDCARD} component={AddBoxContainer} />
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

export default BoxApp