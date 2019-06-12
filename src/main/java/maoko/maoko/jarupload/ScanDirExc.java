package maoko.maoko.jarupload;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import maoko.maoko.jarupload.conf.AppConf;

/**
 * 扫描目录执行器
 * 
 * @dscr
 * @author fanpei
 * @time 2019年6月10日 下午5:30:17
 *
 */
@Component
public class ScanDirExc {

	/**
	 * 上传执行线程服务
	 */
	public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);// 线程池越多 上传越快

	public void start() {
		AppConf appconf = AppUploadJar.appConf;
		System.out.println("start scan dir:" + appconf.dir);
		scanDir(new File(appconf.dir));
		// System.out.println("scan dir end:" + appconf.dir);
	}

	private void scanDir(File JAR_DIR) {
		if (JAR_DIR.isDirectory()) {
			File[] files = JAR_DIR.listFiles();
			if (files == null || files.length == 0) {
				// ignore
			} else {
				for (File file : files) {
					if (file.getName().startsWith("."))// 缓存文件
						continue;
					if (file.isDirectory()) {
						scanDir(file);
					} else// 当前文件是文件时，则传入父目录
					{
						submit(new UploadJarFiles(file.getParentFile()));
						break;// 防止同目录重复扫描
					}
				}
			}
		}
	}

	/**
	 * 提交一个任务
	 * 
	 * @param task
	 */
	public static void submit(Runnable task) {
		EXECUTOR_SERVICE.submit(task);
	}
}
