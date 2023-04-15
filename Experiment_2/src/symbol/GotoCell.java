package symbol;

/**
 * @author ���ѱ�
 * @date 2023/4/9 16:27
 */
public class GotoCell {
  // �����꣺״̬���
  private int stateId;
  // �����꣺���ս��
  private String nonTerminalSymbolName;
  // ��һ״̬
  private int nextStateId;

  public GotoCell(int stateId, String nonTerminalSymbolName, int nextStateId) {
    this.stateId = stateId;
    this.nonTerminalSymbolName = nonTerminalSymbolName;
    this.nextStateId = nextStateId;
  }

  @Override
  public String toString() {
    return "\nGotoCell{" +
        "stateId=" + stateId +
        ", nonTerminalSymbolName='" + nonTerminalSymbolName + '\'' +
        ", nextStateId=" + nextStateId +
        '}';
  }
}
