import { connect } from 'react-redux'
import PartList from '../../components/part/PartList'
import { modeWatchPart, toggleItemCheckbox } from '../../actions';
import { uploadImage } from '../../actions/acyncActionCreators'

const mapStateToProps = (state) => {
    return {
        parts: state.parts.partList,
        boxes: state.boxes.boxList,
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        handleModeWatchPart: (part) => () => {
            dispatch(modeWatchPart(part))
        },
        handleTogglePartCheckBox: (partId) => () => {
            dispatch(toggleItemCheckbox("PART", partId))
        },
        handleImageUpload: (partId, images) => {
            dispatch(uploadImage(partId, images))
        },
    }
}

const PartListContainer = connect(mapStateToProps, mapDispatchToProps)(PartList)

export default PartListContainer