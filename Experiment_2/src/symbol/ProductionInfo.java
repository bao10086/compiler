package symbol;

/**
 * @author ���ѱ�
 * @date 2023/4/9 16:28
 */
public class ProductionInfo {
  // ����ʽ���
  private int indexId;
  // ͷ�����ս��
  private String headName;
  // ����ʽ�����ķ����ĸ���
  private int bodySize;

  private static int idNum = 0;

  public ProductionInfo(String headName, int bodySize) {
    this.indexId = idNum++;
    this.headName = headName;
    this.bodySize = bodySize;
  }

  @Override
  public String toString() {
    return "\nProductionInfo{" +
        "indexId=" + indexId +
        ", headName='" + headName + '\'' +
        ", bodySize=" + bodySize +
        '}';
  }
}
