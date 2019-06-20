package maoko.jarupload;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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

	public static ThreadPoolExecutor UPLOAD_SERVICE = null;// 上传线程

	public static ThreadPoolExecutor PRINT_SERVICE = null; // 打印线程

	public static void start() throws InterruptedException {
		AppConf appconf = AppUploadJar.appConf;
		UPLOAD_SERVICE = (ThreadPoolExecutor) Executors.newFixedThreadPool(appconf.uploadTdCount);
		PRINT_SERVICE = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		System.out.println("start scan dir:" + appconf.dir);
		scanDir(new File(appconf.dir));
		stop();
	}

	public static void stop() throws InterruptedException {
		while (true) {
			Thread.sleep(60000);// 60秒
			if (UPLOAD_SERVICE.getQueue().isEmpty() && PRINT_SERVICE.getQueue().isEmpty()) {
				UPLOAD_SERVICE.shutdown();
				PRINT_SERVICE.shutdown();
				System.out.println("所有的线程结束！");
				break;
			}
		}

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
						if (file.getName().endsWith(".pom") || file.getName().endsWith(".jar")) {// 以pom结束
							excuteUpload(new UploadJarFiles(file.getParentFile()));
							break;// 防止同目录重复扫描
						}
					}
				}
			}
		}

	}

	/**
	 * 执行上传任务
	 * 
	 * @param task
	 */
	public static void excuteUpload(Runnable task) {
		UPLOAD_SERVICE.submit(task);
	}

	/**
	 * 执行打印任务
	 * 
	 * @param task
	 */
	public static void excutePrint(Runnable task) {
		PRINT_SERVICE.submit(task);
	}
}
