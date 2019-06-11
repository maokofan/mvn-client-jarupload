package maoko.maoko.jarupload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import maoko.maoko.jarupload.conf.MvnCmd;

/**
 * 上传一个jar,包含多个文件
 * 
 * @dscr
 * @author fanpei
 * @time 2019年6月10日 下午5:31:11
 *
 */
public class UploadJarFiles implements Runnable {

	private static Logger logger = null;
	private static Pattern DATE_PATTERN = null;

	public static void init() {
		logger = LoggerFactory.getLogger(UploadJarFiles.class);
		DATE_PATTERN = Pattern.compile("-[\\d]{8}\\.[\\d]{6}-");
	}

	private File jarDir;

	/**
	 * @param path jar父目录
	 */
	public UploadJarFiles(File jarDir) {
		this.jarDir = jarDir;
	}

	public void run() {
		File pom = null;
		File jar = null;
		File source = null;
		File javadoc = null;
		File[] files = jarDir.listFiles();
		// 忽略日期快照版本，如 xxx-mySql-2.2.6-20170714.095105-1.jar
		for (File file : files) {
			String name = file.getName();
			if (DATE_PATTERN.matcher(name).find()) {
				// skip
			} else if (name.endsWith(".pom")) {
				pom = file;
			} else if (name.endsWith("-javadoc.jar")) {
				javadoc = file;
			} else if (name.endsWith("-sources.jar")) {
				source = file;
			} else if (name.endsWith(".jar")) {
				jar = file;
			}
		}
		if (pom != null && jar != null) {
			deploy(pom, jar, source, javadoc);
		}
	}

	public static boolean packingIsPom(File pom) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(pom)));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().indexOf("<packaging>pom</packaging>") != -1) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * 上传包
	 * 
	 * @param pom
	 */
	public void deploy(final File pom, final File jar, final File source, final File javadoc) {
		// logger.info("ready to upload jar dir:" + pom.getAbsolutePath());
		StringBuffer cmd = new StringBuffer(MvnCmd.BASE_CMD_STR);
		cmd.append(" -DpomFile=").append(pom.getPath());
		cmd.append(" -Dpackaging=jar -Dfile=").append(jar.getPath());// 当有bundle类型时，下面的配置可以保证上传的jar包后缀为.jar
		cmd.append(" -Dfile=").append(pom.getPath());
		if (source != null) {
			cmd.append(" -Dsources=").append(source.getPath());
		}
		if (javadoc != null) {
			cmd.append(" -Djavadoc=").append(javadoc.getPath());
		}
		upload(pom, cmd);
		// logger.info("end to upload jar dir:" + pom.getAbsolutePath());
	}

	private void upload(final File pom, StringBuffer cmd) {
		try {
			final Process proc = MvnCmd.DOSCMD_EXCUTOR.exec(cmd.toString(), null, pom.getParentFile());
			InputStream inputStream = proc.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line;
			StringBuffer logBuffer = new StringBuffer();
			logBuffer.append("\n\n\n========================================================\n");
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("[INFO]") || line.startsWith("Upload")) {
					logBuffer.append(Thread.currentThread().getName() + " : " + line + "\n");
				}
			}
			logger.info(logBuffer.toString());
			int result = proc.waitFor();
			if (result != 0) {
				logger.error("上传失败：" + pom.getAbsolutePath());
			} else
				logger.info("上传成功:" + this.jarDir.getAbsolutePath());
		} catch (Exception e) {
			logger.error("上传失败：" + pom.getAbsolutePath(), e);
			e.printStackTrace();
		}
	}

}
