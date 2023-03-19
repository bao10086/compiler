package symbol; /**
 * @author ���ѱ�
 * @date 2023/3/15 22:06
 */

import java.util.ArrayList;

public class Graph {

  static private int graphIdNum = 0;
  private int graphId;
  private int numOfStates;
  private State start;
  private ArrayList<State> pEndStateTable;
  private ArrayList<Edge> pEdgeTable;
  private ArrayList<State> pStateTable;

  public Graph() {
    this.graphId = graphIdNum++;
    this.numOfStates = 0;
    this.pEndStateTable = new ArrayList<>();
    this.pEdgeTable = new ArrayList<>();
    this.pStateTable = new ArrayList<>();
  }

  public Graph(Graph graph) {
    this.graphId = graphIdNum++;
    this.numOfStates = graph.getNumOfStates();
    this.start = graph.getStart();
    this.pEndStateTable = graph.getpEndStateTable();
    this.pEdgeTable = graph.getpEdgeTable();
    this.pStateTable = graph.getpStateTable();
  }

  public static void setGraphIdNum(int graphIdNum) {
    Graph.graphIdNum = graphIdNum;
  }

  public State getStart() {
    return start;
  }

  public void setStart(State start) {
    this.start = start;
  }

  public int getGraphId() {
    return graphId;
  }

  public void setGraphId(int graphId) {
    this.graphId = graphId;
  }

  public int getNumOfStates() {
    return numOfStates;
  }

  public void setNumOfStates(int numOfStates) {
    this.numOfStates = numOfStates;
  }

  public ArrayList<State> getpEndStateTable() {
    return pEndStateTable;
  }

  public void setpEndStateTable(ArrayList<State> pEndStateTable) {
    this.pEndStateTable = pEndStateTable;
  }

  public ArrayList<Edge> getpEdgeTable() {
    return pEdgeTable;
  }

  public void setpEdgeTable(ArrayList<Edge> pEdgeTable) {
    this.pEdgeTable = pEdgeTable;
  }

  public ArrayList<State> getpStateTable() {
    return pStateTable;
  }

  public void setpStateTable(ArrayList<State> pStateTable) {
    this.pStateTable = pStateTable;
  }

  public void addState(State pState) {
    pStateTable.add(pState);
    numOfStates++;
  }

  public void addEdge(Edge edge) {
    pEdgeTable.add(edge);
    for (State s : pStateTable) {
      if (edge.getNextState() == s.getStateId()) {
        if (s.getType() == StateType.MATCH && !pEndStateTable.contains(s)) {
          pEndStateTable.add(s);
        }
        return;
      }
    }
  }

  public void addEndEdge(State end) {
    for (State state : pEndStateTable) {
      state.setType(StateType.UNMATCH);
      Edge edge = new Edge(state.getStateId(), end.getStateId(), DriverType.NULL);
      pEdgeTable.add(edge);
    }
    pEndStateTable.clear();
    pEndStateTable.add(end);
  }

  public void addEndEdgeToOther(State start) {
    for (Edge edge : pEdgeTable) {
      for (State state : pEndStateTable) {
        if (edge.getNextState() == state.getStateId()) {
          edge.setNextState(start.getStateId());
        }
      }
    }
    pEndStateTable.clear();
    pEndStateTable.add(start);
    start.setType(StateType.MATCH);
  }

  public void addEdgeToEnd(int stateId) {
    for (State state : pEndStateTable) {
      Edge edge = new Edge(stateId, state.getStateId(), DriverType.NULL);
      pEdgeTable.add(edge);
    }
  }

  public void reNumber(int index) {
    // ״̬���±��
    for (State state : pStateTable) {
      int stateId = state.getStateId();
      state.setStateId(stateId+ index);
    }
    // �����±��
    for (Edge edge : pEdgeTable) {
      int fromState = edge.getFromState();
      edge.setFromState(fromState + index);
      int nextState = edge.getNextState();
      edge.setNextState(nextState + index);
    }
  }

  public void addTable(Graph g) {
    pEdgeTable.addAll(g.getpEdgeTable());
    pStateTable.addAll(g.getpStateTable());
    pEndStateTable.addAll(g.pEndStateTable);
  }

  public void addEndState(State state) {
    pEndStateTable.add(state);
  }

