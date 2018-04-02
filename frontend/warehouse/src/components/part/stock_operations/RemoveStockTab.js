import React from 'react'
import { Segment, Header, Form } from 'semantic-ui-react'
import { defineBox, initialBoxInStockEntries } from '../../../util/index'
import BoxesInPart, { stockEntriesByBoxGroup } from '../BoxesInPart'


class RemoveStockTab extends React.Component {
  constructor(props) {
    super(props)
    const currentBox = initialBoxInStockEntries(props.part, props.boxes)
    this.state = {
      assignedLocation: currentBox !== null ? currentBox.id : 0,
      quantity: 1,
      part: props.part,
      fromBox: currentBox,
      comment: ''
    }
  }

  componentWillReceiveProps(nextProps) {
    const currentBox = initialBoxInStockEntries(nextProps.part, nextProps.boxes)
    this.setState({
      fromBox: currentBox,
      assignedLocation: currentBox !== null ? currentBox.id : 0,
    })
  }

  handleChange = (name) => (event) => {
    this.setState({
      [name]: event.target.value
    })
  }

  handleChangeRadios = (value) => () => {
    this.setState({
      assignedLocation: value.boxId,
      fromBox: defineBox(this.props.boxes, value.boxId)
    })
  }

  handleSubmit = () => {
    this.props.handleStockRemove(this.state)
  }

  render() {
    const part = this.props.part
    const boxes = this.props.boxes
    console.log(
      "stockEntriesByBoxGroup",
      stockEntriesByBoxGroup(part).keys().next().value
    )
    return (
      <Segment>
        <Header>Удалить/убрать запчасть</Header>
        <Form >
          <Form.Group widths="equal">
            <Form.Field >
              <Form.Group grouped>
                <label>Из местоположения: </label>

                <BoxesInPart
                  allBoxes={boxes}
                  part={part}
                  currentIndex={this.state.assignedLocation}
                  onChangeHandler={this.handleChangeRadios}
                />

              </Form.Group>
            </Form.Field>
            <Form.Field>
              <label>Количество</label>
              <input value={this.state.quantity} onChange={this.handleChange("quantity")} />
            </Form.Field>
          </Form.Group>
          <Form.Field>
            <label>Комментарий</label>
            <input value={this.state.comment} onChange={this.handleChange("comment")} />
          </Form.Field>
          <Form.Button onClick={this.handleSubmit}>Сохранить</Form.Button>
        </Form>
      </Segment>
    )
  }
}

export default RemoveStockTab