import React from 'react'
import { Form, Radio } from 'semantic-ui-react'
import { groupBy } from '../../util/index'
export const stockEntriesByBoxGroup = (part) => new Map(Object.entries(groupBy(part.stockEntries, 'box')))

const BoxesInPart = ({ allBoxes, part, currentIndex, onChangeHandler }) => {
  if (part.stockEntries) {
    let entriesByBoxGroup = stockEntriesByBoxGroup(part)
    return (
      <div>
        {[...entriesByBoxGroup.entries()].map((e, index) => {
          let boxId = e[0]
          let boxesArraySize = e[1].length
          let box = allBoxes.find(b => b.id === Number(boxId))
          return (
            <Form.Field
              key={box.id}
              control={Radio}
              label={box.name + " (" + boxesArraySize + " шт)"}
              checked={currentIndex === box.id}
              onChange={onChangeHandler({
                boxId: box.id,
              })}
            />)
        })}

      </div>
    )

  }
  return null
}

export default BoxesInPart