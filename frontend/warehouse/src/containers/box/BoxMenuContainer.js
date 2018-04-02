import React from 'react'
import { Grid, Menu, Input, Button } from 'semantic-ui-react'
import { modeAddNewBox } from '../../actions/index'
import { NavLink } from 'react-router-dom'
import { connect } from 'react-redux'
import { downloadBoxesStickers } from '../../actions/acyncActionCreators'
import { withRouter } from 'react-router-dom'
import { BOX_ADD_PAGE } from '../../constants/url';

const mapStateToProps = (state) => {
    return {
        selectedBoxes: state.boxes.selectedItems,
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        modeAddNewBoxHandler: (e, data) => {
            dispatch(modeAddNewBox())
        },
        downloadBoxStickersHandler: (selectedIds) => {
            dispatch(downloadBoxesStickers(selectedIds))
        }
    }
}

const BoxMenu = ({ selectedBoxes, modeAddNewBoxHandler, downloadBoxStickersHandler }) => (
    <Grid.Row>
        <Grid.Column>
            <Menu stackable >
                <Menu.Item><Input icon='search' placeholder='Поиск коробки' /></Menu.Item>
                <Menu.Item as={NavLink} name="addBox" to={BOX_ADD_PAGE} onClick={(e, data) => { modeAddNewBoxHandler(e, data) }}>Добавить коробку</Menu.Item>
                <Menu.Item
                    as={Button}
                    name="downloadBoxesStickers"
                    onClick={() => { downloadBoxStickersHandler(selectedBoxes) }}
                >
                    Скачать наклейки коробок
                </Menu.Item>
            </Menu>
        </Grid.Column>
    </Grid.Row >
)

const BoxMenuContainer = withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(BoxMenu))

export default BoxMenuContainer