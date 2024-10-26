package jp.co.monocrea;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommonUtil {

  /**
   * 現在実行中のメソッド名を取得します。
   *
   * @return 現在のメソッド名
   */
  public String getCurrentMethodName() {
    // スタックトレースから現在のメソッド名を取得して返す
    return Thread.currentThread().getStackTrace()[2].getMethodName();
  }
}
