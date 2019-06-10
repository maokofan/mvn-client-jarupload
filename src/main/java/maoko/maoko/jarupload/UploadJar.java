package maoko.maoko.jarupload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import maoko.maoko.jarupload.conf.MvnCmd;

/**
 * 上传一个jar
 * 
 * @dscr
 * @author fanpei
 * @time 2019年6月10日 下午5:31:11
 *
 */
public class UploadJar implements Runnable {
	public static final Runtime DOS_CMD = Runtime.getRuntime();
	public static final Writer ERROR;// 注意多线程安全

	static {
		Writer err = null;
		try {
			err = new OutputStreamWriter(new FileOutputStream("deploy-error.log"), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		ERROR = err;
	}

	public void run() {
		final File pom = null;
		final File jar = null;
		final File source = null;
		final File javadoc = null;

		StringBuffer cmd = new StringBuffer(MvnCmd.BASE_CMD);
		cmd.append(" -DpomFile=").append(pom.getPath());
		if (jar != null) {
			// 当有bundle类型时，下面的配置可以保证上传的jar包后缀为.jar
			cmd.append(" -Dpackaging=jar -Dfile=").append(jar.getPath());
		} else {
			cmd.append(" -Dfile=").append(pom.getPath());
		}
		if (source != null) {
			cmd.append(" -Dsources=").append(source.getPath());
		}
		if (javadoc != null) {
			cmd.append(" -Djavadoc=").append(javadoc.getPath());
		}
		try {
			final Process proc = DOS_CMD.exec(cmd.toString(), null, pom.getParentFile());
			InputStream inputStream = proc.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line;
			StringBuffer logBuffer = new StringBuffer();
			logBuffer.append("\n\n\n=======================================\n");
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("[INFO]") || line.startsWith("Upload")) {
					logBuffer.append(Thread.currentThread().getName() + " : " + line + "\n");
				}
			}
			System.out.println(logBuffer);
			int result = proc.waitFor();
			if (result != 0) {
				error("上传失败：" + pom.getAbsolutePath());
			}
		} catch (IOException e) {
			error("上传失败：" + pom.getAbsolutePath());
			e.printStackTrace();
		} catch (InterruptedException e) {
			error("上传失败：" + pom.getAbsolutePath());
			e.printStackTrace();
		}
	}

	public static void error(String error) {
		try {
			System.err.println(error);
			ERROR.write(error + "\n");
			ERROR.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
