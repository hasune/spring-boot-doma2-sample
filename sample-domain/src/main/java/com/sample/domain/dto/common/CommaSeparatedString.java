package com.sample.domain.dto.common;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import org.seasar.doma.Domain;

@Domain(valueType = String.class, factoryMethod = "of")
@NoArgsConstructor
public class CommaSeparatedString implements Serializable {

  private static final long serialVersionUID = -6864852815920199569L;

  @Getter String data;

  /**
   * ファクトリメソッド
   *
   * @param data
   * @return
   */
  public static CommaSeparatedString of(String data) {
    val css = new CommaSeparatedString();
    css.data = String.join(",", data);
    return css;
  }

  // ResultSet.getBytes(int)で取得された値がこのコンストラクタで設定される
  CommaSeparatedString(String data) {
    this.data = String.join(",", data);
  }

  // PreparedStatement.setBytes(int, bytes)へ設定する値がこのメソッドから取得される
  String getValue() {
    return this.data;
  }
}
