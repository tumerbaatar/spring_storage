import React from 'react'
import { Segment, Form, Radio } from 'semantic-ui-react'
import { Redirect } from 'react-router-dom'
import { MODE_WATCH_PART, MODE_ADD_NEW_PART } from '../../actions/index'
import { PART_PAGE_INCOMPLETE } from '../../constants/url';


class AddPart extends React.Component {
  state = {
    part: {
      partNumber: '',
      name: '',
      description: '',
      manufacturer: '',
      manufacturerPartNumber: '',
    },

    afterCreation: MODE_WATCH_PART
  }

  handleChange = name => event => {
    this.setState(
      {
        part: {
          ...this.state.part,
          [name]: event.target.value
        }
      }
    )
  }

  handleChangeRadios = (e, { value }) => this.setState({ afterCreation: value })

  handleSubmit = event => {
    event.preventDefault()
    this.props.handleSubmit(this.props.selectedStorageSlug, this.state)
  }

  render() {
    const mode = this.props.mode

    if (mode.name === MODE_WATCH_PART) {
      return (<Redirect push to={PART_PAGE_INCOMPLETE + mode.modePayload.permanentHash} />)
    }

    const { part } = this.state
    const { afterCreation } = this.state
    return (
      <div>
        <Segment>
          <Form>
            <Form.Group widths="equal">
              <Form.Field required>
                <label>Парт-номер</label>
                <input placeholder="p/n"
                  value={part.partNumber}
                  onChange={this.handleChange("partNumber")}
                />

              </Form.Field>
              <Form.Field>
                <label>Название</label>
                <input placeholder="Название"
                  value={part.name}
                  onChange={this.handleChange("name")}
                />
              </Form.Field>
            </Form.Group>

            <Form.Field>
              <label>Описание</label>
              <input placeholder="Описание в одну строку"
                value={part.description}
                onChange={this.handleChange("description")}
              />
            </Form.Field>

            <Form.Group widths="equal">
              <Form.Field>
                <label>Производитель</label>
                <input placeholder="Производитель (если указано)"
                  value={part.manufacturer}
                  onChange={this.handleChange("manufacturer")}
                />
              </Form.Field>
              <Form.Field>
                <label>Парт-номер от производителя</label>
                <input placeholder="Парт-номер от производителя (если есть)"
                  value={part.manufacturerPartNumber}
                  onChange={this.handleChange("manufacturerPartNumber")}
                />
              </Form.Field>
            </Form.Group>

            <Form.Group inline>
              <label>После создания: </label>
              <Form.Field
                control={Radio}
                label='Перейти к созданной запчасти'
                value={MODE_WATCH_PART}
                checked={afterCreation === MODE_WATCH_PART }
                onChange={this.handleChangeRadios}
              />
              <Form.Field
                control={Radio}
                label='Остаться и продолжить добавление'
                value={MODE_ADD_NEW_PART}
                checked={afterCreation === MODE_ADD_NEW_PART}
                onChange={this.handleChangeRadios}
              />
            </Form.Group>
            <Form.Button onClick={this.handleSubmit}>Создать</Form.Button>
          </Form>
        </Segment>
      </div>

    )
  }
}

export default AddPart