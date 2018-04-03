import React from 'react'
import { Search } from 'semantic-ui-react'
import { connect } from 'react-redux'
import { searchPart } from '../../actions/acyncActionCreators'
import { withRouter } from 'react-router-dom'
import {server} from '../../index'

const mapStateToProps = (state) => {
  let partSearchResults = state.parts.partSearchResults
  return {
    storageSlug: state.storages.selectedStorageSlug,
    partSearchResults: partSearchResults,
    searchResults: partSearchResults ? partSearchResults.map(p => {
      return {
        id: p.id,
        title: p.name,
        description: p.description,
        image: `${server}` + p.images[0]
      }
    }) : null,
    isSearching: state.parts.isSearching,
  }
}

const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    partSearchHandler: (value) => {
      dispatch(searchPart(value))
    },
  }
}

class PartSearch extends React.Component {
  componentWillMount() {
    this.resetComponent()
  }

  componentWillReceiveProps(nextProps) {
    this.setState({
      isSearching: nextProps.isSearching,
      results: nextProps.searchResults,
    })
  }

  resetComponent = () => this.setState({ isSearching: false, results: [], value: '' })

  handleResultSelect = (e, { result }) => {
    const part = this.props.partSearchResults.find(p => p.id === result.id)
    const { storageSlug, history } = this.props
    history.push(`/storage/${storageSlug}/parts/` + part.permanentHash)
  }

  handleSearchChange = (e, { value }) => {
    const { partSearchHandler } = this.props
    this.setState({ isSearching: true, value })
    partSearchHandler(value)
  }

  render() {
    const { isSearching, value, results } = this.state

    return (
      <Search
        loading={isSearching}
        onResultSelect={this.handleResultSelect}
        onSearchChange={this.handleSearchChange}
        results={results}
        value={value}
      />
    )
  }
}

const PartSearchContainer = withRouter(connect(
  mapStateToProps,
  mapDispatchToProps
)(PartSearch))

export default PartSearchContainer 