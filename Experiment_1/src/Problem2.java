/**
 * @author ���ѱ�
 * @date 2023/3/15 22:23
 */
import java.util.ArrayList;
import symbol.*;

public class Problem2 {
  public static Graph generateBasicNFA(DriverType driverType, int driverId, LexemeCategory category) {
    Graph pNFA = new Graph();
    State pState1 = new State(0, StateType.UNMATCH, LexemeCategory.EMPTY);
    State pState2 = new State(1, StateType.MATCH, category);
    Edge edge = new Edge(pState1.getStateId(), pState2.getStateId(), driverId, driverType);
    // ����NFA��
    pNFA.addState(pState1);
    pNFA.setStart(pState1);
    pNFA.addState(pState2);
    pNFA.addEdge(edge);
    return pNFA;
  }

  public static Graph union(Graph pNFA1, Graph pNFA2) {
    /**
     * ������
     */
    Graph graph = new Graph();
    // ������֮ǰ��pNFA1��pNFA2���еȼ۸���
    pNFA1.change();
    pNFA2.change();
    // ��ȡ��ʼ״̬
    State start = pNFA1.getStart();
    graph.setStart(start);
    // ��ȡ״̬��Ŀ
    int stateNum1 = pNFA1.getNumOfStates();
    int stateNum2 = pNFA2.getNumOfStates();
    graph.setNumOfStates(stateNum1+stateNum2-2);
    // ���±�Ų������µ�graph
    pNFA2.reNumber(stateNum1-2);
    // ��pNFA1��pNFA2������ͼ��
    graph.addTable(pNFA1);
    graph.addTable(pNFA2);
    // �ϲ��ս�״̬
    graph.mergeEnd(pNFA1, stateNum1+stateNum2-3);
    // �ϲ���ʼ״̬
    graph.mergeStart(pNFA2);
    return graph;
  }

  public static Graph product(Graph pNFA1, Graph pNFA2) {
    /**
     * ��������
     */
    Graph graph = new Graph();
    // ��ȡ��ʼ״̬�����Ϊ0
    State start = pNFA1.getStart();
    graph.setStart(start);
    // ���pNFA1�������
    graph.addTable(pNFA1);
    // ��ȡ״̬��Ŀ
    int stateNum1 = pNFA1.getNumOfStates();
    int stateNum2 = pNFA2.getNumOfStates();
    // �ж����޳����
    if (pNFA1.haveOutside(pNFA1.getpEndStateTable()) && pNFA2.haveInSide(pNFA2.getStart())) {
      // ���±��
      pNFA2.reNumber(stateNum1);
      // ����״̬��Ϣ
      graph.setNumOfStates(stateNum1+stateNum2);
      // ��Ӧű�
      for (State state:pNFA1.getpEndStateTable()) {
        Edge edge = new Edge(state.getStateId(), pNFA2.getStart().getStateId(), DriverType.NULL);
        graph.addEdge(edge);
      }
    }
    else {
      // ���±��
      pNFA2.reNumber(stateNum1-1);
      // ��ʼ״̬��pNFA2���ս�״̬�ϲ�
      pNFA2.removeStateById(stateNum1-1);
      // ����״̬��Ϣ
      graph.setNumOfStates(stateNum1+stateNum2-1);
    }
    for (State state: graph.getpStateTable()) {
      state.setType(StateType.UNMATCH);
    }
    graph.setpEndStateTable(new ArrayList<>());
    // ����pNFA1��pNFA2�ı�
    graph.addTable(pNFA2);
    return graph;
  }

  public static Graph plusClosure(Graph pNFA) {
    /**
     * ���հ�����
     */
    Graph graph = new Graph();
    // ������ʼ״̬����pNFA�ĳ�ʼ״̬
    State start = pNFA.getStart();
    graph.setStart(start);
    // ��ȡ״̬��Ŀ
    int stateNum1 = pNFA.getNumOfStates();
    graph.setNumOfStates(stateNum1);
    // ����pNFA�ӽ�������ʼ�ı�
    graph.addTable(pNFA);
    pNFA.addEndEdgeToOther(pNFA.getStart());
    return graph;
  }

  public static Graph closure(Graph pNFA) {
    /**
     * �հ�����
     */
    State start = pNFA.getStart();
    ArrayList<State> endStateList = pNFA.getpEndStateTable();
    // �жϳ���ߣ���������״̬��Ϣ
    int changeType = pNFA.change();
    if (changeType == 0) {
      // �޳���ߣ��ս�״̬�ϲ�����ʼ״̬
      pNFA.mergeEnd(pNFA, 0);
      pNFA.setNumOfStates(pNFA.getpStateTable().size());
      for (State state: pNFA.getpStateTable()) {
        if (state.getStateId() == 0) {
          state.setType(StateType.MATCH);
          pNFA.addEndState(state);
        }
      }
    }
    else {
      // ��Ӵ�ԭ��ʼ״̬��ԭ�ս�״̬�Ħű�
      for (State state: endStateList) {
        Edge edge = new Edge(state.getStateId(), start.getStateId(), DriverType.NULL);
        pNFA.addEdge(edge);
      }
      // ��Ӵӿ�ʼ���ս�Ħű�
      start = pNFA.getStart();
      ArrayList<Edge> edgeList = new ArrayList<>();
      for (State state: pNFA.getpEndStateTable()) {
        Edge edge = new Edge(start.getStateId(), state.getStateId(), DriverType.NULL);
        // �����漰�����Ľ���״̬���ϵĲ����������Ҫ�ȱ�������ӱ�
        edgeList.add(edge);
      }
      for (Edge edge: edgeList) {
        pNFA.addEdge(edge);
      }
    }
    // �½�һ��NFA�����ƽ�������
    Graph graph = new Graph(pNFA);
    return graph;
  }

  public static Graph zeroOrOne(Graph pNFA) {
    /**
     * 0 ���� 1 �����㡣
     */
    Graph graph = new Graph();
    // ��ȡ��ʼ״̬
    State start = pNFA.getStart();
    graph.setStart(start);
    graph.addTable(pNFA);
    // ���ݳ������ӻ����״̬��Ϣ
    graph.change();
    // ����״̬��Ŀ
    graph.setNumOfStates(graph.getpStateTable().size());
    // ����pNFA�ĳ�ʼ״̬���ս�״̬�ı�
    for (State state: graph.getpEndStateTable()) {
      Edge edge = new Edge(start.getStateId(), state.getStateId(), DriverType.NULL);
      graph.addEdge(edge);
    }
    return graph;
  }
}
