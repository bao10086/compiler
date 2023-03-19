package symbol; /**
 * @author ���ѱ�
 * @date 2023/3/15 21:52
 */


public class RegularExpression {
  int regularId;
  String name;
  /**
   * ��������������� 7 �֣���=��, ��~������-��, ��|������.��, ��*��, ��+��, ��?��
   */
  char operatorSymbol;
  /**
   * �������
    */
  int operandId1;
  /**
   * �Ҳ�������һԪ����ʱΪnull��
   */
  int operandId2;
  /**
   * �������������
   */
  OperandType type1;
  /**
   * �Ҳ����������ͣ�һԪ����ʱΪnull��
   */
  OperandType type2;
  /**
   * ������������
   */
  OperandType resultType;
  /**
   * �ʵ� category ����ֵ
   */
  LexemeCategory category;
  /**
   * ��Ӧ�� NFA
   */
  Graph pNFA;

  private static int regularIdNum = 0;

  public RegularExpression(char operatorSymbol, int operandId1,
      int operandId2, OperandType type1, OperandType type2, OperandType resultType,
      LexemeCategory category, Graph pNFA) {
    this.regularId = regularIdNum++;
    this.name = Integer.toString(regularId);
    this.operatorSymbol = operatorSymbol;
    this.operandId1 = operandId1;
    this.operandId2 = operandId2;
    this.type1 = type1;
    this.type2 = type2;
    this.resultType = resultType;
    this.category = category;
    this.pNFA = pNFA;
  }
}
