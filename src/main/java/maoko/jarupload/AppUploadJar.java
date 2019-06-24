package maoko.jarupload;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import maoko.jarupload.conf.AppConf;
import maoko.jarupload.conf.MvnCmd;
import maoko.jarupload.conf.MvnSettings;

/**
 * 需要配置maven环境变量
 * 
 * @dscr 程序入口
 * @author fanpei
 * @time 2019年6月10日 下午3:56:19
 *
 */
@SpringBootApplication
public class AppUploadJar implements CommandLineRunner {
	public static AppConf appConf;

	@Autowired
	public AppConf appConf_obj;

	public static void main(String[] args) {
		SpringApplication.run(AppUploadJar.class, args);
	}

	public void run(String... args) throws Exception {
		AppUploadJar.appConf = appConf_obj;
		new Thread(new Runnable() {// 异步执行
			public void run() {
				try {
					File mavendir = new File(AppUploadJar.appConf.dir);
					if (!mavendir.exists()) {
						throw new Exception(AppUploadJar.appConf.dir + " is not exists");
					}
					TheadPoolExc.init();
					MvnSettings.init();
					MvnCmd.init();
					UploadJarFiles.init();
					TheadPoolExc.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
