/**
 * @author ���ѱ�
 * @date 2023/3/18 12:00
 */

import java.util.ArrayList;
import symbol.DriverType;
import symbol.Graph;
import symbol.LexemeCategory;

public class Problem4 {
  public void test() {
    /**
     * ����(a|b)*abb
     */
    // �����ַ�����
    int driveIda = Problem1.range('a', 'a');
    int driveIdb = Problem1.range('b', 'b');
    System.out.println(driveIda);
    System.out.println(driveIdb);
    // ����NFAͼ
    // a
    Graph graphA = Problem2.generateBasicNFA(DriverType.CHAR, driveIda, LexemeCategory.EMPTY);
    System.out.println("a");
    System.out.println(graphA);
    // b
    Graph graphB = Problem2.generateBasicNFA(DriverType.CHAR, driveIdb, LexemeCategory.EMPTY);
    System.out.println("b");
    System.out.println(graphB);
    // a|b
    Graph graphAUnionB = Problem2.union(graphA, graphB);
    System.out.println("a|b");
    System.out.println(graphAUnionB);
    // (a|b)*
    Graph graphClosure = Problem2.closure(graphAUnionB);
    System.out.println("(a|b)*");
    System.out.println(graphClosure);
    // (a|b)*a
    graphA = Problem2.generateBasicNFA(DriverType.CHAR, driveIda, LexemeCategory.EMPTY);
    System.out.println("(a|b)*a");
    Graph graphAndA = Problem2.product(graphClosure, graphA);
    System.out.println(graphAndA);
    // (a|b)*ab
    graphB = Problem2.generateBasicNFA(DriverType.CHAR, driveIdb, LexemeCategory.EMPTY);
    Graph graphAndB = Problem2.product(graphAndA, graphB);
    System.out.println("(a|b)*ab");
    System.out.println(graphAndB);
    // (a|b)*abb
    graphB = Problem2.generateBasicNFA(DriverType.CHAR, driveIdb, LexemeCategory.EMPTY);
    Graph graphEnd = Problem2.product(graphAndB, graphB);
    System.out.println("(a|b)*abb");
    System.out.println(graphEnd);

    // NFAת��ΪDFA
    Graph graphDFA = Problem3.NFAToDFA(graphEnd);
    System.out.println(graphDFA);
  }

