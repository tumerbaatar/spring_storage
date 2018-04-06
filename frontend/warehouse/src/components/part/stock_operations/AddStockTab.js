import React from 'react'
import { Segment, Header, Form, Dropdown } from 'semantic-ui-react'
import { defineBox, initialBoxInStockEntries } from '../../../util/index'
import BoxesInPart from '../BoxesInPart'

class AddStockTab extends React.Component {
  constructor(props) {
    super(props)
    const currentBox = initialBoxInStockEntries(props.part, props.boxes)

    this.state = {
      assignedLocation: currentBox ? currentBox.id : 0,
      part: props.part,
      box: currentBox,
      quantity: 1,
      price: 0,
      comment: ''
    }
  }

  handleChange = (name) => (event) => {
    this.setState({ [name]: event.target.value })
  }

  handleChangeRadios = (value) => () => {
    this.setState({
      assignedLocation: value.boxId,
      box: defineBox(this.props.boxes, value.boxId)
    })
  }

  handleSelectDropDownLocation = (boxes) => {
    return (e, { value }) => {
      let box = boxes.find(b => b.name === value)
      if (box) {
        this.setState({
          box: box,
          assignedLocation: box.id
        })
      }
    }
  }

  handleChange = (name) => (event) => {
    this.setState({
      [name]: event.target.value
    })
  }

  handleSubmit = () => {
    this.props.handleStockAdd(this.state)
  }

  render() {
    const { part, boxes } = this.props

    return (
      <Segment>
        <Header>Положить на хранение</Header>
        <Form >
          <Form.Group widths="equal">
            <Form.Field >
              <Form.Group grouped>
                <label>Местоположение: </label>

                <BoxesInPart
                  allBoxes={boxes}
                  part={part}
                  currentIndex={this.state.assignedLocation}
                  onChangeHandler={this.handleChangeRadios}
                />

                <Form.Field
                  label='Выбрать другое'
                  onChange={this.handleChangeRadios()}
                />

                <Form.Field>
                  <Dropdown
                    search
                    searchInput={{ type: 'string' }}
                    selection
                    onChange={this.handleSelectDropDownLocation(boxes)}
                    options={
                      [...boxes.map(b => {
                        return ({
                          key: b.id,
                          text: b.name,
                          value: b.name,
                        })
                      })]
                    }
                    placeholder='Выберите место хранения...'
                  />
                </Form.Field>

                <Form.Field>
                  <Form.Field>
                    <label>Количество</label>
                    <input value={this.state.quantity} default={1} onChange={this.handleChange("quantity")} />
                  </Form.Field>
                  <Form.Field>
                    <label>Цена</label>
                    <input value={this.state.price} default={0} onChange={this.handleChange("price")} />
                  </Form.Field>
                </Form.Field>

              </Form.Group>

            </Form.Field>
          </Form.Group>

          <Form.Field>
            <label>Комментарий</label>
            <input value={this.state.comment} onChange={this.handleChange("comment")} />
          </Form.Field>
          <Form.Button onClick={this.handleSubmit}>Сохранить</Form.Button>
        </Form>
      </Segment >
    )
  }
}


export default AddStockTab