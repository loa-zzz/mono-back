package jp.co.monocrea.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jp.co.monocrea.CommonUtil;
import jp.co.monocrea.model.User;
import jp.co.monocrea.service.UserService;
import org.jboss.logging.Logger;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

  private static final Logger LOG = Logger.getLogger(UserResource.class);

  @Inject UserService userService;
  @Inject CommonUtil common;

  /**
   * 全てのユーザーを取得します。 データベースから全てのユーザーを取得し、リスト形式で返します。
   *
   * @return 全ユーザーのリスト
   */
  @GET
  public List<User> getAllUsers() {
    String methodName = common.getCurrentMethodName() + "：";
    LOG.info(methodName + "全ユーザーを取得中");
    List<User> users = userService.findAll();
    LOG.info(methodName + "ユーザーの取得成功: " + users.size() + "件");
    return users;
  }

  /**
   * 指定されたIDのユーザーを取得します。 ユーザーが見つからない場合は404エラーを返します。
   *
   * @param id 取得するユーザーのID
   * @return ユーザー情報、もしくは404エラー
   */
  @GET
  @Path("/{id}")
  public Response getUserById(@PathParam("id") Long id) {
    User user = userService.findById(id);
    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build(); // ユーザーが見つからない場合
    }
    return Response.ok(user).build();
  }

  /**
   * 新規ユーザーを作成します。 作成されたユーザーを返し、201ステータスを返します。
   *
   * @param user 作成するユーザー
   * @return 作成されたユーザー情報と201ステータス
   */
  @POST
  public Response createUser(User user) {
    userService.save(user);
    return Response.status(Response.Status.CREATED).entity(user).build();
  }

  /**
   * 指定されたIDのユーザー情報を更新します。 IDに基づいてユーザー情報を更新し、更新後のデータを返します。
   *
   * @param id 更新するユーザーのID
   * @param user 更新後のユーザー情報
   * @return 更新されたユーザー情報
   */
  @PUT
  @Path("/{id}")
  public Response updateUser(@PathParam("id") Long id, User user) {
    user.setId(id); // IDをセットしてから保存
    userService.save(user);
    return Response.ok(user).build();
  }

  /**
   * 指定されたIDのユーザーを削除します。 削除が成功した場合は204ステータスを返します。
   *
   * @param id 削除するユーザーのID
   * @return 204ステータス（成功）
   */
  @DELETE
  @Path("/{id}")
  @Transactional
  public Response deleteUser(@PathParam("id") Long id) {
    userService.delete(id);
    return Response.noContent().build();
  }
}
