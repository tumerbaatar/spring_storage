import React from 'react'
import { Grid, Menu, Button } from 'semantic-ui-react'
import { modeAddNewPart } from '../../actions/index'
import { NavLink } from 'react-router-dom'
import { connect } from 'react-redux'
import { downloadPartsStickers } from '../../actions/acyncActionCreators'
import { withRouter } from 'react-router-dom'
import PartSearchContainer from './PartSearch'

const mapStateToProps = (state) => {
  return {
    selectedParts: state.parts.selectedItems,
    storageSlug: state.storages.selectedStorageSlug,
  }
}

const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    partCreationHandler: (e, data) => { //todo 21/02/2018 поработать над более семантически правильным названием
      dispatch(modeAddNewPart())
    },
    stickersDownloadHandler: (selectedPartsIds) => {
      dispatch(downloadPartsStickers(selectedPartsIds))
    },
  }
}

const PartMenu = (props) => {
  const {
    storageSlug,
    selectedParts,
    partCreationHandler,
    stickersDownloadHandler,
  } = props

  return (
    <Grid.Row>
      <Grid.Column>
        <Menu stackable >
          <Menu.Item>
            <PartSearchContainer />
          </Menu.Item>
          <Menu.Item
            as={NavLink}
            name="createPart"
            to={`/storage/${storageSlug}/parts/add`}
            onClick={(e, data) => { partCreationHandler(e, data) }}
          >Добавить запчасть</Menu.Item>
          <Menu.Item
            as={Button}
            name="downloadPartsStickers"
            onClick={() => { stickersDownloadHandler(selectedParts) }}
          >
            Скачать наклейки
                </Menu.Item>
        </Menu>
      </Grid.Column>
    </Grid.Row >
  )
}

const PartMenuContainer = withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(PartMenu))

export default PartMenuContainer