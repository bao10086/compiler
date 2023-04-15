import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import symbol.GrammarSymbol;
import symbol.NonTerminalSymbol;
import symbol.Production;
import symbol.SymbolType;
import symbol.TerminalSymbol;

/**
 * @author ���ѱ�
 * @date 2023/4/9 19:37
 */
public class Problem1Test {
  @Test
  public void TestNonTerminalSymbol() {
    // ���ս��A���ս��a,b
    NonTerminalSymbol A = new NonTerminalSymbol("A", SymbolType.NONTERMINAL);
    TerminalSymbol a = new TerminalSymbol("a", SymbolType.TERMINAL);
    TerminalSymbol b = new TerminalSymbol("b", SymbolType.TERMINAL);
    // ����ʽA=>Aa��A=>b
    Production p1 = new Production();
    p1.addSymbolAtLast(A);
    p1.addSymbolAtLast(a);
    Production p2 = new Production();
    p2.addSymbolAtLast(b);
    A.addProduction(p1);
    A.addProduction(p2);
    // �ж���ݹ��������ݹ�
    System.out.println("Test1_before");
    System.out.println(A);
    System.out.println("Test1_after");
    NonTerminalSymbol A_dot = Problem1.leftRecursion(A);
    System.out.println(A);
    System.out.println(A_dot);
  }

  @Test
  public void TestleftCommonFactor() {
    // ���ս��A���ս��a,b,c
    NonTerminalSymbol A = new NonTerminalSymbol("A", SymbolType.NONTERMINAL);
    TerminalSymbol a = new TerminalSymbol("a", SymbolType.TERMINAL);
    TerminalSymbol b = new TerminalSymbol("b", SymbolType.TERMINAL);
    TerminalSymbol c = new TerminalSymbol("c", SymbolType.TERMINAL);
    // ����ʽA=>ab��A=>ac
    Production p1 = new Production();
    p1.addSymbolAtLast(a);
    p1.addSymbolAtLast(b);
    Production p2 = new Production();
    p2.addSymbolAtLast(a);
    p2.addSymbolAtLast(c);
    A.addProduction(p1);
    A.addProduction(p2);
    // �ж������Ӻ���ȡ������
    System.out.println("Test2_before");
    System.out.println(A);
    System.out.println("Test2_after");
    ArrayList<NonTerminalSymbol> A_dot = Problem1.leftCommonFactor(A);
    System.out.println(A);
    System.out.println(A_dot);
  }

