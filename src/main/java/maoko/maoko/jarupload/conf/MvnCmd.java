package maoko.maoko.jarupload.conf;

/**
 * @dscr mvn 命令
 * @author fanpei
 * @time 2019年6月10日 下午6:56:10
 *
 */
public class MvnCmd {
	private static final String DOS_CMD = "cmd /c mvn " //
			+ "-s {0} " //
			+ "deploy:deploy-file "//
			+ "-Durl={1}"//
			+ "-DrepositoryId={2} "//
			+ "-DgeneratePom=false";//

	public static String BASE_CMD;// mvn 命令

	public static void init(String settingsXml, String durl, String repositoryId) {
		BASE_CMD = DOS_CMD.replace("{0}", settingsXml)//
				.replace("{1}", durl)//
				.replace("{2}", repositoryId);
	}
}
