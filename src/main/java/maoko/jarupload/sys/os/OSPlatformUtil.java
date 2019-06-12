package maoko.jarupload.sys.os;

/**
 * 操作系统类型获取助手
 * 
 * @author fanpei
 * @date 2019年1月7日下午10:02:44
 */
public class OSPlatformUtil {

	/**
	 * 获取操作系统名字
	 * 
	 * @return 操作系统名
	 */
	public static EOsType getOSType() {
		if (isAix()) {
			_instance.platform = EOsType.AIX;
		} else if (isDigitalUnix()) {
			_instance.platform = EOsType.Digital_Unix;
		} else if (isFreeBSD()) {
			_instance.platform = EOsType.FreeBSD;
		} else if (isHPUX()) {
			_instance.platform = EOsType.HP_UX;
		} else if (isIrix()) {
			_instance.platform = EOsType.Irix;
		} else if (isLinux()) {
			_instance.platform = EOsType.Linux;
		} else if (isMacOS()) {
			_instance.platform = EOsType.Mac_OS;
		} else if (isMacOSX()) {
			_instance.platform = EOsType.Mac_OS_X;
		} else if (isMPEiX()) {
			_instance.platform = EOsType.MPEiX;
		} else if (isNetWare()) {
			_instance.platform = EOsType.NetWare_411;
		} else if (isOpenVMS()) {
			_instance.platform = EOsType.OpenVMS;
		} else if (isOS2()) {
			_instance.platform = EOsType.OS2;
		} else if (isOS390()) {
			_instance.platform = EOsType.OS390;
		} else if (isOSF1()) {
			_instance.platform = EOsType.OSF1;
		} else if (isSolaris()) {
			_instance.platform = EOsType.Solaris;
		} else if (isSunOS()) {
			_instance.platform = EOsType.SunOS;
		} else if (isWindows()) {
			_instance.platform = EOsType.Windows;
		} else {
			_instance.platform = EOsType.Others;
		}
		return _instance.platform;
	}

	private static String OS = System.getProperty("os.name").toLowerCase();

	private static OSPlatformUtil _instance = new OSPlatformUtil();

	private EOsType platform;

	public static boolean isLinux() {
		return OS.indexOf("linux") >= 0;
	}

	public static boolean isMacOS() {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") < 0;
	}

	public static boolean isMacOSX() {
		return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0 && OS.indexOf("x") > 0;
	}

	public static boolean isWindows() {
		return OS.indexOf("windows") >= 0;
	}

	public static boolean isOS2() {
		return OS.indexOf("os/2") >= 0;
	}

	public static boolean isSolaris() {
		return OS.indexOf("solaris") >= 0;
	}

	public static boolean isSunOS() {
		return OS.indexOf("sunos") >= 0;
	}

	public static boolean isMPEiX() {
		return OS.indexOf("mpe/ix") >= 0;
	}

	public static boolean isHPUX() {
		return OS.indexOf("hp-ux") >= 0;
	}

	public static boolean isAix() {
		return OS.indexOf("aix") >= 0;
	}

	public static boolean isOS390() {
		return OS.indexOf("os/390") >= 0;
	}

	public static boolean isFreeBSD() {
		return OS.indexOf("freebsd") >= 0;
	}

	public static boolean isIrix() {
		return OS.indexOf("irix") >= 0;
	}

	public static boolean isDigitalUnix() {
		return OS.indexOf("digital") >= 0 && OS.indexOf("unix") > 0;
	}

	public static boolean isNetWare() {
		return OS.indexOf("netware") >= 0;
	}

	public static boolean isOSF1() {
		return OS.indexOf("osf1") >= 0;
	}

	public static boolean isOpenVMS() {
		return OS.indexOf("openvms") >= 0;
	}
}
