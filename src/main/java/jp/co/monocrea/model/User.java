package jp.co.monocrea.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private int age;

  /**
   * ユーザーのIDを取得します。
   * 
   * @return ユーザーID
   */
  public Long getId() {
    return id;
  }

  /**
   * ユーザーのIDを設定します。
   * 
   * @param id ユーザーID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * ユーザーの名前を取得します。
   * 
   * @return ユーザー名
   */
  public String getName() {
    return name;
  }

  /**
   * ユーザーの名前を設定します。
   * 
   * @param name ユーザー名
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * ユーザーの年齢を取得します。
   * 
   * @return ユーザーの年齢
   */
  public int getAge() {
    return age;
  }

  /**
   * ユーザーの年齢を設定します。
   * 
   * @param age ユーザーの年齢
   */
  public void setAge(int age) {
    this.age = age;
  }
}
