import BoxList from '../../components/box/BoxList'
import { connect } from 'react-redux'
import { toggleItemCheckbox } from '../../actions';

const mapStateToProps = (state) => {
    return {
        boxes: state.boxes.boxList,
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        handleToggleBoxCheckbox: (boxId) => () => {
            dispatch(toggleItemCheckbox("BOX", boxId))
        }
    }
}

const BoxListContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(BoxList)

export default BoxListContainer