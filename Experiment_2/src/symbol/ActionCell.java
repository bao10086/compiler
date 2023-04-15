package symbol;

/**
 * @author ���ѱ�
 * @date 2023/4/9 16:26
 */
public class ActionCell {
  // �����꣺״̬���
  private int stateId;
  // �����꣺�ս��
  private String terminalSymbolName;
  // Action ���
  private ActionCategory type ;
  // Action �� id
  private int id;

  public ActionCell(int stateId, String terminalSymbolName, ActionCategory type, int id) {
    this.stateId = stateId;
    this.terminalSymbolName = terminalSymbolName;
    this.type = type;
    this.id = id;
  }

  @Override
  public String toString() {
    return "ActionCell{" +
        "stateId=" + stateId +
        ", terminalSymbolName='" + terminalSymbolName + '\'' +
        ", type=" + type + id +
        '}';
  }
}