  public void removeStateById(int stateId) {
    for (State state : pStateTable) {
      if (state.getStateId() == stateId) {
        pStateTable.remove(state);
      }
    }
    numOfStates --;
  }

  public boolean haveOutside(ArrayList<State> stateArrayList) {
    /**
     * �ж��Ƿ��бߴӽ���״̬����
     */
    for (Edge edge : pEdgeTable) {
      for (State state : stateArrayList) {
        if (edge.getFromState() == state.getStateId()) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean haveInSide(State start) {
    /**
     * �ж��Ƿ��бߵ����ʼ״̬
     */
    for (Edge edge : pEdgeTable) {
      if (edge.getNextState() == start.getStateId()) {
        return true;
      }
    }
    return false;
  }

  public int change() {
    /**
     * ��NFA���еȼ۸���
     */
    int changeType = 0;
    // ��ʼ״̬�����
    if (haveInSide(start)) {
      // ����һ��״̬
      State newStart = new State(0, StateType.UNMATCH, LexemeCategory.EMPTY);
      addState(newStart);
      // ���±��
      reNumber(1);
      // ���һ��newStart����ʼ״̬�Ħű�
      Edge edge = new Edge(0, 1, DriverType.NULL);
      pEdgeTable.add(edge);
      // ���ó�ʼ״̬
      start = newStart;
      // ���λΪ1��ʾ��ʼ״̬�����
      changeType |= 1;
    }
    if (haveOutside(pEndStateTable)) {
      // ����һ��״̬
      State newEnd = new State(pStateTable.size(), StateType.UNMATCH, LexemeCategory.EMPTY);
      addState(newEnd);
      // ��ӽ���״̬��newEnd�Ħű�
      for (State state: pEndStateTable) {
        Edge edge = new Edge(state.getStateId(), newEnd.getStateId(), DriverType.NULL);
        pEdgeTable.add(edge);
      }
      // ��յ�ǰ����״̬
      pEdgeTable.clear();
      // �����µĽ���״̬
      pEndStateTable.add(newEnd);
      // �ڶ���λΪ1��ʾ�ս�״̬�г���
      changeType |= 2;
    }
    return changeType;
  }

  public void mergeStart(Graph NFA) {
    State start = NFA.getStart();
    for (Edge edge: NFA.getpEdgeTable()) {
      // �ҵ����дӳ�ʼ״̬�����ı�
      if (edge.getFromState() == start.getStateId()) {
        edge.setFromState(0);
      }
    }
    pStateTable.remove(start);
  }

  public void mergeEnd(Graph NFA, int endStateId) {
    ArrayList<State> endRemoveList = new ArrayList<>();
    for (State state: NFA.pEndStateTable) {
      for (Edge edge: NFA.getpEdgeTable()) {
        // �ҵ����е����ս�״̬�ı�
        if (edge.getNextState() == state.getStateId()) {
          edge.setNextState(endStateId);
        }
      }
      pStateTable.remove(state);
      endRemoveList.add(state);
    }
    for (State state: endRemoveList) {
      pEndStateTable.remove(state);
    }
  }

  @Override
  public String toString() {
    String s = "Graph{" + '\n';
    s += " graphId: " + graphId + '\n';
    s += " numOfStates: " + numOfStates + '\n';
    s += " start: " + start.toString() + '\n';
    s += " pEndStateTable: " + pEndStateTable.size() + '\n';
    for (State state : pEndStateTable) {
      s += "  " + state.toString() + '\n';
    }
    s += " pEdgeTable: " + pEdgeTable.size() + '\n';
    for (Edge edge : pEdgeTable) {
      s += "  " + edge.toString() + '\n';
    }
    s += " pStateTable: " + numOfStates + '\n';
    for (State state : pStateTable) {
      s += "  " + state.toString() + '\n';
    }
    return s;
  }
}
// NFA״̬����ֻ��洢 category ����ֵ��Ϊ�յ���Щ״̬������״̬����Ҫ�洢
// NFA�У�ֻ�н���״̬�� type ����ֵΪ MATCH������״̬�� type ����ֵ��Ϊ UNMATCH
// DFA�У�ֻ�� type ����ֵΪ MATCH ��״̬���� category ����ֵ�Ų�Ϊ��
