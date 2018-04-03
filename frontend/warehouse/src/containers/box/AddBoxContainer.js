import { connect } from 'react-redux'
import AddBox from '../../components/box/AddBox'
import { postBoxes } from '../../actions/acyncActionCreators'

const mapStateToProps = state => {
    return {
        mode: state.applicationMode,
        storageSlug: state.storages.selectedStorageSlug,
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        handleSubmit: (storageSlug, boxCreationState) => {
            dispatch(postBoxes(storageSlug, boxCreationState))
        }
    }
}

const AddBoxContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(AddBox)


export default AddBoxContainer