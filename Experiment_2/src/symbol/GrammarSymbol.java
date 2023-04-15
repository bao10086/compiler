package symbol;

/**
 * @author ���ѱ�
 * @date 2023/4/9 15:42
 */
public class GrammarSymbol {
  /**
   * �ķ���
   */
  // ����
  private String name;
  // �ķ��������
  private SymbolType type;

  public GrammarSymbol(String name, SymbolType type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SymbolType getType() {
    return type;
  }

  public void setType(SymbolType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "GrammarSymbol{" +
        "name='" + name + '\'' +
        ", type=" + type +
        '}';
  }
}
