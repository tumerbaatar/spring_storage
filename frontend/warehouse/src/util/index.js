
export var groupBy = function (xs, key) {
  return xs.reduce(function (rv, x) {
    (rv[x[key]] = rv[x[key]] || []).push(x);
    return rv;
  }, {});
}

export const defineBox = (boxes, id) => {
  if (boxes && id) {
    const box = boxes.find(b => b.id === id)
    return box;
  }
  return null
}

export const initialBoxInStockEntries = (part, boxes) => {
  const boxKeys = Object.keys(groupBy(part.stockEntries, 'box'))
  const currentBox = boxKeys.length !== 0 && boxes ? defineBox(boxes, Number(boxKeys[0])) : null
  return currentBox
}
