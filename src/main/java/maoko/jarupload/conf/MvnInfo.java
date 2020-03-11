package maoko.jarupload.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * @dscr 文件mvn信息
 * @author fanpei
 * @time 2019年6月20日 上午11:33:00
 *
 */
public class MvnInfo {

	private String groupId;
	private String artifactId;
	private String version;

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	private File pom;

	public MvnInfo(File pom) {this.pom = pom;}

	private final static int GROUPIDCOUNT = "<groupId>".length();
	private final static int ARTIFACTIDCOUNT = "<artifactId>".length();
	private final static int VERSIONCOUNT = "<version>".length();

//	TODO: 改成model解析
	public String readInfo() throws FileNotFoundException, IOException, XmlPullParserException {
		String mvnInfo = "";
		FileInputStream fis = new FileInputStream(pom);
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model = reader.read(fis);

		groupId = model.getGroupId();
		if(groupId == null || groupId.isEmpty())
		{
			groupId = model.getParent().getGroupId();
		}

		artifactId = model.getArtifactId();
		if(artifactId ==null || artifactId.isEmpty())
		{
			artifactId=model.getParent().getArtifactId();
		}

		version = model.getVersion();
		if(version == null || version.isEmpty())
		{
			version = model.getParent().getVersion();
		}

		if(!groupId.isEmpty() && !artifactId.isEmpty() && !version.isEmpty())
		{
			StringBuilder infoSb = new StringBuilder();
			infoSb.append(" -DgroupId=").append(groupId);
			infoSb.append(" -DartifactId=").append(artifactId);
			infoSb.append(" -Dversion=").append(version);
			mvnInfo = infoSb.toString();
		}

		return mvnInfo;
	}

	public static void main(String[] args) {
		MvnInfo info = new MvnInfo(new File(
				"D:\\softdependcy\\maven\\repository\\org\\codehaus\\plexus\\plexus\\1.0.4\\plexus-1.0.4.pom"));
		try {
			System.out.println(info.readInfo());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}
