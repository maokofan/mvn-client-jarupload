package maoko.jarupload.conf;

import java.io.File;

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
			+ " -DrepositoryId={2}"//
			+ " -DgeneratePom=false";//

	public static String BASE_CMD_STR;// mvn 命令
	public static Runtime DOSCMD_EXCUTOR = null;

	public static void init() {
		String settingsXml = MvnSettings.SETTINGS_PATH;
		BASE_CMD_STR = DOS_CMD.replace("{0}", settingsXml)//
				.replace("{1}", AppUploadJar.appConf.repository_durl)//
				.replace("{2}", AppUploadJar.appConf.repository_id);
		DOSCMD_EXCUTOR = Runtime.getRuntime();
	}

	public static String getFullCmdStr(final File pom, final File jar, final File source, final File javadoc) {
		StringBuilder cmd = new StringBuilder(MvnCmd.BASE_CMD_STR);
		if (jar != null)
			cmd.append(" -Dfile=").append(jar.getName());// 当有bundle类型时，下面的配置可以保证上传的jar包后缀为.jar
		if (pom != null)
			cmd.append(" -DpomFile=").append(pom.getName());
		cmd.append(" -Dpackaging=jar");
		if (source != null) {
			cmd.append(" -Dsources=").append(source.getName());
		}
		if (javadoc != null) {
			cmd.append(" -Djavadoc=").append(javadoc.getName());
		}
		return cmd.toString();
	}

}
