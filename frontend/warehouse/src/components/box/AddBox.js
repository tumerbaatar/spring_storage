import React from 'react'
import { Checkbox, Tab, Form, Radio, Divider, Table } from 'semantic-ui-react'
import { MODE_NEW_BOXES_HAVE_ADDED } from '../../actions'
import { Redirect } from 'react-router-dom'

const LETTERS = 'Буквы'
const NUMBERS = 'Числа'

class SingleBoxAddition extends React.Component {
  state = {
    name: '',
    singlePartBox: false
  }

  handleChange = (name) => {
    return (value) => {
      this.setState({ [name]: value })
    }
  }

  toggleToSingleItemOnly = () => { this.setState({ singlePartBox: !this.state.singlePartBox }) }

  handleSubmit = () => {
    this.props.handleSubmit([this.state])
  }

  render() {
    return (
      <Form>
        <Form.Field>
          <label>Название нового местопложения</label>
          <input value={this.state.name} onChange={(e) => this.handleChange("name")(e.target.value)} />
        </Form.Field>
        <Form.Field>
          <Checkbox label="Для хранения только одного предмета" checked={this.state.singlePartBox} onChange={this.toggleToSingleItemOnly} />
        </Form.Field>
        <Form.Button onClick={this.handleSubmit} >Создать</Form.Button>
      </Form>
    )
  }
}

const GeneratedRows = ({ row }) => (
  <ul>
    {row.map((r, i) => <li key={i}>{r.name}</li>)}
  </ul>
)

const generateSequence = (upperBound) => {
  let sequence = []
  if (!isNaN(parseFloat(upperBound)) && !isNaN(upperBound - 0)) {
    const end = upperBound
    let currentNumber = 1
    while (currentNumber <= end) {
      sequence.push(currentNumber)
      currentNumber++
    }
  } else {
    const end = upperBound.charCodeAt(0)
    let currentChar = "a".charCodeAt(0)
    while (currentChar <= end) {
      sequence.push(String.fromCharCode(currentChar))
      currentChar = currentChar + 1
    }
  }
  return sequence
}

class RowAddtion extends React.Component {
  state = {
    rowPrefix: 'box1',
    rowRangeType: LETTERS,
    upperBoundLetter: 'e',
    upperBoundNumber: 5,
    holdsSingleItemOnly: false,
  }

  toggleToSingleItemOnly = () => { this.setState({ holdsSingleItemOnly: !this.state.holdsSingleItemOnly }) }

  handleChange = (name) => {
    return (e, { value }) => {
      this.setState({ [name]: value })
    }
  }

  generateBoxRow = () => {
    let row = []
    let upperBound
    if (this.state.rowRangeType === LETTERS) {
      upperBound = this.state.upperBoundLetter
    } else {
      upperBound = this.state.upperBoundNumber
    }
    generateSequence(upperBound).forEach(
      symbol => row.push({
        name: this.state.rowPrefix + "-" + symbol,
        holdsSingleItemOnly: this.state.holdsSingleItemOnly
      })
    )
    return row
  }

  handleSubmit = () => {
    this.props.handleSubmit(this.generateBoxRow())
  }

  render() {
    const rowPlaces = this.generateBoxRow()
    return (
      <Form>
        <Form.Field>
          <label>Префикс названия ряда</label>
          <input value={this.state.rowPrefix} onChange={(e) => this.handleChange("rowPrefix")(e, e.target)} />
        </Form.Field>
        <Form.Group inline>
          <label>Использовать последовательность: </label>
          <Form.Field
            control={Radio}
            label='Букв'
            value={LETTERS}
            checked={this.state.rowRangeType === LETTERS}
            onChange={this.handleChange("rowRangeType")}
          />
          <Form.Field
            control={Radio}
            label='Чисел'
            value={NUMBERS}
            checked={this.state.rowRangeType === NUMBERS}
            onChange={this.handleChange("rowRangeType")}
          />
        </Form.Group>

        <Form.Group>
          <Form.Field>
            <label>{this.state.rowRangeType} продолжаются до:</label>
            <input
              type={this.state.rowRangeType === LETTERS ? "text" : "number"}
              value={this.state.rowRangeType === LETTERS ? this.state.upperBoundLetter : this.state.upperBoundNumber}
              onChange={(e) => this.handleChange(this.state.rowRangeType === LETTERS ? "upperBoundLetter" : "upperBoundNumber")(e, e.target)}
            />
          </Form.Field>
        </Form.Group>
        <Divider />
        <Form.Field>
          <label>Будет создан ряд из {rowPlaces.length} мест</label>
          <GeneratedRows row={rowPlaces} />
        </Form.Field>
        <Form.Field>
          <Checkbox label="Пометить все места как хранящие только один предмет" checked={this.state.toggleToSingleItemOnly} onChange={this.toggleToSingleItemOnly} />
        </Form.Field>
        <Form.Button onClick={this.handleSubmit}>Создать</Form.Button>
      </Form>
    )
  }
}

