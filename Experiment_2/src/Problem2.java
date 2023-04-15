import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import symbol.ActionCategory;
import symbol.ActionCell;
import symbol.DFA;
import symbol.GotoCell;
import symbol.GrammarSymbol;
import symbol.ItemCategoy;
import symbol.ItemSet;
import symbol.LR0Item;
import symbol.NonTerminalSymbol;
import symbol.Production;
import symbol.ProductionInfo;
import symbol.SymbolType;
import symbol.TerminalSymbol;
import symbol.TransitionEdge;

/**
 * @author ���ѱ�
 * @date 2023/4/10 15:06
 */
public class Problem2 {
  public static void getClosure(ItemSet itemSet) {
    // ջ�����ڱ���δ��������ķ�������Ŀ
    Stack<LR0Item> item = new Stack<>();
    // �����к���������ջ��
    for (LR0Item lr: itemSet.getpItemTable()) {
      item.push(lr);
    }
    while (!item.isEmpty()) {
      LR0Item lr = item.pop();
      int pos = lr.getDotPosition();
      // pos�ڲ���ʽ����棬˵��Ϊ��Լ��Ŀ
      if (pos == lr.getProduction().getBodySize()) {
        continue;
      }
      else {
        // �ҵ������ķ���
        GrammarSymbol symbol = lr.getProduction().getpBodySymbolTable().get(pos);
        // ���ķ���Ϊ���ս����˵��Ϊ��Լ��Ŀ
        if (symbol.getType() == SymbolType.NONTERMINAL) {
          // �����÷��ս����ÿ������ʽ
          for (Production production: ((NonTerminalSymbol)symbol).getpProductionTable()) {
            if (!itemSet.containItem(production, 0)) {
              // �½�һ���Ǻ����ԭ��λ��Ϊ0
              LR0Item newItem = new LR0Item((NonTerminalSymbol)symbol, production, 0,
                  ItemCategoy.NONCORE);
              // ��ӽ��հ�
              itemSet.addItem(newItem);
              // ����ջ��
              item.push(newItem);
            }
          }
        }
      }
    }
  }

  private static ArrayList<ItemSet> allItemSet = new ArrayList<>();
  public static void addItemSet(ItemSet set) {
    allItemSet.add(set);
  }

  public static ArrayList<ItemSet> getAllItemSet() {
    return allItemSet;
  }
  private static ArrayList<ItemSet> newItemSet = new ArrayList<>();

  public static ArrayList<TransitionEdge> exhaustTransition(ItemSet itemSet) {
    // �����´����ı�Ǩ��
    ArrayList<TransitionEdge> edges = new ArrayList<>();
    Map<GrammarSymbol, Vector<LR0Item>> map = new HashMap<>();
    // ��������������������䱣����map��
    for (LR0Item item: itemSet.getpItemTable()) {
      int pos = item.getDotPosition();
      // ��ǰ��ĿΪ��Լ��Ŀ
      if (pos == item.getProduction().getBodySize()) {
        continue;
      }
      // ��ú����ķ���
      GrammarSymbol symbol = item.getProduction().getpBodySymbolTable().get(pos);
      // ���ķ���δ���֣����´���һ��vector����
      if (map.get(symbol) == null) {
        Vector<LR0Item> v = new Vector<>();
        v.add(item);
        map.put(symbol, v);
      }
      // ֮ǰ���ֹ������ں������item
      else {
        map.get(symbol).add(item);
      }
    }
    // ��������������
    for (GrammarSymbol symbol: map.keySet()) {
      // ��һ��Ľ���,idΪ-1����ֹ�ظ����µ�״̬��Ų�������
      ItemSet toSet = new ItemSet(-1);
      for (LR0Item item: map.get(symbol)) {
        // �½�һ���������typeΪCORE��pos��һ
        LR0Item lr = new LR0Item(item);
        toSet.addItem(lr);
      }
      // �����ıհ�
      getClosure(toSet);
      // �ж���һ��Ƿ�Ϊ���
      Boolean isExist = false;
      for (ItemSet set: allItemSet) {
        if (toSet.isSame(set)) {
          isExist = true;
          toSet = set;
          break;
        }
      }
      // ���Ϊ���
      if (!isExist) {
        // ���ø��Ϊ�µ��id����֤����
        toSet.setStateId();
        allItemSet.add(toSet);
        newItemSet.add(toSet);
      }
      // ����һ����Ǩ��
      TransitionEdge edge = new TransitionEdge(symbol, itemSet, toSet);
      edges.add(edge);
    }
    return edges;
  }

  public static DFA getDFA(ItemSet start) {
    // �½�һ��DFA
    DFA dfa = new DFA(start);
    // ����δ���״̬���
    Deque<ItemSet> queue = new ArrayDeque<>();
    queue.push(start);
    while (!queue.isEmpty()) {
      ItemSet current = queue.pop();
      // �Ե�ǰ�������٣��õ���Ǩ��
      ArrayList<TransitionEdge> edges = exhaustTransition(current);
      queue.addAll(newItemSet);
      newItemSet.clear();
      // ������б�Ǩ��dfa��
      dfa.addEdges(edges);
    }
    return dfa;
  }

