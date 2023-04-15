package symbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ���ѱ�
 * @date 2023/4/9 15:53
 */
public class NonTerminalSymbol extends GrammarSymbol{
  /**
   * ���ս��
   */

  // �йط��ս�����ɵĲ���ʽ
  private ArrayList<Production> pProductionTable;
  // ����ʽ�ĸ���
  private int numOfProduction;
  // ���ս���� FIRST ����ֵ
  private Set<TerminalSymbol> pFirstSet;
  // ���ս���� FOLLOW ����ֵ
  private Set <TerminalSymbol> pFollowSet;
  // ����ս���� FOLLOW ����ֵʱ������������ķ��ս����
  private Set <NonTerminalSymbol> pDependentSetInFollow;

  public NonTerminalSymbol(String name, SymbolType type) {
    super(name, type);
    pProductionTable = new ArrayList<>();
    numOfProduction = 0;
    pFirstSet = new HashSet<>();
    pFollowSet = new HashSet<>();
    pDependentSetInFollow = new HashSet<>();
  }

  public ArrayList<Production> getpProductionTable() {
    return pProductionTable;
  }

  public void setpProductionTable(ArrayList<Production> pProductionTable) {
    this.pProductionTable = pProductionTable;
  }

  public int getNumOfProduction() {
    return numOfProduction;
  }

  public void setNumOfProduction(int numOfProduction) {
    this.numOfProduction = numOfProduction;
  }

  public Set<TerminalSymbol> getpFirstSet() {
    return pFirstSet;
  }

  public void setpFirstSet(Set<TerminalSymbol> pFirstSet) {
    this.pFirstSet = pFirstSet;
  }

  public Set<TerminalSymbol> getpFollowSet() {
    return pFollowSet;
  }

  public void setpFollowSet(Set<TerminalSymbol> pFollowSet) {
    this.pFollowSet = pFollowSet;
  }

  public Set<NonTerminalSymbol> getpDependentSetInFollow() {
    return pDependentSetInFollow;
  }

  public void setpDependentSetInFollow(Set<NonTerminalSymbol> pDependentSetInFollow) {
    this.pDependentSetInFollow = pDependentSetInFollow;
  }

  public void removeProduction(Production production) {
    pProductionTable.remove(production);
    numOfProduction --;
  }

  public void addProduction(Production production) {
    pProductionTable.add(production);
    numOfProduction ++;
  }

  public void addDependentSetFollow(NonTerminalSymbol symbol) {
    if (symbol.getName() != getName()) {
      pDependentSetInFollow.add(symbol);
    }
  }

  public void addFollowSet(Set<TerminalSymbol> follow) {
    pFollowSet.addAll(follow);
  }

  public void addFollow(TerminalSymbol follow) {
    pFollowSet.add(follow);
  }

  public Set<TerminalSymbol> removeEpsilon() {
    Set<TerminalSymbol> first= new HashSet<>(pFirstSet);
    for (TerminalSymbol symbol: first) {
      if (symbol.getName().equals("��")) {
        first.remove(symbol);
        break;
      }
    }
    return first;
  }

  public Boolean containsEpsilon(){
    for (TerminalSymbol symbol: pFirstSet) {
      if (symbol.getName().equals("��")) {
        return true;
      }
    }
    return false;
  }

  public void addFollowDependent() {
    for (NonTerminalSymbol symbol: pDependentSetInFollow) {
      addFollowSet(symbol.pFollowSet);
    }
  }

  @Override
  public String toString() {
    String first = "{";
    for (TerminalSymbol s: pFirstSet) {
      first += s.getName() + " ";
    }
    first += "}";
    // follow����
    String follow = "{";
    for (TerminalSymbol s: pFollowSet) {
      follow += s.getName() + " ";
    }
    follow += "}";
    // follow dependent����
    String dependent = "{";
    for (NonTerminalSymbol s: pDependentSetInFollow) {
      dependent += s.getName() + " ";
    }
    dependent += "}";
    return this.getName() + "{" +
        "\npProductionTable=" + pProductionTable +
        ", \nnumOfProduction=" + numOfProduction +
        ", pFirstSet=" + first +
        ", pFollowSet=" + follow +
        ", \npDependentSetInFollow=" + dependent +
//        ", type=" + getType() +
        '}';
  }
}
