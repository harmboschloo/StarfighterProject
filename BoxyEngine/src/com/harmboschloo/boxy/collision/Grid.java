package com.harmboschloo.boxy.collision;

/*
 * package com.harmboschloo.boxy.core.collision;
 * 
 * import java.util.ArrayList; import java.util.Collection; import
 * java.util.Iterator; import java.util.LinkedList; import java.util.List;
 * 
 * import com.harmboschloo.boxy.core.core.AreaF; import
 * com.harmboschloo.boxy.core.core.AreaI; import
 * com.harmboschloo.boxy.core.core.VectorF; import
 * com.harmboschloo.boxy.core.core.VectorI;
 * 
 * public class Grid<TBox extends IsBox> { private class CellData { public int
 * index; public TBox box; public CellData next;
 * 
 * public CellData(TBox box) { reset(box); }
 * 
 * public void reset(TBox box) { this.index = INVALID_INDEX; this.box = box;
 * this.next = null; } }
 * 
 * private static final int INVALID_INDEX = Integer.MAX_VALUE;
 * 
 * private VectorF cellSize = new VectorF(); private AreaF worldArea = new
 * AreaF(); private AreaI gridArea = new AreaI(); private ArrayList<CellData>
 * cellDataArray = new ArrayList<CellData>(); private ArrayList<CellData>
 * gridDataArray = new ArrayList<CellData>();
 * 
 * public Grid() { }
 * 
 * public Grid(Collection<? extends TBox> boxes, VectorF minimumCellSize,
 * VectorF worldSize) { reset(boxes, minimumCellSize, worldSize); }
 * 
 * public void reset(Collection<? extends TBox> boxes, VectorF minimumCellSize,
 * VectorF worldSize) { int x = (int) Math.floor(worldSize.x /
 * minimumCellSize.x); int y = (int) Math.floor(worldSize.y /
 * minimumCellSize.y); cellSize.set(worldSize.x / x, worldSize.y / y); assert
 * (cellSize.x >= minimumCellSize.x); assert (cellSize.y >= minimumCellSize.y);
 * worldArea.setSize(worldSize); gridArea.getSize().set(x, y);
 * 
 * // init cell data int area = gridArea.get(); cellDataArray.clear();
 * cellDataArray.ensureCapacity(area); while (cellDataArray.size() < area) {
 * cellDataArray.add(null); }
 * 
 * // init grid data gridDataArray.clear();
 * gridDataArray.ensureCapacity(boxes.size()); Iterator<? extends TBox> iterator
 * = boxes.iterator(); while (iterator.hasNext()) { cellDataArray.add(new
 * CellData(iterator.next())); } }
 * 
 * public void update() { for (CellData cellData : gridDataArray) {
 * update(cellData); } }
 * 
 * public void update(CellData cellData) { assert (cellData.box != null); if
 * (cellData.box.isActive()) { if (cellData.index == INVALID_INDEX) {
 * add(cellData); assert (cellData.index != INVALID_INDEX); } else {
 * move(cellData); } } else { if (cellData.index != INVALID_INDEX) {
 * remove(cellData); assert (cellData.index == INVALID_INDEX); assert
 * (cellData.next == null); } } }
 * 
 * private void add(CellData cellData) { assert (cellData.box != null); assert
 * (cellData.index == INVALID_INDEX); assert (cellData.next == null);
 * cellData.index = getCellIndexFromWorldPosition(cellData.box .getPosition());
 * cellData.next = cellDataArray.get(cellData.index);
 * cellDataArray.set(cellData.index, cellData); }
 * 
 * private void remove(CellData cellData) { assert (cellData.index !=
 * INVALID_INDEX); CellData current = cellDataArray.get(cellData.index); if
 * (current == cellData) { cellDataArray.set(cellData.index, cellData.next); }
 * else { while (true) { assert (current != null); if (current.next == cellData)
 * { current.next = cellData.next; break; } current = current.next; } }
 * cellData.next = null; cellData.index = INVALID_INDEX; }
 * 
 * private void move(CellData cellData) { assert (cellData.box != null); assert
 * (cellData.index != INVALID_INDEX); int index =
 * getCellIndexFromWorldPosition(cellData.box.getPosition()); if (index !=
 * cellData.index) { remove(cellData); cellData.index = index; cellData.next =
 * cellDataArray.get(index); cellDataArray.set(index, cellData); } }
 * 
 * public VectorI getGridPosition(VectorF position) { return
 * gridArea.wrap(getUnwrappedGridPosition(position)); }
 * 
 * public VectorI getUnwrappedGridPosition(VectorF position) { return new
 * VectorI((int) Math.floor(position.x / cellSize.x), (int)
 * Math.floor(position.y / cellSize.y)); }
 * 
 * VectorF getCellSize() { return cellSize; }
 * 
 * CellData getCellData(VectorI gridPosition) { return
 * cellDataArray.get(getCellIndexFromGridPosition(gridPosition)); }
 * 
 * AreaF getWorldArea() { return worldArea; }
 * 
 * AreaI getGridArea() { return gridArea; }
 * 
 * CellData getGridData(int index) { assert (index > 0); assert (index <
 * gridDataArray.size()); return gridDataArray.get(index); }
 * 
 * int getGridDataSize() { return gridDataArray.size(); }
 * 
 * private int getCellIndexFromWorldPosition(VectorF position) { return
 * getCellIndexFromGridPosition(getGridPosition(position)); }
 * 
 * private int getCellIndexFromGridPosition(VectorI gridPosition) { assert
 * (gridPosition.x > 0); assert (gridPosition.x < gridArea.getSize().x); assert
 * (gridPosition.y > 0); assert (gridPosition.y < gridArea.getSize().y); assert
 * ((gridPosition.y * gridArea.getSize().x + gridPosition.x) < cellDataArray
 * .size()); return (gridPosition.y * gridArea.getSize().x + gridPosition.x); }
 * 
 * public List<TBox> findCellData(VectorF position, VectorF maxDistance) {
 * LinkedList<TBox> list = new LinkedList<TBox>();
 * 
 * // get wrapped min/max, so min can be greater than max VectorI
 * gridPositionMin = getGridPosition(position.minus(maxDistance)); VectorI
 * gridPositionMax = getGridPosition(position.minus(maxDistance));
 * 
 * VectorI gridPosition = new VectorI(); CellData cellData = null;
 * 
 * // x loop gridPosition.x = gridPositionMin.x; while (true) { assert
 * (gridPosition.x < gridArea.getSize().x);
 * 
 * // y loop gridPosition.y = gridPositionMin.y; while (true) { assert
 * (gridPosition.y < gridArea.getSize().y); cellData =
 * getCellData(gridPosition);
 * 
 * for (; cellData != null; cellData = cellData.next) { assert (cellData.box !=
 * null); list.add(cellData.box); }
 * 
 * // final grid cell done, break y loop if (gridPosition.y ==
 * gridPositionMax.y) { break; }
 * 
 * // not done, next grid cell in y loop // make sure it is within the grid
 * ++gridPosition.y; if (gridPosition.y >= gridArea.getSize().y) {
 * gridPosition.y -= gridArea.getSize().y; } } // y loop
 * 
 * // final grid cell done, break x loop if (gridPosition.x ==
 * gridPositionMax.x) { break; }
 * 
 * // not done, next grid cell x loop // make sure it is within the grid
 * ++gridPosition.x; if (gridPosition.x >= gridArea.getSize().x) {
 * gridPosition.x -= gridArea.getSize().x; } } // x loop
 * 
 * return list; } }
 */