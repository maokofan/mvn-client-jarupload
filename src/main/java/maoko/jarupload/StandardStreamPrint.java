package maoko.jarupload;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 标准输出打印
 * 
 * @dscr
 * @author fanpei
 * @time 2019年6月13日 上午10:52:19
 *
 */
public class StandardStreamPrint implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(UploadJarFiles.class);
	private InputStream in;

	public StandardStreamPrint(InputStream _in) {
		this.in = _in;
	}

	public void run() {
		try {
			List<String> lines = IOUtils.readLines(in, Charset.forName("utf-8"));
			if (lines != null) {
				StringBuilder builder = new StringBuilder(System.lineSeparator());
				for (String line : lines) {
					if (line.startsWith("["))
						builder.append(line).append(System.lineSeparator());
				}
				logger.info(builder.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