  public void testTINY() {
    /**
     * TINY����
     */
    // �����ַ�����
    int charSetLetterLower = Problem1.range('a', 'z');
    int charSetLetterUpper = Problem1.range('A', 'Z');
    int charSetDigit = Problem1.range('0', '9');
    int charSetLetter = Problem1.union(charSetLetterLower, charSetLetterUpper);
    ArrayList<Integer> charSetLetterList = new ArrayList<>();
    for (char c = 'a'; c <= 'z'; c ++) {
      charSetLetterList.add(Problem1.range(c, c));
    }
    int charSetAdd = Problem1.range('+', '+');
    int charSetSub = Problem1.range('-', '-');
    int charSetMul = Problem1.range('*', '*');
    int charSetDiv = Problem1.range('/', '/');
    int charSetEqual = Problem1.range('=', '=');
    int charSetSmall = Problem1.range('<', '<');
    int charSetLeftBracket = Problem1.range('(', '(');
    int charSetRightBracket = Problem1.range(')', ')');
    int charSetSemicolon = Problem1.range(';', ';');
    int charSetColon = Problem1.range(':', ':');
    int charSetSpace = Problem1.range(' ', ' ');
    int charSetLeftNote = Problem1.range('{', '{');
    int charSetRightNote = Problem1.range('}', '}');

    // �ؼ���if
    Graph graphI = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(8), LexemeCategory.EMPTY);
    Graph graphF = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.KEYWORD);
    Graph graphIF = Problem2.product(graphI, graphF);

    // �ؼ���then
    Graph graphT = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(19), LexemeCategory.EMPTY);
    Graph graphH = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(7), LexemeCategory.EMPTY);
    Graph graphE = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.EMPTY);
    Graph graphN = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(13), LexemeCategory.KEYWORD);
    Graph graphTH = Problem2.product(graphT, graphH);
    Graph graphTHE = Problem2.product(graphTH, graphE);
    Graph graphTHEN = Problem2.product(graphTHE, graphN);

    // �ؼ���else
    graphE = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.EMPTY);
    Graph graphL = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(11), LexemeCategory.EMPTY);
    Graph graphS = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(18), LexemeCategory.EMPTY);
    Graph graphE1 = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.KEYWORD);
    Graph graphEL = Problem2.product(graphE, graphL);
    Graph graphELS = Problem2.product(graphEL, graphS);
    Graph graphELSE = Problem2.product(graphELS, graphE1);

    // �ؼ���end
    graphE = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.EMPTY);
    graphN = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(13), LexemeCategory.EMPTY);
    Graph graphD = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(3), LexemeCategory.KEYWORD);
    Graph graphEN = Problem2.product(graphE, graphN);
    Graph graphEND = Problem2.product(graphEN, graphD);

    // �ؼ���repeat
    Graph graphR = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(17), LexemeCategory.EMPTY);
    graphE = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.EMPTY);
    Graph graphP = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(15), LexemeCategory.EMPTY);
    graphE1 = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.EMPTY);
    Graph graphA = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(0), LexemeCategory.EMPTY);
    graphT = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(19), LexemeCategory.KEYWORD);
    Graph graphRE = Problem2.product(graphR, graphE);
    Graph graphREP = Problem2.product(graphRE, graphP);
    Graph graphREPE = Problem2.product(graphREP, graphE1);
    Graph graphREPEA = Problem2.product(graphREPE, graphA);
    Graph graphREPEAT = Problem2.product(graphREPEA, graphT);

    // �ؼ���until
    Graph graphU = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(20), LexemeCategory.EMPTY);
    graphN = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(13), LexemeCategory.EMPTY);
    graphT = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(19), LexemeCategory.EMPTY);
    graphI = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(8), LexemeCategory.EMPTY);
    graphL = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(11), LexemeCategory.KEYWORD);
    Graph graphUN = Problem2.product(graphU, graphN);
    Graph graphUNT = Problem2.product(graphUN, graphT);
    Graph graphUNTI = Problem2.product(graphUNT, graphI);
    Graph graphUNTIL = Problem2.product(graphUNTI, graphL);

    // �ؼ���read
    graphR = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(17), LexemeCategory.EMPTY);
    graphE = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.EMPTY);
    graphA = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(0), LexemeCategory.EMPTY);
    graphD = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(3), LexemeCategory.KEYWORD);
    graphRE = Problem2.product(graphR, graphE);
    Graph graphREA = Problem2.product(graphRE, graphA);
    Graph graphREAD = Problem2.product(graphREA, graphD);

    // �ؼ���write
    Graph graphW = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(22), LexemeCategory.EMPTY);
    graphR = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(17), LexemeCategory.EMPTY);
    graphI = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(8), LexemeCategory.EMPTY);
    graphT = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(19), LexemeCategory.EMPTY);
    graphE = Problem2.generateBasicNFA(DriverType.CHAR, charSetLetterList.get(4), LexemeCategory.KEYWORD);
    Graph graphWR = Problem2.product(graphW, graphR);
    Graph graphWRI = Problem2.product(graphWR, graphI);
    Graph graphWRIT = Problem2.product(graphWRI, graphT);
    Graph graphWRITE = Problem2.product(graphWRIT, graphE);

    // ר�÷���+
    Graph graphAdd = Problem2.generateBasicNFA(DriverType.CHAR, charSetAdd, LexemeCategory.NUMERIC_OPERATOR);

    // ר�÷���-
    Graph graphSub = Problem2.generateBasicNFA(DriverType.CHAR, charSetSub, LexemeCategory.NUMERIC_OPERATOR);

    // ר�÷���*
    Graph graphMul = Problem2.generateBasicNFA(DriverType.CHAR, charSetMul, LexemeCategory.NUMERIC_OPERATOR);

    // ר�÷���/
    Graph graphDiv= Problem2.generateBasicNFA(DriverType.CHAR, charSetDiv, LexemeCategory.NUMERIC_OPERATOR);

    // ר�÷���=
    Graph graphEqual = Problem2.generateBasicNFA(DriverType.CHAR, charSetEqual, LexemeCategory.LOGIC_OPERATOR);

    // ר�÷���<
    Graph graphSmall = Problem2.generateBasicNFA(DriverType.CHAR, charSetSmall, LexemeCategory.COMPARE_CONST);

    // ר�÷���(
    Graph graphLeftBracket = Problem2.generateBasicNFA(DriverType.CHAR, charSetLeftBracket, LexemeCategory.LOGIC_OPERATOR);

    // ר�÷���)
    Graph graphRightBracket = Problem2.generateBasicNFA(DriverType.CHAR, charSetRightBracket, LexemeCategory.LOGIC_OPERATOR);

    // ר�÷���;
    Graph graphSemicolon = Problem2.generateBasicNFA(DriverType.CHAR, charSetSemicolon, LexemeCategory.LOGIC_OPERATOR);

    // ר�÷���:=
    Graph graphColon = Problem2.generateBasicNFA(DriverType.CHAR, charSetColon, LexemeCategory.LOGIC_OPERATOR);

    // ID = letter+
    Graph graphLetter = Problem2.generateBasicNFA(DriverType.CHARSET, charSetLetter, LexemeCategory.ID);
    Graph graphID = Problem2.plusClosure(graphLetter);

    // NUM = digit+
    Graph graphDigit = Problem2.generateBasicNFA(DriverType.CHARSET, charSetDigit, LexemeCategory.INTEGER_CONST);
    Graph graphNum = Problem2.plusClosure(graphDigit);

    // �ո�
    Graph graphSpa = Problem2.generateBasicNFA(DriverType.CHARSET, charSetSpace, LexemeCategory.SPACE_CONST);
    Graph graphSpace = Problem2.plusClosure(graphSpa);

    // ע��{}, ����Ƕ��
    Graph graphLeftNote = Problem2.generateBasicNFA(DriverType.CHAR, charSetLeftNote, LexemeCategory.EMPTY);
    graphLetter = Problem2.generateBasicNFA(DriverType.CHARSET, charSetLetter, LexemeCategory.EMPTY);
    Graph graphContent = Problem2.closure(graphLetter);
    Graph graphRightNote = Problem2.generateBasicNFA(DriverType.CHARSET, charSetRightNote, LexemeCategory.NOTE);
    Graph graphNote = Problem2.product(Problem2.product(graphLeftNote, graphContent), graphRightNote);

    // �ܽ�
    Graph graph1 = Problem2.union(graphIF, graphTHEN);
    Graph graph2 = Problem2.union(graph1, graphEND);
    Graph graph3 = Problem2.union(graph2, graphELSE);
    Graph graph4 = Problem2.union(graph3, graphREPEAT);
    Graph graph5 = Problem2.union(graph4, graphUNTIL);
    Graph graph6 = Problem2.union(graph5, graphREAD);
    Graph graph7 = Problem2.union(graph6, graphWRITE);
    Graph graph8 = Problem2.union(graph7, graphAdd);
    Graph graph9 = Problem2.union(graph8, graphSub);
    Graph graph10 = Problem2.union(graph9, graphMul);
    Graph graph11 = Problem2.union(graph10, graphDiv);
    Graph graph12 = Problem2.union(graph11, graphEqual);
    Graph graph13 = Problem2.union(graph12, graphSmall);
    Graph graph14 = Problem2.union(graph13, graphLeftBracket);
    Graph graph15 = Problem2.union(graph14, graphRightBracket);
    Graph graph16 = Problem2.union(graph15, graphSemicolon);
    Graph graph17 = Problem2.union(graph16, graphColon);
    Graph graph18 = Problem2.union(graph17, graphID);
    Graph graph19 = Problem2.union(graph18, graphNum);
    Graph graph20 = Problem2.union(graph19, graphSpace);
    Graph graphEnd = Problem2.union(graph20, graphNote);

    // ת��ΪDFA
    Graph graphDFA = Problem3.NFAToDFA(graphEnd);
    System.out.println(graphDFA);
  }
  public static void main(String[] args) {
    Problem4 problem4 = new Problem4();
    problem4.test();
//    problem4.testTINY();
  }
}
