import React from 'react'
import { Grid, Container, Segment, Header, Form, Icon, Divider, Tab, Image } from 'semantic-ui-react'
import { defineBox } from '../../util/index'
import AddStockTab from './stock_operations/AddStockTab'
import MoveStockTab from './stock_operations/MoveStockTab'
import RemoveStockTab from './stock_operations/RemoveStockTab'
import Dropzone from 'react-dropzone'
import {server} from '../../index'


const PartInfo = (props) => {

  const panes = [
    { menuItem: 'Информация', render: () => <Tab.Pane><PartInfoTab {...props} /></Tab.Pane> },
    { menuItem: 'Добавить на хранение', render: () => <Tab.Pane><AddStockTab {...props} /></Tab.Pane> },
    { menuItem: 'Переместить', render: () => <Tab.Pane><MoveStockTab {...props} /></Tab.Pane> },
    { menuItem: 'Убрать', render: () => <Tab.Pane><RemoveStockTab {...props} /></Tab.Pane> },
  ]

  if (!props.part) {
    props.fetchPartByHash(props.match.params.partHash)
    return <div>Part is null now</div>
  }

  const part = props.parts.find(p => p.id === props.part.id)

  return (
    <Grid stackable>
      <Grid.Row>
        <Grid.Column computer={4}>
          <Dropzone
            disableClick
            accept="image/jpeg, image/png"
            onDrop={(files) => props.handleImageUpload(part.id, files)}
            style={{ position: "relative" }}
          >
            <Image
              size="tiny"
              floated="right"
              src={`${server}` + part.images[0]}
              onClick={e => console.log(part.id, "image clicked")}
            />
          </Dropzone>
        </Grid.Column>
        <Grid.Column computer={12}>
          <Header>
            {part.name}
            <Header.Subheader>
              {part.description}
            </Header.Subheader>
          </Header>
        </Grid.Column>
      </Grid.Row>

      <Grid.Row>
        <Grid.Column>
          <Container>
            <Tab menu={{ color: 'red', fluid: true, vertical: true, tabular: 'right' }} panes={panes} />
          </Container>
        </Grid.Column>
      </Grid.Row>
    </Grid>
  )
}

class PartInfoTab extends React.Component {
  render() {
    const part = this.props.part
    const boxes = this.props.boxes

    return (
      <Segment>

        <Form>
          <Form.Group widths="equal">
            <Form.Field>
              <label>Производитель</label>
              <span>{part.manufacturer} <a><Icon name="edit" /></a></span>
            </Form.Field>
            <Form.Field>
              <label>MPN</label>
              <span>{part.manufacturerPartNumber} <a><Icon name="edit" /></a></span>
            </Form.Field>
            <Form.Field>
              <label>P/N</label>
              <span>{part.partNumber} <a><Icon name="edit" /></a></span>
            </Form.Field>
            <Form.Field>
              <label>Где лежит?</label>
              <span>{part.location ? defineBox(boxes, part.location).name : "--"}  </span>
            </Form.Field>
          </Form.Group>
        </Form>
        <Divider />
      </Segment>
    )
  }
}

export default PartInfo