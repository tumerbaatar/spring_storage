import PartInfo from '../../components/part/PartInfo'
import { connect } from 'react-redux'
import { fetchPartByHash, stockAdd, stockRemove, stockMove, uploadImage } from '../../actions/acyncActionCreators'

const mapStateToProps = (state) => {
    let part
    if (state.applicationMode.modePayload) {
        part = state.parts.partList.find(p => p.id === state.applicationMode.modePayload.id)
    } else {
        part = null
    }
    return {
        part: part,
        parts: state.parts.partList,
        boxes: state.boxes.boxList,
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        fetchPartByHash: (partHash) => {
            dispatch(fetchPartByHash(partHash))
        },
        handleStockAdd: (stockAddState) => {
            dispatch(stockAdd(stockAddState))
        },
        handleStockRemove: (stockRemoveState) => {
            dispatch(stockRemove(stockRemoveState))
        },
        handleStockMove: (stockMoveState) => {
            dispatch(stockMove(stockMoveState))
        },
        handleImageUpload: (partId, images) => {
            dispatch(uploadImage(partId, images))
        },
    }
}

const PartInfoContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(PartInfo)

export default PartInfoContainer
