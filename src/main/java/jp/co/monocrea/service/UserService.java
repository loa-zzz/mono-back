package jp.co.monocrea.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jp.co.monocrea.CommonUtil;
import jp.co.monocrea.model.User;
import jp.co.monocrea.repository.UserRepository;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserService {

  private static final Logger LOG = Logger.getLogger(UserService.class);

  @Inject UserRepository userRepository;
  @Inject CommonUtil common;

  /**
   * データベースから全てのユーザーを取得します。 処理をログに記録し、全てのユーザーのリストを返します。
   *
   * @return 全ユーザーのリスト
   */
  public List<User> findAll() {
    String methodName = common.getCurrentMethodName() + "：";
    LOG.info(methodName + "データベースから全てのユーザーを取得");
    List<User> users = userRepository.listAll();
    LOG.info(methodName + "取得成功。ユーザー数: " + users.size());
    return users;
  }

  /**
   * 指定されたIDのユーザーをデータベースから取得します。 処理をログに記録し、ユーザーが見つかれば返し、見つからない場合は警告をログに記録します。
   *
   * @param id 取得するユーザーのID
   * @return 指定されたIDのユーザー、または見つからなければnull
   */
  public User findById(Long id) {
    String methodName = common.getCurrentMethodName() + "：";
    LOG.info(methodName + "ID: " + id + " のユーザーを取得中");
    User user = userRepository.findById(id);
    if (user == null) {
      LOG.warn(methodName + "ID " + id + " のユーザーが見つかりません");
    } else {
      LOG.info(methodName + "ID " + id + " のユーザーを発見: " + user);
    }
    return user;
  }

  /**
   * 新しいユーザーをデータベースに保存します。 IDが既に存在する場合は、エラーをスローします。
   *
   * @param user 保存するユーザーオブジェクト
   * @return 保存されたユーザー
   * @throws WebApplicationException ユーザーが既に存在する場合
   */
  @Transactional
  public User save(User user) {
    // ユーザーがすでに存在するか確認
    if (user.getId() != null && userRepository.findByIdOptional(user.getId()).isPresent()) {
      throw new WebApplicationException(
          "ID " + user.getId() + " のユーザーは既に存在します", Response.Status.CONFLICT);
    }

    // 新規ユーザーを登録
    userRepository.persist(user);
    return user;
  }

  /**
   * 指定されたIDのユーザー情報を更新します。 ユーザーが存在しない場合はエラーをスローします。
   *
   * @param id 更新するユーザーのID
   * @param user 更新後のユーザー情報
   * @return 更新されたユーザー
   * @throws WebApplicationException ユーザーが存在しない場合
   */
  @Transactional
  public User update(Long id, User user) {
    String methodName = common.getCurrentMethodName() + "：";
    LOG.info(methodName + "ID: " + id + " のユーザー情報を更新中");
    User existingUser = userRepository.findById(id);
    if (existingUser == null) {
      LOG.error("ID " + id + " のユーザーが見つかりません");
      throw new WebApplicationException("ID " + id + " のユーザーは存在しません", Response.Status.NOT_FOUND);
    }
    existingUser.setName(user.getName());
    existingUser.setAge(user.getAge());
    userRepository.persist(existingUser);
    LOG.info("ID " + id + " のユーザー情報を更新しました");
    return existingUser;
  }

  /**
   * 指定されたIDのユーザーを削除します。 ユーザーが存在しない場合はエラーをスローします。
   *
   * @param id 削除するユーザーのID
   * @throws WebApplicationException ユーザーが存在しない場合
   */
  @Transactional
  public void delete(Long id) {
    String methodName = common.getCurrentMethodName() + "：";
    LOG.info(methodName + "ID: " + id + " のユーザーを削除中");
    if (!userRepository.findByIdOptional(id).isPresent()) {
      LOG.error("ID " + id + " のユーザーが見つかりません");
      throw new WebApplicationException("ID " + id + " のユーザーは存在しません", Response.Status.NOT_FOUND);
    }
    userRepository.deleteById(id);
    LOG.info(methodName + "ID " + id + " のユーザーを削除しました");
  }
}
