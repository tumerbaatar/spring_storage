import React from 'react'
import { Table, Loader, Checkbox, Image } from 'semantic-ui-react'
import { Link } from 'react-router-dom'
import { defineBox } from '../../util/index'
import Dropzone from 'react-dropzone'
import { SERVER_NAME, PART_PAGE_INCOMPLETE } from '../../constants/url';

const PartList = (props) => {
  const { parts, boxes, handleModeWatchPart, handleTogglePartCheckBox, handleImageUpload } = props
  if (!parts) {
    return <Loader active inline='centered' />
  }
  return (
    <Table>
      <Table.Header>
        <Table.Row>
          <Table.HeaderCell />
          <Table.HeaderCell />
          <Table.HeaderCell>id</Table.HeaderCell>
          <Table.HeaderCell>Название</Table.HeaderCell>
          <Table.HeaderCell>Ссылка</Table.HeaderCell>
          <Table.HeaderCell>Где лежит?</Table.HeaderCell>
        </Table.Row>
      </Table.Header>
      <Table.Body>
        {parts.map(p => {
          return (
            <Table.Row key={p.id}>
              <Table.Cell collapsing>
                <Checkbox onChange={handleTogglePartCheckBox(p.id)} />
              </Table.Cell>

              <Table.Cell collapsing>
                <Dropzone
                  disableClick
                  accept="image/jpeg, image/png"
                  onDrop={(files) => handleImageUpload(p.id, files)}
                  style={{ position: "relative" }}
                >
                  <Image size="tiny" rounded src={SERVER_NAME + p.images[0]} onClick={e => console.log(p.id, "image clicked")} />
                </Dropzone>
              </Table.Cell>

              <Table.Cell>{p.id}</Table.Cell>
              <Table.Cell>{p.name}</Table.Cell>
              <Table.Cell>
                <Link to={PART_PAGE_INCOMPLETE + p.permanentHash} onClick={handleModeWatchPart(p)}>
                  {p.permanentHash}
                </Link>
              </Table.Cell>
              <Table.Cell>{defineBox(boxes, p.location) && defineBox(boxes, p.location).name}</Table.Cell>
            </Table.Row>
          )
        })}
      </Table.Body>
    </Table>
  )
}

export default PartList