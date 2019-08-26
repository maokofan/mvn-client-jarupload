package maoko.jarupload.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;

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

	public MvnInfo(File pom) {
		this.pom = pom;
	}

	private final static int GROUPIDCOUNT = "<groupId>".length();
	private final static int ARTIFACTIDCOUNT = "<artifactId>".length();
	private final static int VERSIONCOUNT = "<version>".length();

	public String readInfo() throws FileNotFoundException, IOException {
		String mvnInfo = "";
		int infoCount = 0;
		List<String> lines = IOUtils.readLines(new FileInputStream(pom), Charset.forName("utf-8"));
		if (lines != null) {
			//过滤<parent></parent>
			boolean parentFlag = false;
			for (String line : lines) {
				line = line.trim();
				if(line.indexOf("<parent>") != -1){
					parentFlag = true;
				}
				if(!parentFlag){
					int startIndex = -1;
					int endIndex = -1;
					if ((startIndex = line.indexOf("<groupId>")) != -1) {
						infoCount++;
						endIndex = line.indexOf("</groupId>");
						groupId = line.substring(startIndex + GROUPIDCOUNT, endIndex);
						continue;
					} else if ((startIndex = line.indexOf("<artifactId>")) != -1) {
						infoCount++;
						endIndex = line.indexOf("</artifactId>");
						artifactId = line.substring(startIndex + ARTIFACTIDCOUNT, endIndex);
						continue;
					} else if ((startIndex = line.indexOf("<version>")) != -1) {
						infoCount++;
						endIndex = line.indexOf("</version>");
						version = line.substring(startIndex + VERSIONCOUNT, endIndex);
						continue;
					}
					if (infoCount == 3) {
						StringBuilder infoSb = new StringBuilder();
						infoSb.append(" -DgroupId=").append(groupId);
						infoSb.append(" -DartifactId=").append(artifactId);
						infoSb.append(" -Dversion=").append(version);
						mvnInfo = infoSb.toString();
						break;
					}
				}
				if(line.indexOf("</parent>") != -1){
					parentFlag = false;
				}
			}

		}

		return mvnInfo;
	}

	public static void main(String[] args) {
		MvnInfo info = new MvnInfo(new File(
				"D:\\softdependcy\\maven\\repository\\org\\codehaus\\plexus\\plexus\\1.0.4\\plexus-1.0.4.pom"));
		try {
			System.out.println(info.readInfo());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
