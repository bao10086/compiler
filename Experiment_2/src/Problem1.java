import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import symbol.Cell;
import symbol.GrammarSymbol;
import symbol.NonTerminalSymbol;
import symbol.Production;
import symbol.SymbolType;
import symbol.TerminalSymbol;

/**
 * @author ���ѱ�
 * @date 2023/4/9 16:35
 */
public class Problem1 {
  public static NonTerminalSymbol leftRecursion(NonTerminalSymbol nonTerminalSymbol) {
    // ��ȡ����ʽ�󲿵ķ��ս��A
    String name = nonTerminalSymbol.getName();
    // ����A=>A��������
    ArrayList<Production> left = new ArrayList<>();
    // ����A=>�µ�����
    ArrayList<Production> constant = new ArrayList<>();
    // �������в���ʽ
    for (Production production: nonTerminalSymbol.getpProductionTable()) {
      // ����ʽ�Ҳ���ͷ���ķ�������ΪA���������left;�������constant
      if (production.getpBodySymbolTable().get(0).getName().equals(name)) {
        left.add(production);
      }
      else {
        constant.add(production);
      }
    }
    // leftΪ�գ�˵����������ݹ�
    if (left.isEmpty()) {
      System.out.println("��������ݹ�");
      return null;
    }
    System.out.println("������ݹ�");
    // �½�һ�����ս��A��
    NonTerminalSymbol S_dot = new NonTerminalSymbol(nonTerminalSymbol.getName()+'\'', SymbolType.NONTERMINAL);
    // ��A=>�±仯ΪA=>��A'
    for (Production production: constant) {
      production.addSymbolAtLast(S_dot);
    }
    // ��A=>A���仯ΪA'=>��A'
    for (Production production: left) {
      // ���ս��A�Ƴ�����ʽ
      nonTerminalSymbol.removeProduction(production);
      // ����ʽͷ��ȥ��A
      production.removeFirstSymbol(nonTerminalSymbol);
      // ����ʽβ������A��
      production.addSymbolAtLast(S_dot);
      // ��ӵ����ս��A���Ĳ���ʽ������
      S_dot.addProduction(production);
    }
    // ���A=>��
    Production production = new Production();
    GrammarSymbol epsilon = new GrammarSymbol("��", SymbolType.NULL);
    production.addSymbolAtLast(epsilon);
    // ������ս��A���Ĳ���ʽ������
    S_dot.addProduction(production);
    return S_dot;
  }

  public static ArrayList<NonTerminalSymbol> leftCommonFactor(NonTerminalSymbol symbol) {
    Map<GrammarSymbol, ArrayList<Production>> head = new HashMap<>();
    Boolean flag = false;
    for (Production production: symbol.getpProductionTable()) {
      GrammarSymbol s = production.getpBodySymbolTable().get(0);
      // �����ڸ�ͷ����Ϣ�����ʾ���һ�γ��֣�����map��
      if (head.get(s) == null) {
        ArrayList<Production> p = new ArrayList<>();
        p.add(production);
        head.put(s, p);
      }
      // ˵���Ѿ����ֹ�����ʱ����������
      else {
        head.get(s).add(production);
        flag = true;
      }
    }
    if (flag == false) {
      System.out.println("������������");
      return null;
    }
    System.out.println("����������");
    ArrayList<NonTerminalSymbol> ans = new ArrayList<>();
    // ��������ͷ����Ϣ
    for (GrammarSymbol key: head.keySet()) {
      // �жϾ�����ͬͷ����Ϣ�ĸ���
      if (head.get(key).size() > 1) {
        // �½�һ�����ս��A��
        NonTerminalSymbol A_dot = new NonTerminalSymbol(symbol.getName()+"\'", SymbolType.NONTERMINAL);
        // �½�һ������ʽA->key A'
        Production p = new Production();
        p.addSymbolAtLast(key);
        p.addSymbolAtLast(A_dot);
        symbol.addProduction(p);
        // ����������
        for (Production production: head.get(key)) {
          // ɾ��A->key �����е�key
          production.removeFirstSymbol(key);
          // ɾ��A->�����Ĳ���ʽ��
          symbol.removeProduction(production);
          // ����ʽ��ΪA��->����
          A_dot.addProduction(production);
        }
        // ����µķ��ս��
        ans.add(A_dot);
      }
    }
    return ans;
  }

  public static Set<TerminalSymbol> firstOfProduction(Production production) {
    // ���Ƿ����
    Boolean nullStand = true;
    int i = 0;
    Set<TerminalSymbol> pFirstSet = new HashSet<>();
    ArrayList<GrammarSymbol> pBodySymbolTable = production.getpBodySymbolTable();
    // �½�һ�����ķ��������ж�
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ��ǰ���ķ���FIRST��������ʱ
    while(nullStand && i < production.getBodySize()) {
      // ��ȡ��ǰ�ķ�����FIRST
      Set<TerminalSymbol> firstY = firstOfSymbol(pBodySymbolTable.get(i));
      // �жϵ�ǰ�ķ���FIRST�Ƿ������
      if (firstY.contains(epsilon)) {
        // ��ת����һ���ķ�����ȥ����
        i ++;
        firstY.remove(epsilon);
      }
      else {
        // �Ų��ٳ���
        nullStand = false;
      }
      // �ѵ�ǰ�ķ�����FIRST��������
      pFirstSet.addAll(firstY);
    }
    // ����������Ƶ����ţ���FIRST�����а�����
    if (nullStand && i == production.getBodySize()) {
      pFirstSet.add(epsilon);
    }
    // ���ò���ʽ��FIRST����
    production.setpFirstSet(pFirstSet);
    return pFirstSet;
  }