  @Test
  public void TestfirstOfProduction() {
    // ���ս��E,E',T,T',F
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol T_dot = new NonTerminalSymbol("T'", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE->TE'
    Production p1 = new Production();
    p1.addSymbolAtLast(T);
    p1.addSymbolAtLast(E_dot);
    E.addProduction(p1);
    // ����ʽE'->+TE'
    Production p2 = new Production();
    p2.addSymbolAtLast(plus);
    p2.addSymbolAtLast(T);
    p2.addSymbolAtLast(E_dot);
    E_dot.addProduction(p2);
    // ����ʽE'->��
    Production p3 = new Production();
    p3.addSymbolAtLast(epsilon);
    E_dot.addProduction(p3);
    // ����ʽT->FT'
    Production p4 = new Production();
    p4.addSymbolAtLast(F);
    p4.addSymbolAtLast(T_dot);
    T.addProduction(p4);
    // ����ʽT'->*FT'
    Production p5 = new Production();
    p5.addSymbolAtLast(multi);
    p5.addSymbolAtLast(F);
    p5.addSymbolAtLast(T_dot);
    T_dot.addProduction(p5);
    // ����ʽT'->��
    Production p6 = new Production();
    p6.addSymbolAtLast(epsilon);
    T_dot.addProduction(p6);
    // ����ʽF->(E)
    Production p7 = new Production();
    p7.addSymbolAtLast(left);
    p7.addSymbolAtLast(E);
    p7.addSymbolAtLast(right);
    F.addProduction(p7);
    // ����ʽF->id
    Production p8 = new Production();
    p8.addSymbolAtLast(id);
    F.addProduction(p8);

    //
    System.out.println("Test3_before");
    System.out.println(E);
    System.out.println(E_dot);
    System.out.println(T);
    System.out.println(T_dot);
    System.out.println(F);
    System.out.println("Test3_after");
    Set<TerminalSymbol> firstP1 = Problem1.firstOfProduction(p1);
    Set<TerminalSymbol> firstP2 = Problem1.firstOfProduction(p2);
    Set<TerminalSymbol> firstP3 = Problem1.firstOfProduction(p3);
    Set<TerminalSymbol> firstP4 = Problem1.firstOfProduction(p4);
    Set<TerminalSymbol> firstP5 = Problem1.firstOfProduction(p5);
    Set<TerminalSymbol> firstP6 = Problem1.firstOfProduction(p6);
    Set<TerminalSymbol> firstP7 = Problem1.firstOfProduction(p7);
    Set<TerminalSymbol> firstP8 = Problem1.firstOfProduction(p8);
    outputFirstOfProduction(firstP1, p1);
    outputFirstOfProduction(firstP2, p2);
    outputFirstOfProduction(firstP3, p3);
    outputFirstOfProduction(firstP4, p4);
    outputFirstOfProduction(firstP5, p5);
    outputFirstOfProduction(firstP6, p6);
    outputFirstOfProduction(firstP7, p7);
    outputFirstOfProduction(firstP8, p8);
  }

  public void outputFirstOfProduction(Set<TerminalSymbol> first, Production p) {
    System.out.print(p + "\t{");
    for (TerminalSymbol symbol: first) {
      System.out.print(symbol.getName()+ " ");
    }
    System.out.print("}");
  }

  @Test
  public void TestfirstOfSymbol() {
    // ���ս��E,E',T,T',F
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol T_dot = new NonTerminalSymbol("T'", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE->TE'
    Production p1 = new Production();
    p1.addSymbolAtLast(T);
    p1.addSymbolAtLast(E_dot);
    E.addProduction(p1);
    // ����ʽE'->+TE'
    Production p2 = new Production();
    p2.addSymbolAtLast(plus);
    p2.addSymbolAtLast(T);
    p2.addSymbolAtLast(E_dot);
    E_dot.addProduction(p2);
    // ����ʽE'->��
    Production p3 = new Production();
    p3.addSymbolAtLast(epsilon);
    E_dot.addProduction(p3);
    // ����ʽT->FT'
    Production p4 = new Production();
    p4.addSymbolAtLast(F);
    p4.addSymbolAtLast(T_dot);
    T.addProduction(p4);
    // ����ʽT'->*FT'
    Production p5 = new Production();
    p5.addSymbolAtLast(multi);
    p5.addSymbolAtLast(F);
    p5.addSymbolAtLast(T_dot);
    T_dot.addProduction(p5);
    // ����ʽT'->��
    Production p6 = new Production();
    p6.addSymbolAtLast(epsilon);
    T_dot.addProduction(p6);
    // ����ʽF->(E)
    Production p7 = new Production();
    p7.addSymbolAtLast(left);
    p7.addSymbolAtLast(E);
    p7.addSymbolAtLast(right);
    F.addProduction(p7);
    // ����ʽF->id
    Production p8 = new Production();
    p8.addSymbolAtLast(id);
    F.addProduction(p8);

    //
    System.out.println("Test4_before");
    System.out.println(E);
    System.out.println(E_dot);
    System.out.println(T);
    System.out.println(T_dot);
    System.out.println(F);
    System.out.println("Test4_after");
    Set<TerminalSymbol> firstE = Problem1.firstOfSymbol(E);
    Set<TerminalSymbol> firstE_dot = Problem1.firstOfSymbol(E_dot);
    Set<TerminalSymbol> firstT = Problem1.firstOfSymbol(T);
    Set<TerminalSymbol> firstT_dot = Problem1.firstOfSymbol(T_dot);
    Set<TerminalSymbol> firstF = Problem1.firstOfSymbol(F);
    outputFirstOfSymbol(E, firstE);
    outputFirstOfSymbol(E_dot, firstE_dot);
    outputFirstOfSymbol(T, firstT);
    outputFirstOfSymbol(T_dot, firstT_dot);
    outputFirstOfSymbol(F, firstF);
  }

  public void outputFirstOfSymbol(NonTerminalSymbol s, Set<TerminalSymbol> first) {
    System.out.print(s.getName() + " {");
    for (TerminalSymbol symbol: first) {
      System.out.print(symbol.getName()+ " ");
    }
    System.out.print("}\n");

  }

  @Test
  public void TestFollowOfSymbol() {
    // ���ս��E,E',T,T',F
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol T_dot = new NonTerminalSymbol("T'", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE->TE'
    Production p1 = new Production();
    p1.addSymbolAtLast(T);
    p1.addSymbolAtLast(E_dot);
    E.addProduction(p1);
    // ����ʽE'->+TE'
    Production p2 = new Production();
    p2.addSymbolAtLast(plus);
    p2.addSymbolAtLast(T);
    p2.addSymbolAtLast(E_dot);
    E_dot.addProduction(p2);
    // ����ʽE'->��
    Production p3 = new Production();
    p3.addSymbolAtLast(epsilon);
    E_dot.addProduction(p3);
    // ����ʽT->FT'
    Production p4 = new Production();
    p4.addSymbolAtLast(F);
    p4.addSymbolAtLast(T_dot);
    T.addProduction(p4);
    // ����ʽT'->*FT'
    Production p5 = new Production();
    p5.addSymbolAtLast(multi);
    p5.addSymbolAtLast(F);
    p5.addSymbolAtLast(T_dot);
    T_dot.addProduction(p5);
    // ����ʽT'->��
    Production p6 = new Production();
    p6.addSymbolAtLast(epsilon);
    T_dot.addProduction(p6);
    // ����ʽF->(E)
    Production p7 = new Production();
    p7.addSymbolAtLast(left);
    p7.addSymbolAtLast(E);
    p7.addSymbolAtLast(right);
    F.addProduction(p7);
    // ����ʽF->id
    Production p8 = new Production();
    p8.addSymbolAtLast(id);
    F.addProduction(p8);

    //
    System.out.println("Test4_before");
    System.out.println(E);
    System.out.println(E_dot);
    System.out.println(T);
    System.out.println(T_dot);
    System.out.println(F);
    System.out.println("Test4_after_FOLLOW");
    // ��FIRST����
    Set<TerminalSymbol> firstE = Problem1.firstOfSymbol(E);
    Set<TerminalSymbol> firstE_dot = Problem1.firstOfSymbol(E_dot);
    Set<TerminalSymbol> firstT = Problem1.firstOfSymbol(T);
    Set<TerminalSymbol> firstT_dot = Problem1.firstOfSymbol(T_dot);
    Set<TerminalSymbol> firstF = Problem1.firstOfSymbol(F);
    // ��FOLLOW����
    // ������#
    TerminalSymbol end = new TerminalSymbol("#", SymbolType.TERMINAL);
    E.addFollow(end);
    // ��FOLLOW����
    Problem1.followOfSymbol(E);
    Problem1.followOfSymbol(E_dot);
    Problem1.followOfSymbol(T);
    Problem1.followOfSymbol(T_dot);
    Problem1.followOfSymbol(F);
    System.out.println(E);
    System.out.println(E_dot);
    System.out.println(T);
    System.out.println(T_dot);
    System.out.println(F);

    // �������
    System.out.println("Test4_after_DEPENDENT");
    E.addFollowDependent();
    E_dot.addFollowDependent();
    T.addFollowDependent();
    T_dot.addFollowDependent();
    F.addFollowDependent();
    System.out.println(E);
    System.out.println(E_dot);
    System.out.println(T);
    System.out.println(T_dot);
    System.out.println(F);
  }

  @Test
  public void TestIsLL1() {
    // ���ս��E,E',T,T',F
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol T_dot = new NonTerminalSymbol("T'", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE->TE'
    Production p1 = new Production();
    p1.addSymbolAtLast(T);
    p1.addSymbolAtLast(E_dot);
    E.addProduction(p1);
    // ����ʽE'->+TE'
    Production p2 = new Production();
    p2.addSymbolAtLast(plus);
    p2.addSymbolAtLast(T);
    p2.addSymbolAtLast(E_dot);
    E_dot.addProduction(p2);
    // ����ʽE'->��
    Production p3 = new Production();
    p3.addSymbolAtLast(epsilon);
    E_dot.addProduction(p3);
    // ����ʽT->FT'
    Production p4 = new Production();
    p4.addSymbolAtLast(F);
    p4.addSymbolAtLast(T_dot);
    T.addProduction(p4);
    // ����ʽT'->*FT'
    Production p5 = new Production();
    p5.addSymbolAtLast(multi);
    p5.addSymbolAtLast(F);
    p5.addSymbolAtLast(T_dot);
    T_dot.addProduction(p5);
    // ����ʽT'->��
    Production p6 = new Production();
    p6.addSymbolAtLast(epsilon);
    T_dot.addProduction(p6);
    // ����ʽF->(E)
    Production p7 = new Production();
    p7.addSymbolAtLast(left);
    p7.addSymbolAtLast(E);
    p7.addSymbolAtLast(right);
    F.addProduction(p7);
    // ����ʽF->id
    Production p8 = new Production();
    p8.addSymbolAtLast(id);
    F.addProduction(p8);

    // ��FIRST����
    Set<TerminalSymbol> firstE = Problem1.firstOfSymbol(E);
    Set<TerminalSymbol> firstE_dot = Problem1.firstOfSymbol(E_dot);
    Set<TerminalSymbol> firstT = Problem1.firstOfSymbol(T);
    Set<TerminalSymbol> firstT_dot = Problem1.firstOfSymbol(T_dot);
    Set<TerminalSymbol> firstF = Problem1.firstOfSymbol(F);
    // ��FOLLOW����
    // ������#
    TerminalSymbol end = new TerminalSymbol("#", SymbolType.TERMINAL);
    E.addFollow(end);
    // ��FOLLOW����
    Problem1.followOfSymbol(E);
    Problem1.followOfSymbol(E_dot);
    Problem1.followOfSymbol(T);
    Problem1.followOfSymbol(T_dot);
    Problem1.followOfSymbol(F);

    // �������
    E.addFollowDependent();
    E_dot.addFollowDependent();
    T.addFollowDependent();
    T_dot.addFollowDependent();
    F.addFollowDependent();

    System.out.println("Test5_JUDGE");
    System.out.println("E:" + Problem1.isLL1(E));
    System.out.println("E_dot:" + Problem1.isLL1(E_dot));
    System.out.println("T:" + Problem1.isLL1(T));
    System.out.println("T_dot:" + Problem1.isLL1(T_dot));
    System.out.println("F:" + Problem1.isLL1(F));
  }

  @Test
  public void TestParseTable() {
    // ���ս��E,E',T,T',F
    NonTerminalSymbol E = new NonTerminalSymbol("E", SymbolType.NONTERMINAL);
    NonTerminalSymbol E_dot = new NonTerminalSymbol("E'", SymbolType.NONTERMINAL);
    NonTerminalSymbol T = new NonTerminalSymbol("T", SymbolType.NONTERMINAL);
    NonTerminalSymbol T_dot = new NonTerminalSymbol("T'", SymbolType.NONTERMINAL);
    NonTerminalSymbol F = new NonTerminalSymbol("F", SymbolType.NONTERMINAL);
    // �ս��+,*,(,),id,��
    TerminalSymbol plus = new TerminalSymbol("+", SymbolType.TERMINAL);
    TerminalSymbol multi = new TerminalSymbol("*", SymbolType.TERMINAL);
    TerminalSymbol left = new TerminalSymbol("(", SymbolType.TERMINAL);
    TerminalSymbol right = new TerminalSymbol(")", SymbolType.TERMINAL);
    TerminalSymbol id = new TerminalSymbol("id", SymbolType.TERMINAL);
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ����ʽE->TE'
    Production p1 = new Production();
    p1.addSymbolAtLast(T);
    p1.addSymbolAtLast(E_dot);
    E.addProduction(p1);
    // ����ʽE'->+TE'
    Production p2 = new Production();
    p2.addSymbolAtLast(plus);
    p2.addSymbolAtLast(T);
    p2.addSymbolAtLast(E_dot);
    E_dot.addProduction(p2);
    // ����ʽE'->��
    Production p3 = new Production();
    p3.addSymbolAtLast(epsilon);
    E_dot.addProduction(p3);
    // ����ʽT->FT'
    Production p4 = new Production();
    p4.addSymbolAtLast(F);
    p4.addSymbolAtLast(T_dot);
    T.addProduction(p4);
    // ����ʽT'->*FT'
    Production p5 = new Production();
    p5.addSymbolAtLast(multi);
    p5.addSymbolAtLast(F);
    p5.addSymbolAtLast(T_dot);
    T_dot.addProduction(p5);
    // ����ʽT'->��
    Production p6 = new Production();
    p6.addSymbolAtLast(epsilon);
    T_dot.addProduction(p6);
    // ����ʽF->(E)
    Production p7 = new Production();
    p7.addSymbolAtLast(left);
    p7.addSymbolAtLast(E);
    p7.addSymbolAtLast(right);
    F.addProduction(p7);
    // ����ʽF->id
    Production p8 = new Production();
    p8.addSymbolAtLast(id);
    F.addProduction(p8);

    // ��FIRST����
    Set<TerminalSymbol> firstE = Problem1.firstOfSymbol(E);
    Set<TerminalSymbol> firstE_dot = Problem1.firstOfSymbol(E_dot);
    Set<TerminalSymbol> firstT = Problem1.firstOfSymbol(T);
    Set<TerminalSymbol> firstT_dot = Problem1.firstOfSymbol(T_dot);
    Set<TerminalSymbol> firstF = Problem1.firstOfSymbol(F);
    // ��FOLLOW����
    // ������#
    TerminalSymbol end = new TerminalSymbol("#", SymbolType.TERMINAL);
    E.addFollow(end);
    // ��FOLLOW����
    Problem1.followOfSymbol(E);
    Problem1.followOfSymbol(E_dot);
    Problem1.followOfSymbol(T);
    Problem1.followOfSymbol(T_dot);
    Problem1.followOfSymbol(F);

    // �������
    E.addFollowDependent();
    E_dot.addFollowDependent();
    T.addFollowDependent();
    T_dot.addFollowDependent();
    F.addFollowDependent();

    ArrayList<NonTerminalSymbol> pNonTerminalSymbolTable = new ArrayList<>();
    pNonTerminalSymbolTable.add(E);
    pNonTerminalSymbolTable.add(E_dot);
    pNonTerminalSymbolTable.add(T);
    pNonTerminalSymbolTable.add(T_dot);
    pNonTerminalSymbolTable.add(F);

    System.out.println(Problem1.parseTable(pNonTerminalSymbolTable));
  }
}
