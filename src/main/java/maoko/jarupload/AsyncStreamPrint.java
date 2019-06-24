package maoko.jarupload;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步读取流数据
 * 
 * @dscr
 * @author fanpei
 * @time 2019年6月13日 上午10:52:19
 *
 */
public class AsyncStreamPrint implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(UploadJarFiles.class);
	private InputStream in;
	private String patern;

	public AsyncStreamPrint(InputStream _in) {
		this.in = _in;
	}

	public AsyncStreamPrint(InputStream _in, String patern) {
		this.in = _in;
		this.patern = patern;
	}

	public void run() {
		String result = null;
		List<String> lines = null;
		try {
			lines = IOUtils.readLines(in, Charset.forName("utf-8"));
			if (lines != null && !lines.isEmpty()) {
				StringBuilder builder = new StringBuilder(System.lineSeparator());
				for (String line : lines) {
					if (patern != null && !"".equals(patern) && line.startsWith(patern))
						builder.append(line).append(System.lineSeparator());
					else
						builder.append(line);
				}
				result = builder.toString();
				logger.info(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
