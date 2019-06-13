package maoko.jarupload;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import maoko.jarupload.conf.AppConf;

/**
 * 线程池执行器
 * 
 * @dscr
 * @author fanpei
 * @time 2019年6月10日 下午5:30:17
 *
 */
@Component
public class TheadPoolExc {

	/**
	 * 上传执行线程服务
	 */
	public static ExecutorService UPLOAD_SERVICE = null;// 上传线程

	public static ExecutorService PRINT_SERVICE = null; // 打印线程

	public static void start() {
		AppConf appconf = AppUploadJar.appConf;
		UPLOAD_SERVICE = Executors.newFixedThreadPool(appconf.uploadTdCount);
		PRINT_SERVICE = Executors.newCachedThreadPool();
		System.out.println("start scan dir:" + appconf.dir);
		scanDir(new File(appconf.dir));
		// System.out.println("scan dir end:" + appconf.dir);
	}

	private static void scanDir(File JAR_DIR) {
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
						submitUpload(new UploadJarFiles(file.getParentFile()));
						break;// 防止同目录重复扫描
					}
				}
			}
		}
	}

	/**
	 * 提交一个上传任务
	 * 
	 * @param task
	 */
	public static void submitUpload(Runnable task) {
		UPLOAD_SERVICE.submit(task);
	}

	/**
	 * 提交一个打印任务
	 * 
	 * @param task
	 */
	public static void submitPrint(Runnable task) {
		PRINT_SERVICE.submit(task);
	}
}
