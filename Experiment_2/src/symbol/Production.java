package symbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ���ѱ�
 * @date 2023/4/9 15:55
 */
public class Production {
  /**
   * ����ʽ
   */
  // ����ʽ��ţ����ʶ����
  private int productionId;
  // ����ʽ���а������ķ�������
  private int bodySize;
  // ����ʽ���а������ķ���
  private ArrayList<GrammarSymbol> pBodySymbolTable;
  // ����ʽ�� FIRST ����ֵ
  private Set<TerminalSymbol> pFirstSet;

  public static int idNum = 0;

  public Production() {
    productionId = idNum++;
    bodySize = 0;
    pBodySymbolTable = new ArrayList<>();
    pFirstSet = new HashSet<>();
  }

  public int getProductionId() {
    return productionId;
  }

  public void setProductionId(int productionId) {
    this.productionId = productionId;
  }

  public int getBodySize() {
    return bodySize;
  }

  public void setBodySize(int bodySize) {
    this.bodySize = bodySize;
  }

  public ArrayList<GrammarSymbol> getpBodySymbolTable() {
    return pBodySymbolTable;
  }

  public void setpBodySymbolTable(ArrayList<GrammarSymbol> pBodySymbolTable) {
    this.pBodySymbolTable = pBodySymbolTable;
  }

  public Set<TerminalSymbol> getpFirstSet() {
    return pFirstSet;
  }

  public void setpFirstSet(Set<TerminalSymbol> pFirstSet) {
    this.pFirstSet = pFirstSet;
  }

  public void addSymbolAtLast(GrammarSymbol symbol) {
    pBodySymbolTable.add(symbol);
    bodySize ++;
  }

  public void removeFirstSymbol(GrammarSymbol symbol) {
    pBodySymbolTable.remove(symbol);
    bodySize --;
  }

  public Boolean isEpsilon() {
    if (bodySize == 1 && pBodySymbolTable.get(0).getName().equals("��")) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public String toString() {
    String s = new String();
    for (GrammarSymbol symbol: pBodySymbolTable) {
      s += symbol.getName();
    }
    return "{" +
        "productionId=" + productionId +
        ", bodySize=" + bodySize +
        ", pBodySymbolTable=" + s +
//        ", pFirstSet=" + pFirstSet +
        '}';
  }
}
//����ʽ���У��ķ���֮�䶼���������㣬���Ҳ�Ϳ�ʡȥ������������Ѳ���ʽ
//�е�ĳ���ķ������� pBodySymbolTable ֮ǰ��Ҫǿ������ת������� GrammarSymbol *��
//�͡���������ת��û�����⡣��Ϊ TerminalSymbol �� NonTerminalSymbol ����
//GrammarSymbol �����ࡣ��ʹ�� pBodySymbolTable ��Ԫ��ʱ��������Ա���� type ��
//ֵ�����Ϊ NONTERMINAL������ǿ������ת������� NonTerminalSymbol *���͡���
//��Ϊ TERMINAL������ǿ������ת������� TerminalSymbol *���͡�
