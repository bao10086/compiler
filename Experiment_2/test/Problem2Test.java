import java.util.ArrayList;
import java.util.Set;
import org.junit.Test;
import symbol.DFA;
import symbol.ItemCategoy;
import symbol.ItemSet;
import symbol.LR0Item;
import symbol.NonTerminalSymbol;
import symbol.Production;
import symbol.ProductionInfo;
import symbol.SymbolType;
import symbol.TerminalSymbol;

/**
 * @author ���ѱ�
 * @date 2023/4/10 15:06
 */
public class Problem2Test {
  @Test
  public void TestGetClosure() {
    // ���ս��E,,T,F��E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // ����ʽE->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // ����ʽE->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // ����ʽT->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // ����ʽT->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // ����ʽF->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // ����ʽF->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    System.out.println(item0);
  }

  @Test
  public void TestExhaustTransition() {
    // ���ս��E,,T,F��E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // ����ʽE->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // ����ʽE->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // ����ʽT->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // ����ʽT->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // ����ʽF->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // ����ʽF->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);

    Problem2.exhaustTransition(item0);
    System.out.println(Problem2.getAllItemSet());
  }

  @Test
  public void TestGetDFA() {
    // ���ս��E,,T,F��E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // ����ʽE->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // ����ʽE->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // ����ʽT->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // ����ʽT->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // ����ʽF->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // ����ʽF->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);

    DFA dfa = Problem2.getDFA(item0);
    System.out.println(Problem2.getAllItemSet());
    System.out.println(dfa);
  }

  @Test
  public void TestIsSLR1() {
    // ���ս��Z', Z
    NonTerminalSymbol Z_dot = new NonTerminalSymbol("Z'", SymbolType.NONTERMINAL);
    NonTerminalSymbol Z = new NonTerminalSymbol("Z", SymbolType.NONTERMINAL);
    // �ս��a,c,d
    TerminalSymbol a = new TerminalSymbol("a", SymbolType.TERMINAL);
    TerminalSymbol c = new TerminalSymbol("c", SymbolType.TERMINAL);
    TerminalSymbol d = new TerminalSymbol("d", SymbolType.TERMINAL);

    // ����ʽZ'->Z
    Production p0 = new Production();
    p0.addSymbolAtLast(Z);
    Z_dot.addProduction(p0);
    // ����ʽZ->d
    Production p1 = new Production();
    p1.addSymbolAtLast(d);
    Z.addProduction(p1);
    // ����ʽZ->cZa
    Production p2 = new Production();
    p2.addSymbolAtLast(c);
    p2.addSymbolAtLast(Z);
    p2.addSymbolAtLast(a);
    Z.addProduction(p2);
    // ����ʽZ->Za
    Production p3 = new Production();
    p3.addSymbolAtLast(Z);
    p3.addSymbolAtLast(a);
    Z.addProduction(p3);

    // ��FIRST����
    Set<TerminalSymbol> firstZ_dot = Problem1.firstOfSymbol(Z_dot);
    Set<TerminalSymbol> firstZ = Problem1.firstOfSymbol(Z);
    // ��FOLLOW����
    // ������#
    TerminalSymbol end = new TerminalSymbol("#", SymbolType.TERMINAL);
    Z_dot.addFollow(end);
    // ��FOLLOW����
    Problem1.followOfSymbol(Z_dot);
    Problem1.followOfSymbol(Z);
    // �������
    Z_dot.addFollowDependent();
    Z.addFollowDependent();
//    System.out.println(Z_dot);
//    System.out.println(Z);

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(Z_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);

    DFA dfa = Problem2.getDFA(item0);
    System.out.println(Problem2.getAllItemSet());
    System.out.println(dfa);

    System.out.println(Problem2.isSLR1());
  }

  @Test
  public void TestGetCell() {
    // ���ս��E,T,F��E'
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE'->E
    Production p0 = new Production();
    p0.addSymbolAtLast(E);
    E_dot.addProduction(p0);
    // ����ʽE->E+T
    Production p1 = new Production();
    p1.addSymbolAtLast(E);
    p1.addSymbolAtLast(plus);
    p1.addSymbolAtLast(T);
    E.addProduction(p1);
    // ����ʽE->T
    Production p2 = new Production();
    p2.addSymbolAtLast(T);
    E.addProduction(p2);
    // ����ʽT->T*F
    Production p4 = new Production();
    p4.addSymbolAtLast(T);
    p4.addSymbolAtLast(multi);
    p4.addSymbolAtLast(F);
    T.addProduction(p4);
    // ����ʽT->F
    Production p5 = new Production();
    p5.addSymbolAtLast(F);
    T.addProduction(p5);
    // ����ʽF->(E)
    Production p6 = new Production();
    p6.addSymbolAtLast(left);
    p6.addSymbolAtLast(E);
    p6.addSymbolAtLast(right);
    F.addProduction(p6);
    // ����ʽF->id
    Production p7 = new Production();
    p7.addSymbolAtLast(id);
    F.addProduction(p7);

    ArrayList<NonTerminalSymbol> nonTerminalSymbols = new ArrayList<>();
    nonTerminalSymbols.add(E_dot);
    nonTerminalSymbols.add(E);
    nonTerminalSymbols.add(T);
    nonTerminalSymbols.add(F);
    // ��FIRST����
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      Problem1.firstOfSymbol(symbol);
    }
    // ��FOLLOW����
    // ������#
    TerminalSymbol end = new TerminalSymbol("#", SymbolType.TERMINAL);
    E_dot.addFollow(end);
    // ��FOLLOW����
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      Problem1.followOfSymbol(symbol);
    }
    // �������
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      symbol.addFollowDependent();
      System.out.println(symbol);
    }

    // I0
    ItemSet item0 = new ItemSet();
    LR0Item lr = new LR0Item(E_dot, p0, 0, ItemCategoy.CORE);
    item0.addItem(lr);
    Problem2.getClosure(item0);
    Problem2.addItemSet(item0);
    // DFA
    DFA dfa = Problem2.getDFA(item0);
    Problem2.isSLR1();

    // ����ʽ������
    ArrayList<ProductionInfo> productionInfoTable = new ArrayList<>();
    for (NonTerminalSymbol symbol: nonTerminalSymbols) {
      productionInfoTable.addAll(Problem2.createInfo(symbol));
    }
    productionInfoTable.remove(0);

    // �﷨������
    Problem2.getCell(dfa);
    System.out.println(productionInfoTable);
    System.out.println(Problem2.getpActionCellTable());
    System.out.println(Problem2.getpGotoCellTable());
  }
}
