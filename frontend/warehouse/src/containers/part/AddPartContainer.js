import AddPart from '../../components/part/AddPart';
import { connect } from 'react-redux';
import { postPart } from '../../actions/acyncActionCreators';

const mapStateToProps = state => {
    return {
        mode: state.applicationMode,
        storageSlug: state.storages.selectedStorageSlug,
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        handleSubmit: (partCreationState) => {
            dispatch(postPart(partCreationState))
        }
    }
}

const AddPartContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(AddPart);

export default AddPartContainer;
