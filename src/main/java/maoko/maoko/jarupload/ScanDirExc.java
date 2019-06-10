package maoko.maoko.jarupload;

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

	private AppConf appconf;
	/**
	 * 上传执行线程服务
	 */
	public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);// 线程池越多 上传越快

	public void start() {
		System.out.println("start scan dir:" + appconf.dir);
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
