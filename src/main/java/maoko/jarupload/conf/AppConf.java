package maoko.jarupload.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @dscr 程序配置
 * @author fanpei
 * @time 2019年6月10日 下午3:59:21
 *
 */
@Configuration
@PropertySource("file:${user.dir}/conf/app.properties")
public class AppConf {

	/**
	 * 远程仓库id标识
	 */
	@Value("${repository.id}")
	public String repository_id;

	/**
	 * 远程仓库地址
	 */
	@Value("${repository.durl}")
	public String repository_durl;

	/**
	 * 远程仓库登录用户密码
	 */
	@Value("${repository.user.name}")
	public String user_name;

	/**
	 * 远程仓库登录用户密码
	 */
	@Value("${repository.user.password}")
	public String user_password;

	/**
	 * 扫描当前本地maven jar包目录
	 */
	@Value("${maven.repository.local}")
	public String dir;

	/**
	 * 扫描当前本地maven jar包目录
	 */
	@Value("${maven.bin.dir}")
	public String maven_bin_dir;

	@Value("${app.upload.thread.count}")
	public int uploadTdCount;

}
