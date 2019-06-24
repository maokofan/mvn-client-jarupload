package maoko.jarupload;

import java.util.List;

/**
 * @dscr 异步读取数据回调
 * @author fanpei
 * @time 2019年6月21日 下午3:03:28
 *
 */
@Deprecated
public interface AsyncCallback {

	/**
	 * 返回行字符串结果
	 * 
	 * @return
	 * @throws Exception
	 */
	void returnLineStrs(List<String> results) throws Exception;
}
