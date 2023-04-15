package symbol;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author ���ѱ�
 * @date 2023/4/9 16:21
 */
public class ItemSet {
  /**
   * LR(0)�
   */
  // ״̬���
  private int stateId;
  // LR0 ��Ŀ��
  private ArrayList<LR0Item> pItemTable;

  private static int idNum = 0;

  public ItemSet() {
    stateId = idNum++;
    pItemTable = new ArrayList<>();
  }

  public ItemSet(int stateId) {
    this.stateId = stateId;
    pItemTable = new ArrayList<>();
  }

  public int getStateId() {
    return stateId;
  }

  public void setStateId() {
    this.stateId = idNum++;
  }

  public ArrayList<LR0Item> getpItemTable() {
    return pItemTable;
  }

  public void setpItemTable(ArrayList<LR0Item> pItemTable) {
    this.pItemTable = pItemTable;
  }

  public void addItem(LR0Item item) {
    pItemTable.add(item);
  }

  public Boolean containItem(Production production, int pos) {
    for (LR0Item item1: pItemTable) {
      if (item1.getProduction() == production && item1.getDotPosition() == pos) {
        return true;
      }
    }
    return false;
  }

  public Boolean isSame(ItemSet itemSet) {
    // ��Ŀ��Ŀ��ͬ����ز���ͬ
    if (pItemTable.size() != itemSet.getpItemTable().size()) {
      return false;
    }
    // ��ʱ��Ŀ��Ŀ��ͬ
    for (int i = 0; i < pItemTable.size(); i ++) {
      // ��itemSet�в���������Ŀ�������߱�Ȼ���ڲ���
      if (!pItemTable.get(i).equals(itemSet.getpItemTable().get(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return "ItemSet{" +
        "stateId=" + stateId +
        ", pItemTable=" + pItemTable +
        '}';
  }
}
