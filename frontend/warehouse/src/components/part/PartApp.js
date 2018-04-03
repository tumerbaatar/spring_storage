import React from 'react'
import { Grid, Segment } from 'semantic-ui-react'
import { Route, Switch } from 'react-router-dom'
import PartMenuContainer from '../../containers/part/PartMenuContainer'
import AddPartContainer from '../../containers/part/AddPartContainer'
import PartInfoContainer from '../../containers/part/PartInfoContainer'
import PartListContainer from '../../containers/part/PartListContainer'

const PartApp = () => (
  <Grid stackable>
    <PartMenuContainer />

    <Grid.Row>
      <Grid.Column computer={12}>
        <Switch>
          <Route exact path={'/storage/:storageSlug/parts/add'} component={AddPartContainer} />
          <Route path={'/storage/:storageSlug/parts/:partHash'} component={PartInfoContainer} />
          <Route exact path={'/storage/:storageSlug/parts'} component={PartListContainer} />
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

export default PartApp