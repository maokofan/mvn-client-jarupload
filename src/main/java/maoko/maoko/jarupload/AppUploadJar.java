package maoko.maoko.jarupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import maoko.maoko.jarupload.conf.AppConf;
import maoko.maoko.jarupload.conf.MvnCmd;
import maoko.maoko.jarupload.conf.MvnSettings;
import maoko.maoko.jarupload.conf.RepositoryUser;

/**
 * @dscr 程序入口
 * @author fanpei
 * @time 2019年6月10日 下午3:56:19
 *
 */
@SpringBootApplication
public class AppUploadJar implements CommandLineRunner {

	@Autowired
	private AppConf appConf;

	@Autowired
	private ScanDirExc exc;

	public static void main(String[] args) {
		SpringApplication.run(AppUploadJar.class, args);
	}

	public void run(String... args) throws Exception {
		RepositoryUser user = new RepositoryUser(appConf.user_id, appConf.user_name, appConf.user_password);
		UploadJarFiles.init();
		MvnSettings.init(user);
		MvnCmd.init(MvnSettings.SETTINGS_PATH, appConf.repository_id, appConf.repository_durl);
		exc.start(appConf);
	}
}