class GridAddition extends React.Component {
  state = {
    gridPrefix: 'box1-',

    rowRangeType: LETTERS,
    rowUpperBoundLetter: 'e',
    rowUpperBoundNumber: 5,

    rowWithCapitalLetter: false,
    rowWithLeadingZeros: false,

    divider: '',

    columnRangeType: NUMBERS,
    columnUpperBoundLetter: 'e',
    columnUpperBoundNumber: 5,

    columnWithCapitalLetter: false,
    columnWithLeadingZeros: false,

    holdsSingleItemOnly: false,
  }

  toggleToSingleItemOnly = () => { this.setState({ holdsSingleItemOnly: !this.state.holdsSingleItemOnly }) }

  handleChange = (name) => {
    return (e, { value }) => {
      this.setState({ [name]: value })
    }
  }

  handleSubmit = () => {
    this.props.handleSubmit(this.generatedGrid())
  }

  generatedGrid = () => {
    const cells = []
    let rowUpperBound
    let columnUpperBound

    if (this.state.rowRangeType === LETTERS) {
      rowUpperBound = this.state.rowUpperBoundLetter
    } else {
      rowUpperBound = this.state.rowUpperBoundNumber
    }
    if (this.state.columnRangeType === LETTERS) {
      columnUpperBound = this.state.columnUpperBoundLetter
    } else {
      columnUpperBound = this.state.columnUpperBoundNumber
    }

    const rows = generateSequence(rowUpperBound)
    const columns = generateSequence(columnUpperBound)

    rows.forEach(r =>
      columns.forEach(c =>
        cells.push({
          name: this.state.gridPrefix + r + this.state.divider + c,
          holdsSingleItemOnly: this.state.holdsSingleItemOnly
        })
      )
    )
    return cells
  }

