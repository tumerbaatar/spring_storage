import React from 'react'
import { Table, Loader, Checkbox } from 'semantic-ui-react'

const BoxList = (props) => {
  const { boxes, handleToggleBoxCheckbox } = props
  // const storageSlug = props.match.params.storageSlug
  if (boxes.length === 0) {
    return <Loader active inline='centered' />
  }

  return (
    <Table size="small" compact>
      <Table.Header>
        <Table.Row>
          <Table.HeaderCell />
          <Table.HeaderCell>Место</Table.HeaderCell>
          <Table.HeaderCell>Описание</Table.HeaderCell>
        </Table.Row>
      </Table.Header>
      <Table.Body>
        {boxes.map(b => {
          return (
            <Table.Row key={b.id}>
              <Table.Cell collapsing><Checkbox onChange={handleToggleBoxCheckbox(b.id)} /></Table.Cell>
              <Table.Cell>{b.name}</Table.Cell>
              <Table.Cell>{b.id}</Table.Cell>
            </Table.Row>
          )
        }).reverse()}
      </Table.Body>
    </Table>
  )
}

export default BoxList