  public static Set<TerminalSymbol> firstOfSymbol(GrammarSymbol symbol) {
    Set<TerminalSymbol> ans = new HashSet<>();
    // ��ǰ�ķ���Ϊ�ս����ţ���ֱ�ӷ����䱾��
    if (symbol.getType() == SymbolType.TERMINAL || symbol.getType() == SymbolType.NULL) {
      ans.add((TerminalSymbol) symbol);
      return ans;
    }
    // ��ǰ�ķ���Ϊ���ս��������ÿ������ʽ
    for (Production production: ((NonTerminalSymbol)symbol).getpProductionTable()) {
      if (production.getpBodySymbolTable().get(0) == symbol) {
        continue;
      }
      // ��ÿ������ʽ����FIRST��
      for (TerminalSymbol s: firstOfProduction(production)) {
        // ��δ������ս������FIRST����
        if (!ans.contains(s)) {
          ans.add(s);
        }
      }
    }
    // ����FIRST���ս����FIRST����
    ((NonTerminalSymbol) symbol).setpFirstSet(ans);
    return ans;
  }

  public static void followOfSymbol(NonTerminalSymbol symbol) {
    // �½���
    TerminalSymbol epsilon = new TerminalSymbol("��", SymbolType.NULL);
    // ��������ʽ
    for (Production production: symbol.getpProductionTable()) {
      // ����ʽ�ķ�������
      int size = production.getBodySize();
      // ��ȡ���һ���ķ���
      GrammarSymbol Yn = production.getpBodySymbolTable().get(size - 1);
      // symbol->�ţ���ʾΪ�գ������ò���ʽ
      if (Yn.getName().equals("��")) {
        continue;
      }
      // ���һ���ķ���Ϊ���ս������FOLLOW����������symbol��FOLLOW����
      if (Yn.getType() == SymbolType.NONTERMINAL) {
        ((NonTerminalSymbol) Yn).addDependentSetFollow(symbol);
      }
      // ���Ƿ�����������һ����ʼ��ǰ�ң�
      Boolean nullStand = true;
      // �ӵ����ڶ�����ʼ
      int i = size-2, j = size-1;
      while (i >= 0) {
        GrammarSymbol Yi = production.getpBodySymbolTable().get(i);
        // ��ǰ�ķ���Ϊ���ս��
        if (Yi.getType() == SymbolType.NONTERMINAL) {
          // �������FIRST���������ŵķ��ս��
          for (int k = i+1; k <= j; k ++) {
            // ����k���ķ���Ϊ�ս���������������FOLLOW���ϼ���
            if (production.getpBodySymbolTable().get(k).getType() == SymbolType.TERMINAL) {
              ((NonTerminalSymbol)Yi).addFollow((TerminalSymbol) production.getpBodySymbolTable().get(k));
              nullStand = false;
            }
            else {
              NonTerminalSymbol Yk = (NonTerminalSymbol)production.getpBodySymbolTable().get(k);
              Set<TerminalSymbol> firstYk = Yk.removeEpsilon();
              // ����ǰ�ս���ĺ������ս����FIRST���ϲ������ţ�˵���޷����������nullStand��0
              if (!Yk.containsEpsilon()) {
                nullStand = false;
              }
              // ����FIRST����-{��}������ս����FOLLOW����
              ((NonTerminalSymbol)Yi).addFollowSet(firstYk);
            }
          }
          // �����ǰ�ķ�����FIRST���ϲ������ţ�˵��FOLLOW�����޷������������j�޸�Ϊ��ǰi
          if (!((NonTerminalSymbol)Yi).getpFirstSet().contains(epsilon)) {
            j = i;
          }
          // ���nullStand��Ϊtrue����ʾ��ǰ�ķ������ܵ����������FOLLOW����������symbol
          if (nullStand) {
            ((NonTerminalSymbol)Yi).addDependentSetFollow(symbol);
          }
        }
        else {
          j = i;
          if (nullStand) {
            nullStand = false;
          }
        }
        i --;
      }
    }
  }

  public static Boolean isLL1 (NonTerminalSymbol symbol) {
    Map<TerminalSymbol, Integer> map = new HashMap<>();
    // �ж��Ƿ���X->��,�������轫FOLLOW�����map
    if (symbol.containsEpsilon()) {
      for (TerminalSymbol s: symbol.getpFollowSet()) {
        map.put(s, -1);
      }
    }
    // ��������ʽ
    for (Production production: symbol.getpProductionTable()) {
      // ��������ʽ��FIRST���ϣ�����δ���ֹ���˵���޽����������н�������ΪLL��1���ķ���
      for (TerminalSymbol s: production.getpFirstSet()) {
        if (map.get(s) == null) {
          map.put(s,production.getProductionId());
        }
        else {
          return false;
        }
      }
    }
    return true;
  }

  public static ArrayList<Cell> parseTable(ArrayList<NonTerminalSymbol> pNonTerminalSymbolTable) {
    ArrayList<Cell> pParseTableOfLL = new ArrayList<>();
    // ����ÿ�����ս��
    for (NonTerminalSymbol symbol: pNonTerminalSymbolTable) {
      // ����ÿ������ʽ
      for (Production production: symbol.getpProductionTable()) {
        // ���ò���ʽΪX->�ţ���ѡ��FOLLOW�����е��ս������ò���ʽ
        if (production.isEpsilon()) {
          for (TerminalSymbol t: symbol.getpFollowSet()) {
            Cell cell = new Cell(symbol, t, production);
            pParseTableOfLL.add(cell);
          }
        }
        // ����ѡ��FIRST�����е��ս������ò���ʽ
        else {
          for (TerminalSymbol t: production.getpFirstSet()) {
            Cell cell = new Cell(symbol, t, production);
            pParseTableOfLL.add(cell);
          }
        }
      }
    }
    return pParseTableOfLL;
  }
}
