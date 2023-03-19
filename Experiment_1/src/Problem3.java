/**
 * @author ���ѱ�
 * @date 2023/3/15 22:25
 */
import java.util.ArrayList;
import symbol.*;
public class Problem3 {
  public static ArrayList<Integer> move(Graph graph, int stateId, int driveId) {
    /**
     * ״̬ת�ƺ���
     */
    ArrayList<Edge> edgeArrayList = graph.getpEdgeTable();
    ArrayList<Integer> stateArrayListAnswer = new ArrayList<>();
    for (Edge edge: edgeArrayList) {
      if (edge.getFromState() == stateId && edge.getDriverId() == driveId) {
        int nextState = edge.getNextState();
        stateArrayListAnswer.add(nextState);
      }
    }
    return stateArrayListAnswer;
  }

  public static ArrayList<Integer> closure(Graph graph, ArrayList<Integer> stateArrayList) {
    /**
     * ��ձհ�
     */
    ArrayList<Edge> edgeArrayList = graph.getpEdgeTable();
    ArrayList<Integer> resultStateArrayList, currentStateArrayList, nextSateArrayList = new ArrayList<>();
    resultStateArrayList = stateArrayList;
    currentStateArrayList = stateArrayList;
    while(!currentStateArrayList.isEmpty()) {
      // �ҵ��ܹ���ת����״̬����
      for (Edge edge: edgeArrayList) {
        if (currentStateArrayList.contains(edge.getFromState()) && edge.getType() == DriverType.NULL) {
          if (!nextSateArrayList.contains(edge.getNextState())) {
            nextSateArrayList.add(edge.getNextState());
          }
        }
      }
      // ȥ�����״̬���Ϻ��״̬����
      for (Integer stateId: resultStateArrayList) {
        if (nextSateArrayList.contains(stateId)) {
          nextSateArrayList.remove(stateId);
        }
      }
      // ȥ����Ϊ�ձ�ʾ�հ��������
      if (nextSateArrayList.isEmpty()) {
        break;
      }
      else {
        resultStateArrayList.addAll(nextSateArrayList);
        currentStateArrayList = nextSateArrayList;
      }
    }
    return resultStateArrayList;
  }

  public static ArrayList<Integer> dTran(Graph graph, ArrayList<Integer> currentStateArrayList, int driveId) {
    /**
     * ״̬ת�Ƽ���
     */
    ArrayList<Integer> nextStateArrayList = new ArrayList<>();
    for (int stateId: currentStateArrayList) {
      nextStateArrayList.addAll(move(graph, stateId, driveId));
    }
    return closure(graph, nextStateArrayList);
  }

  public static Graph NFAToDFA(Graph pNFA) {
    /**
     * NFAת��ΪDFA
     */
    Graph graph = new Graph();
    ArrayList<Edge> edgeArrayList = pNFA.getpEdgeTable();
    // ��ʼ״̬�ıհ�
    State start = pNFA.getStart();
    ArrayList<Integer> currentStateArrayList = new ArrayList<>();
    currentStateArrayList.add(start.getStateId());
    currentStateArrayList = closure(pNFA, currentStateArrayList);
    // ���ڴ洢����״̬���ϵļ���
    ArrayList<ArrayList<Integer>> allStateList = new ArrayList<>();
    // �洢����״̬����
    ArrayList<Edge> edgeList = new ArrayList<>();
    // �洢��ʼ״̬
    allStateList.add(currentStateArrayList);
    State startList = new State(0, StateType.UNMATCH, LexemeCategory.EMPTY);
    ArrayList<State> stateList = new ArrayList<>();
    stateList.add(startList);
    graph.setStart(startList);
    // �洢����״̬����
    ArrayList<State> endStateList = new ArrayList<>();
    // ��ʾ��ǰ״̬���
    int currentState = 0;
    while (currentState < allStateList.size()) {
      currentStateArrayList = allStateList.get(currentState);
      ArrayList<Integer> driverIdList = new ArrayList<>();
      for (Edge edge: edgeArrayList) {
        if (currentStateArrayList.contains(edge.getFromState()) && edge.getType() != DriverType.NULL
            && !driverIdList.contains(edge.getDriverId())) {
          // �����ַ�
          ArrayList<Integer> stateArrayList = dTran(pNFA, currentStateArrayList, edge.getDriverId());
          driverIdList.add(edge.getDriverId());
          // �ж��Ƿ�Ϊ�µ�״̬����
          if (!allStateList.contains(stateArrayList)) {
            allStateList.add(stateArrayList);
            // ����±�
            Edge edgeNew = new Edge(currentState, stateList.size(), edge.getDriverId(), edge.getType());
            edgeList.add(edgeNew);
            // ����µ�״̬,�ж��Ƿ����ս�״̬�������Ƿ�Ϊ��
            StateType symbol = StateType.UNMATCH;
            LexemeCategory category = LexemeCategory.EMPTY;
            for (State x: pNFA.getpStateTable()) {
              if (stateArrayList.contains(x.getStateId()) && x.getType() == StateType.MATCH) {
                symbol = StateType.MATCH;
              }
              if (x.getCategory() != LexemeCategory.EMPTY) {
                category = x.getCategory();
              }
            }
            State state = new State(stateList.size(), symbol, category);
            if (symbol == StateType.MATCH) {
              endStateList.add(state);
            }
            stateList.add(state);
          }
          else {
            int stateNum = allStateList.indexOf(stateArrayList);
            Edge edgeNew = new Edge(currentState, stateNum, edge.getDriverId(), edge.getType());
            edgeList.add(edgeNew);
          }
        }
      }
      currentState ++;
    }
    graph.setNumOfStates(currentState);
    graph.setpEndStateTable(endStateList);
    graph.setpStateTable(stateList);
    graph.setpEdgeTable(edgeList);
    return graph;
  }
}