  render() {
    return (
      <Form>
        <Form.Field>
          <label>Префикс</label>
          <input value={this.state.gridPrefix} onChange={(e) => this.handleChange("gridPrefix")(e, e.target)} />
        </Form.Field>
        <Form.Group>
          <Form.Field width="six">
            <Form.Field>
              <Form.Group grouped>
                <label>Диапазон строк из</label>
                <Form.Field
                  control={Radio}
                  label='букв'
                  value={LETTERS}
                  checked={this.state.rowRangeType === LETTERS}
                  onChange={this.handleChange("rowRangeType")}
                />
                <Form.Field
                  control={Radio}
                  label='чисел'
                  value={NUMBERS}
                  checked={this.state.rowRangeType === NUMBERS}
                  onChange={this.handleChange("rowRangeType")}
                />
              </Form.Group>
            </Form.Field>

            <Form.Field>
              <label>Продолжается до</label>
              <input
                type={this.state.rowRangeType === LETTERS ? "text" : "number"}
                value={this.state.rowRangeType === LETTERS ? this.state.rowUpperBoundLetter : this.state.rowUpperBoundNumber}
                onChange={e => this.handleChange(this.state.rowRangeType === LETTERS ? "rowUpperBoundLetter" : "rowUpperBoundNumber")(e, e.target)}
              />
            </Form.Field>

            {/* <Form.Field>
              <Checkbox label={this.state.rowRangeType === LETTERS ? "С большой буквы" : "С начальным нулём"} />
            </Form.Field> */}
          </Form.Field>


          <Form.Field width="four">
            <Form.Field>
              <label>Разделитель</label>
              <input value={this.state.divider} onChange={e => this.handleChange("divider")(e, e.target)} />
            </Form.Field>
          </Form.Field>


          <Form.Field width="six">
            <Form.Field>
              <Form.Group grouped>
                <label>Диапазон колонок из</label>
                <Form.Field
                  control={Radio}
                  label='букв'
                  value={LETTERS}
                  checked={this.state.columnRangeType === LETTERS}
                  onChange={this.handleChange("columnRangeType")}
                />
                <Form.Field
                  control={Radio}
                  label='чисел'
                  value={NUMBERS}
                  checked={this.state.columnRangeType === NUMBERS}
                  onChange={this.handleChange("columnRangeType")}
                />
              </Form.Group>
            </Form.Field>

            <Form.Field>
              <label>Продолжается до</label>
              <input
                type={this.state.columnRangeType === LETTERS ? "text" : "number"}
                value={this.state.columnRangeType === LETTERS ? this.state.columnUpperBoundLetter : this.state.columnUpperBoundNumber}
                onChange={e => this.handleChange(this.state.columnRangeType === LETTERS ? "columnUpperBoundLetter" : "columnUpperBoundNumber")(e, e.target)}
              />
            </Form.Field>

            <Form.Field>
              {/* <Checkbox
                label={this.state.columnRangeType === LETTERS ? "С большой буквы" : "С начальным нулём"}
                onChange={e => this.handleChange(this.state.columnRangeType === LETTERS ? "columnWithCapitalLetter" : " columnWithLeadingZeros")}
              /> */}
            </Form.Field>
          </Form.Field>
        </Form.Group>

        {/*-------------------------------------------------*/}

        <Divider />

        {/*-------------------------------------------------*/}

        <Form.Field>
          <label>Сгенерированные ячейки</label>
          <GeneratedTable {...this.state} />
        </Form.Field>

        {/*-------------------------------------------------*/}

        <Divider />
        <Form.Field>
          <label>Будет создана сеть из {this.generatedGrid().length} мест</label>
        </Form.Field>
        <Form.Field>
          {/* <Checkbox label="Для хранения только одного предмета" checked={this.state.holdsSingleItemOnly} onChange={this.toggleToSingleItemOnly} /> */}
          <Checkbox label="Пометить все места как хранящие только один предмет" checked={this.state.toggleToSingleItemOnly} onChange={this.toggleToSingleItemOnly} />
        </Form.Field>
        <Form.Button onClick={this.handleSubmit}>Создать</Form.Button>
      </Form>
    )
  }
}

const GeneratedTable = (args) => {
  const {
    gridPrefix,
    rowRangeType,
    rowUpperBoundLetter,
    rowUpperBoundNumber,
    columnRangeType,
    columnUpperBoundLetter,
    columnUpperBoundNumber,
    divider
  } = args

  const rowUpperBound = rowRangeType === LETTERS ? rowUpperBoundLetter : rowUpperBoundNumber
  const columnUpperBound = columnRangeType === LETTERS ? columnUpperBoundLetter : columnUpperBoundNumber
  const rowSequence = generateSequence(rowUpperBound)
  const columnSequence = generateSequence(columnUpperBound)

  return (
    <Table celled compact >
      <Table.Header></Table.Header>
      <Table.Body>
        {
          rowSequence.map(r => <Table.Row key={r}>{
            columnSequence.map(c => <Table.Cell key={c}>{
              gridPrefix + r + divider + c
            }</Table.Cell>)
          }</Table.Row>)
        }
      </Table.Body>
    </Table>
  )
}

class AddBox extends React.Component {
  handleSubmit = (state) => {
    this.props.handleSubmit(this.props.storageSlug, state)
  }

  render() {
    const { storageSlug, mode } = this.props

    if (mode && mode.name === MODE_NEW_BOXES_HAVE_ADDED) {
      return (<Redirect push to={`/storage/${storageSlug}/boxes`} />)
    }

    const panes = [
      { menuItem: 'Одна коробка', render: () => <Tab.Pane attached={false}><SingleBoxAddition handleSubmit={this.handleSubmit} /></Tab.Pane> },
      { menuItem: 'Ряд', render: () => <Tab.Pane attached={false}><RowAddtion handleSubmit={this.handleSubmit} /></Tab.Pane> },
      { menuItem: 'Сеть', render: () => <Tab.Pane attached={false}><GridAddition handleSubmit={this.handleSubmit} /></Tab.Pane> },
      { menuItem: '3D-сеть', render: () => <Tab.Pane attached={false}>3D-сеть</Tab.Pane> },
    ]
    // return <Tab menu={{ pointing: true }} activeIndex={2} panes={panes} /> // set active tab
    return <Tab menu={{ pointing: true }} panes={panes} />
  }
}

export default AddBox