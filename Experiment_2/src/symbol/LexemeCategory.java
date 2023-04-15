package symbol;

/**
 * @author ���ѱ�
 * @date 2023/3/15 21:55
 */
public enum LexemeCategory {
  // ���ַ�
  EMPTY,
  // ��������
  INTEGER_CONST,
  // ʵ������
  FLOAT_CONST,
  // ��ѧ����������
  SCIENTIFIC_CONST,
  // ��ֵ�����
  NUMERIC_OPERATOR,
  // ע��
  NOTE,
  // �ַ�������
  STRING_CONST,
  // �ո���
  SPACE_CONST,
  // �Ƚ������
  COMPARE_CONST,
  // ������
  ID,
  // �߼������
  LOGIC_OPERATOR,
  // �ؼ���
  KEYWORD
}
