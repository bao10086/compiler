package symbol;

import java.util.Objects;

/**
 * @author ���ѱ�
 * @date 2023/4/9 16:20
 */
public class LR0Item {

  /**
   * LR(0)��Ŀ
   */
  // ���ս��
  private NonTerminalSymbol nonTerminalSymbol;
  // ����ʽ
  private Production production;
  // Բ���λ��
  private int dotPosition;
  // ���͡����֣�CORE(�������NONCORE(�Ǻ����
  private ItemCategoy type;

  public LR0Item(NonTerminalSymbol nonTerminalSymbol, Production production, int dotPosition,
      ItemCategoy type) {
    this.nonTerminalSymbol = nonTerminalSymbol;
    this.production = production;
    this.dotPosition = dotPosition;
    this.type = type;
  }

  public LR0Item(LR0Item item) {
    this.nonTerminalSymbol = item.getNonTerminalSymbol();
    this.production = item.getProduction();
    this.dotPosition = item.getDotPosition() + 1;
    this.type = ItemCategoy.CORE;
  }

  public NonTerminalSymbol getNonTerminalSymbol() {
    return nonTerminalSymbol;
  }

  public void setNonTerminalSymbol(NonTerminalSymbol nonTerminalSymbol) {
    this.nonTerminalSymbol = nonTerminalSymbol;
  }

  public Production getProduction() {
    return production;
  }

  public void setProduction(Production production) {
    this.production = production;
  }

  public int getDotPosition() {
    return dotPosition;
  }

  public void setDotPosition(int dotPosition) {
    this.dotPosition = dotPosition;
  }

  public ItemCategoy getType() {
    return type;
  }

  public void setType(ItemCategoy type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LR0Item item = (LR0Item) o;
    return dotPosition == item.dotPosition && Objects.equals(nonTerminalSymbol,
        item.nonTerminalSymbol) && Objects.equals(production, item.production)
        && type == item.type;
  }

  @Override
  public String toString() {
    String s = new String();
    for (GrammarSymbol symbol: production.getpBodySymbolTable()) {
      s += symbol.getName();
    }
    return "LR0Item{" +
        "nonTerminalSymbol=" + nonTerminalSymbol.getName() +
        ", production=" + s +
        ", dotPosition=" + dotPosition +
        ", type=" + type +
        '}' + '\n';
  }
}
