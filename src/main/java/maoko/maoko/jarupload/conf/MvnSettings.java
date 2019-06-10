package maoko.maoko.jarupload.conf;

import java.io.File;
import java.io.PrintWriter;

/**
 * 生成本地的mvn settings
 * 
 * @dscr
 * @author fanpei
 * @time 2019年6月10日 下午4:27:26
 *
 */
public class MvnSettings {
	private final static String settingsFile = "/conf/mvnSeetings.xml";

	private static String settings = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
			+ "<settings xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\" \r\n"
			+ "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \r\n"
			+ "    xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">"
			+ "{0}" + "</settings>";

	private static String servers = "\r\n<server>\r\n" //
			+ "      <id>{0}</id>\r\n"//
			+ "      <username>{1}</username>\r\n" //
			+ "      <password>{2}</password>\r\n"//
			+ "</server>\r\n";

	public static String SETTINGS_PATH;// xml 路径

	public static void init(RepositoryUser user) {
		genLocalSeetingsXml(user);
	}

	public static void genLocalSeetingsXml(RepositoryUser user) {
		String runPath = System.getProperty("user.dir");
		SETTINGS_PATH = runPath + settingsFile;

		PrintWriter pw = null;
		try {
			// 组装settings字符串
			String serverWithUser = servers.replace("{0}", user.getId())//
					.replace("{1}", user.getUsername())//
					.replace("{2}", user.getPass());
			String settingsWithUser = settings.replace("{0}", serverWithUser);

			// 递归创建文件
			File xmlf = new File(SETTINGS_PATH);
			if (!xmlf.exists()) {
				if (!xmlf.getParentFile().exists())
					xmlf.getParentFile().mkdirs();
				xmlf.createNewFile();
			}

			// 写文件
			pw = new PrintWriter(SETTINGS_PATH);
			pw.print(settingsWithUser);
			pw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}

	}

}
