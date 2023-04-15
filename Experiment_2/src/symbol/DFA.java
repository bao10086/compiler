package symbol;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import java.util.ArrayList;

/**
 * @author ���ѱ�
 * @date 2023/4/9 16:23
 */
public class DFA {
  // ��ʼ�
  private ItemSet startupItemSet;
  // ��Ǩ�߱�
  private ArrayList<TransitionEdge> pEdgeTable;

  public DFA(ItemSet startupItemSet) {
    this.startupItemSet = startupItemSet;
    pEdgeTable = new ArrayList<>();
  }

  public void addEdges(ArrayList<TransitionEdge> edges) {
    pEdgeTable.addAll(edges);
  }

  public ItemSet findNextSet(ItemSet from, GrammarSymbol symbol) {
    for (TransitionEdge edge: pEdgeTable) {
      if (edge.getFromItemSet() == from && edge.getDriverSymbol() == symbol) {
        return edge.getToItemSet();
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "DFA{" +
        "startupItemSet=" + startupItemSet.getStateId() +
        ", \npEdgeTable=" + pEdgeTable +
        '}';
  }
}
