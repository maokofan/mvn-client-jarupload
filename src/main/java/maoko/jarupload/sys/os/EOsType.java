package maoko.jarupload.sys.os;


/**
 * 操作系统类型
 * 
 * @author fanpei
 * @date 2019年1月7日下午10:04:21
 */
public enum EOsType {
	Any("any"), //
	Linux("Linux"), //
	Mac_OS("Mac OS"), //
	Mac_OS_X("Mac OS X"), //
	Windows("Windows"), //
	OS2("OS/2"), //
	Solaris("Solaris"), //
	SunOS("SunOS"), //
	MPEiX("MPE/iX"), //
	HP_UX("HP-UX"), //
	AIX("AIX"), //
	OS390("OS/390"), //
	FreeBSD("FreeBSD"), //
	Irix("Irix"), //
	Digital_Unix("Digital Unix"), //
	NetWare_411("NetWare"), //
	OSF1("OSF1"), //
	OpenVMS("OpenVMS"), //
	Others("Others");

	private EOsType(String desc) {
		this.description = desc;
	}

	public String toString() {
		return description;
	}

	private String description;
}