  public static Boolean isSLR1() {
    // ����ÿ���
    for (ItemSet set: allItemSet) {
      // ���ù�Լ��Ŀ�������ս������
      ArrayList<LR0Item> reduce = new ArrayList<>();
      ArrayList<TerminalSymbol> shift = new ArrayList<>();
      // ����������ÿ����Ŀ
      for (LR0Item item: set.getpItemTable()) {
        Production production = item.getProduction();
        int pos = item.getDotPosition();
        // ����ĿΪ��Լ��Ŀ����Ӹ���Ŀ����Լ��Ŀ����
        if (pos == production.getBodySize()) {
          reduce.add(item);
        }
        // ����ĿΪ������Ŀ������ս���������ս������
        else if (production.getpBodySymbolTable().get(pos).getType() == SymbolType.TERMINAL) {
          shift.add((TerminalSymbol) production.getpBodySymbolTable().get(pos));
        }
      }
      // �жϹ�Լ-�����ͻ
      if (shift.size() > 0 && reduce.size() > 0) {
        // ����ÿ����Լ��Ŀ
        for (LR0Item item: reduce) {
          // �������ʽ�󲿵ķ��ս����FOLLOW����
          Set<TerminalSymbol> follow = item.getNonTerminalSymbol().getpFollowSet();
          // FOLLOW���ϲ����������ս�������ཻ������ΪSLR(1)�ķ�
          for (TerminalSymbol symbol: follow) {
            if (shift.contains(symbol)) {
              return false;
            }
          }
        }
      }
      // �жϹ�Լ-��Լ��ͻ
      if (reduce.size() > 1) {
        Set<TerminalSymbol> followReduce = new HashSet<>();
        for (LR0Item item: reduce) {
          // �������ʽ�󲿵ķ��ս����FOLLOW����
          Set<TerminalSymbol> follow = item.getNonTerminalSymbol().getpFollowSet();
          for (TerminalSymbol symbol: follow) {
            // ����FOLLOW�����е�Ԫ����������Լ��Ŀ��FOLLOW���г��ֹ�������ڹ�Լ-��Լ��ͻ
            if (followReduce.contains(symbol)) {
              return false;
            }
            // ��δ���֣������
            else {
              followReduce.add(symbol);
            }
          }
        }
      }
    }
    return true;
  }

  // LR�﷨�������ACTION����
  private static ArrayList<ActionCell> pActionCellTable = new ArrayList<>();
  // LR�﷨�������GOTO����
  private static ArrayList<GotoCell> pGotoCellTable = new ArrayList<>();

  public static ArrayList<ActionCell> getpActionCellTable() {
    return pActionCellTable;
  }

  public static ArrayList<GotoCell> getpGotoCellTable() {
    return pGotoCellTable;
  }

  public static void getCell(DFA dfa) {
    // ��������״̬����
    for (ItemSet set: allItemSet) {
      // ����ÿ����µ�������Ŀ
      for (LR0Item item: set.getpItemTable()) {
        Production production = item.getProduction();
        // ԭ������λ��
        int pos = item.getDotPosition();
        // ����ĿΪ��Լ��Ŀ���ҵ�FOLLOW������r
        if (pos == production.getBodySize()) {
          // ���и���ĿΪ������Ŀ���ڡ�#������a
          if (item.getProduction().getProductionId() == 0) {
            // ������ӦACTION
            ActionCell cell = new ActionCell(set.getStateId(), "#",
                ActionCategory.a, 0);
            pActionCellTable.add(cell);
          }
          // �������ʽ�󲿵ķ��ս����FOLLOW����
          Set<TerminalSymbol> follow = item.getNonTerminalSymbol().getpFollowSet();
          // ����FOLLOW�����е�ÿ���ս��
          for (TerminalSymbol symbol: follow) {
            // ������ӦACTION
            ActionCell cell = new ActionCell(set.getStateId(), symbol.getName(),
                ActionCategory.r, item.getProduction().getProductionId());
            pActionCellTable.add(cell);
          }
          continue;
        }
        // ԭ�����ķ���
        GrammarSymbol symbol = production.getpBodySymbolTable().get(pos);
        // �ҵ����ķ�����������һ״̬
        ItemSet nextSet = dfa.findNextSet(set, symbol);
        // ����ĿΪ������Ŀ���ҵ��ս����s
        if (symbol.getType() == SymbolType.TERMINAL) {
          // ������ӦACTION
          ActionCell cell = new ActionCell(set.getStateId(), symbol.getName(),
              ActionCategory.s, nextSet.getStateId());
          pActionCellTable.add(cell);
        }
        // ����ĿΪ��Լ��Ŀ���ҵ����ս����GOTO��״̬���
        else {
          // ������ӦGOTO
          GotoCell cell = new GotoCell(set.getStateId(), symbol.getName(),
              nextSet.getStateId());
          pGotoCellTable.add(cell);
        }
      }
    }
  }

  public static ArrayList<ProductionInfo> createInfo(NonTerminalSymbol symbol) {
    ArrayList<ProductionInfo> productionInfoTable = new ArrayList<>();
    for (Production production: symbol.getpProductionTable()) {
      ProductionInfo info = new ProductionInfo(symbol.getName(), production.getBodySize());
      productionInfoTable.add(info);
    }
    return productionInfoTable;
  }
}
