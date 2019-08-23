package maoko.jarupload.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import maoko.jarupload.AppUploadJar;
import maoko.jarupload.AsyncStreamPrint;
import maoko.jarupload.TheadPoolExc;
import maoko.jarupload.sys.os.EOsType;
import maoko.jarupload.sys.os.OSPlatformUtil;

/**
 * @dscr mvn 命令
 * @author fanpei
 * @time 2019年6月10日 下午6:56:10
 *
 */
public class MvnCmd {
	private static String DOS_CMD = "mvn" //
			+ " -s {1}" //
			+ "  deploy:deploy-file"//
			+ " -Durl={2}"//
			+ " -DrepositoryId={3}";

	private static String BASE_CMD_STR;// mvn 命令
	private static String BASE_CMD_STR_FULL;// 带有完整错误信息的mvn 命令

	public static Runtime DOSCMD_EXCUTOR = null;

	public static void init() throws IOException, InterruptedException {
		DOSCMD_EXCUTOR = Runtime.getRuntime();
		String settingsXml = MvnSettings.SETTINGS_PATH;
		EOsType ostype = OSPlatformUtil.getOSType();
		if (EOsType.Linux == ostype || EOsType.Mac_OS_X == ostype) {
			//skip
		} else if (EOsType.Windows == ostype) {
			DOS_CMD = "cmd /c " + DOS_CMD;
		}
		BASE_CMD_STR = DOS_CMD.replace("{1}", settingsXml)//
				.replace("{2}", AppUploadJar.appConf.repository_durl)//
				.replace("{3}", AppUploadJar.appConf.repository_id);
		BASE_CMD_STR_FULL = BASE_CMD_STR.replace("/c mvn", "/c mvn -e -X");
		// setLocalMvn(runPath);
	}

	public static void excuteCmd(String cmd) throws InterruptedException {
		excuteCmd(cmd, null);
	}

	public static void excuteCmd(String cmd, File workDir) throws InterruptedException {
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec(cmd, null, workDir);
			// 线程读取输出流
			TheadPoolExc.excutePrint(new AsyncStreamPrint(proc.getInputStream()));
			TheadPoolExc.excutePrint(new AsyncStreamPrint(proc.getErrorStream()));
			proc.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			/*
			 * if (proc != null) proc.destroyForcibly();
			 */
		}
	}

	/**
	 * @param pom
	 * @param jar
	 * @param source
	 * @param javadoc
	 * @param withFull 是否带完整错误信息提示
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getFullCmdStr(final File pom, final File jar, final File source, final File javadoc,
			boolean withFull) throws FileNotFoundException, IOException {
		StringBuilder cmd = null;
		if (withFull) {
			cmd = new StringBuilder(MvnCmd.BASE_CMD_STR_FULL);
		} else
			cmd = new StringBuilder(MvnCmd.BASE_CMD_STR);
		if (jar != null) {
			cmd.append(" -Dfile=").append(jar.getName());// 当有bundle类型时，下面的配置可以保证上传的jar包后缀为.jar
			cmd.append(" -Dpackaging=jar");
			if (pom != null) {
				cmd.append(" -DgeneratePom=false");
				cmd.append(" -DpomFile=").append(pom.getName());
			} else {// pom不存在 自动补全mvn信息-ps:不支持此情况
				// cmd.append(" -DgeneratePom=true");
				// MvnInfo info = new MvnInfo(pom);
				// cmd.append(info.readInfo());
			}
		} else if (pom != null) {// 打包上传单个pom文件时
			cmd.append(" -Dfile=").append(pom.getName());// 当有bundle类型时，下面的配置可以保证上传的jar包后缀为.jar
			cmd.append(" -Dpackaging=pom");
			cmd.append(" -DgeneratePom=false");
			MvnInfo info = new MvnInfo(pom);
			cmd.append(info.readInfo());
		}

		if (source != null) {
			cmd.append(" -Dsources=").append(source.getName());
		}
		if (javadoc != null) {
			cmd.append(" -Djavadoc=").append(javadoc.getName());
		}
		return cmd.toString();
	}

}
