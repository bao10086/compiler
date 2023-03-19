package symbol; /**
 * @author ���ѱ�
 * @date 2023/3/15 22:00
 */

public class CharSet {
  /*
    �ַ����з�Χ����('a'~'z')��������(a|b)�Ͳ�����(-) ������һ���µ��ַ�������
    */
  /** �ַ���id */
  private final int indexId;
  /** �ַ����еĶ� id��һ���ַ������԰�������� */
  private final int segmentId;
  /** �ε���ʼ�ַ� */
  private final char fromChar;
  /** �εĽ�β�ַ� */
  private final char toChar;

  private static int indexIdNum = 0;
  private static int segmentIdNum = 0;

  public CharSet(char fromChar, char toChar) {
    /**
     * �����µ��ַ���
     */
    this.indexId = indexIdNum++;
    this.segmentId = segmentIdNum++;
    this.fromChar = fromChar;
    this.toChar = toChar;
  }

  public CharSet(char fromChar, char toChar, int indexId) {
    /**
     * �����µ��ַ����Ķ�
     */
    this.indexId = indexId;
    this.segmentId = segmentIdNum++;
    this.fromChar = fromChar;
    this.toChar = toChar;
  }

  public int getIndexId() {
    return indexId;
  }

  public int getSegmentId() {
    return segmentId;
  }

  public char getFromChar() {
    return fromChar;
  }

  public char getToChar() {
    return toChar;
  }

  public static int getIndexIdNum() {
    return indexIdNum;
  }
}
