import React from 'react'
import { Segment, Header, Form, Dropdown } from 'semantic-ui-react'
import { defineBox, initialBoxInStockEntries } from '../../../util/index'
import BoxesInPart from '../BoxesInPart'

class MoveStockTab extends React.Component {
  constructor(props) {
    super(props)
    const currentBox = initialBoxInStockEntries(props.part, props.boxes)
    this.state = {
      boxSelectionFrom: currentBox !== null ? currentBox.id : 0,
      quantity: 1,
      part: props.part,
      boxFrom: currentBox,
      boxTo: null,
      comment: ''
    }
  }

  handleChange = (name) => (event) => {
    this.setState({
      [name]: event.target.value
    })
  }

  handleChangeRadios = (value) => () => {
    this.setState({
      boxSelectionFrom: value.boxId,
      boxFrom: defineBox(this.props.boxes, value.boxId)
    })
  }

  handleSelectDropDownLocation = (boxes) => {
    return (e, { value }) => {
      let box = boxes.find(b => b.name === value)
      if (box) {
        this.setState({
          boxTo: box
        })
      }
    }
  }

  handleSubmit = () => {
    this.setState({
      storage: this.props.storageSlug
    })
    this.props.handleStockMove(this.state)
  }

  render() {
    const part = this.props.part
    const boxes = this.props.boxes

    return (
      <Segment>
        <Header>Переместить запас</Header>
        <Form >
          <Form.Group widths="equal">
            <Form.Field >
              <Form.Group grouped>
                <label>Из местоположения: </label>

                <BoxesInPart
                  allBoxes={boxes}
                  part={part}
                  currentIndex={this.state.boxSelectionFrom}
                  onChangeHandler={this.handleChangeRadios}
                />
              </Form.Group>

              <Form.Group>
                <Form.Field>
                  <label>В местоположение</label>
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

export default MoveStockTab