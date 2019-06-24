package maoko.jarupload.utils;

import java.io.File;
import java.io.PrintWriter;

public class FileUtil {
	public static void writeStr2File(String content, String path) {
		PrintWriter pw = null;
		try {

			// 递归创建文件
			File xmlf = new File(path);
			if (!xmlf.exists()) {
				if (!xmlf.getParentFile().exists())
					xmlf.getParentFile().mkdirs();
				xmlf.createNewFile();
			}

			// 写文件
			pw = new PrintWriter(path);
			pw.print(content);
			pw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}
}
