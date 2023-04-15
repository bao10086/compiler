package symbol;

/**
 * @author ���ѱ�
 * @date 2023/4/9 15:51
 */
public class TerminalSymbol extends GrammarSymbol {
  /**
   * �ս��
   */
  // �ս���Ĵ���
  private LexemeCategory category;

  public TerminalSymbol(String name, SymbolType type) {
    super(name, type);
  }

  public LexemeCategory getCategory() {
    return category;
  }

  public void setCategory(LexemeCategory category) {
    this.category = category;
  }

  @Override
  public String toString() {
    return this.getName() + "{" +
        "\ntype=" + getType() +
        ", \ncategory=" + category +
        '}';
  }
}
