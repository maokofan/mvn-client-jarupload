package maoko.maoko.jarupload.conf;

/**
 * @dscr 私有库登录信息
 * @author fanpei
 * @time 2019年6月10日 下午4:37:04
 *
 */
public class RepositoryUser {

	private String id;
	private String username;
	private String pass;

	public RepositoryUser(String id, String username, String pass) {
		this.id = id;
		this.username = username;
		this.pass = pass;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPass() {
		return pass;
	}

}
