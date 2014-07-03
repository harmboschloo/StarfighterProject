package com.harmboschloo.boxy.collision;

/*package com.harmboschloo.boxy.core.collision;

 import java.util.List;
 import java.util.ListIterator;

 import com.harmboschloo.boxy.core.core.VectorF;

 public class GridContact<TBox extends IsBox, TGridBox extends IsBox> {
 public void test(TBox box, Grid<TGridBox> grid, VectorF maxDistance,
 ContactHandler<TBox, TGridBox> handler) {

 List<TGridBox> gridBoxes = grid.findCellData(box.getPosition(),
 maxDistance);

 if (gridBoxes.isEmpty()) {
 return;
 }

 ContactData<TBox, TGridBox> data = new ContactData<TBox, TGridBox>();
 data.box1 = box;

 ListIterator<TGridBox> iterator = gridBoxes.listIterator();

 while (iterator.hasNext()) {
 data.box2 = iterator.next();

 if (data.box2.getBody() == data.box1.getBody()) {
 continue;
 }

 // calculate position difference
 data.relativePosition = grid.getWorldArea().wrapDistance(
 data.box2.getPosition().minus(data.box1.getPosition()));

 // check 'aabb' x & y
 if (Math.abs(data.relativePosition.x) > maxDistance.x
 || Math.abs(data.relativePosition.y) > maxDistance.y) {
 continue;
 }

 // notify handler, if handler does not want to continue return
 if (!handler.onContact(data)) {
 return;
 }
 }
 }
 }*/
