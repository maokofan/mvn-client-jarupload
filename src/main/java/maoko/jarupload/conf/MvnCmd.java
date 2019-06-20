package maoko.jarupload.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import maoko.jarupload.AppUploadJar;

/**
 * @dscr mvn 命令
 * @author fanpei
 * @time 2019年6月10日 下午6:56:10
 *
 */
public class MvnCmd {
	private static final String DOS_CMD = "cmd /c mvn" //
			+ " -s {0}" //
			+ " deploy:deploy-file"//
			+ " -Durl={1}"//
			+ " -DrepositoryId={2}";

	public static String BASE_CMD_STR;// mvn 命令
	public static Runtime DOSCMD_EXCUTOR = null;

	public static void init() {
		String settingsXml = MvnSettings.SETTINGS_PATH;
		BASE_CMD_STR = DOS_CMD.replace("{0}", settingsXml)//
				.replace("{1}", AppUploadJar.appConf.repository_durl)//
				.replace("{2}", AppUploadJar.appConf.repository_id);
		DOSCMD_EXCUTOR = Runtime.getRuntime();
	}

	public static String getFullCmdStr(final File pom, final File jar, final File source, final File javadoc)
			throws FileNotFoundException, IOException {
		StringBuilder cmd = new StringBuilder(MvnCmd.BASE_CMD_STR);
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
