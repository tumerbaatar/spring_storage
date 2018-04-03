import React from 'react'
import { Container, Dropdown, Grid, Menu } from 'semantic-ui-react'
import { NavLink, withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import { selectStorage } from '../actions/index'
import * as urls from '../constants/url';

function mapStateToProps(state) {
  let arr = []
  state.storages.storageList.forEach(s => arr.push({ key: s.id, text: "Склад " + s.name, value: s.slug }))
  return {
    storages: arr,
    storageSlug: state.storages.selectedStorageSlug,
  }
}

function mapDispatchToProps(dispatch, ownProps) {
  return {
    selectStorage: (storageId) => {
      dispatch(selectStorage(storageId))
    }
  }
}


const TopBar = (props) => {
  const { storages, storageSlug, selectStorage } = props

  return (
    <Grid stackable>
      <Grid.Row>
        <Grid.Column>
          <Menu fixed="top" inverted>
            <Container>
              <Dropdown
                placeholder='Склад'
                item
                compact
                selection
                value={storageSlug}
                options={storages}
                onChange={(e, data) => {
                  selectStorage(data.value)
                  props.history.push("/storage/" + storageSlug + "/parts")
                }}
              />
              {/* <Menu.Item as={NavLink} name="home" to={urls.RELATIVE_PART_INDEX_PAGE}>Запчасти</Menu.Item> */}
              {/* <Menu.Item as={NavLink} name="boxes" to={urls.RELATIVE_BOX_INDEX_PAGE}>Хранилища</Menu.Item> */}

              <Menu.Item as={NavLink} name="home" to={"/storage/" + storageSlug + "/parts"}>Запчасти</Menu.Item>
              <Menu.Item as={NavLink} name="boxes" to={"/storage/" + storageSlug + "/boxes"}>Хранилища</Menu.Item>

              {/* <Menu.Item as={NavLink} name="home" to={"/storage/reset/parts"}>Запчасти</Menu.Item> */}
              {/* <Menu.Item as={NavLink} name="boxes" to={"/storage/reset/boxes"}>Хранилища</Menu.Item> */}
 
            </Container>
          </Menu>
        </Grid.Column>
      </Grid.Row>
    </Grid>
  )
}

const TopBarContainer = withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(TopBar))

export default TopBarContainer