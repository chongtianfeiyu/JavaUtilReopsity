package com.yang.base.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.yang.base.check.CheckUtil;

/**
 * @Description: 网络工具类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-22 上午10:35:20 
 */
public class NetworkUtil {
	
	private static final  Logger log =Logger.getLogger(NetworkUtil.class);

	private static Map<Integer, String> routeProtocols;

	private static final String fdbOid = "1.3.6.1.2.1.17.4.3.1.2";

	public static final long ipTotal = Long.parseLong("4294967296");

	public static String getRouteProtocolName(int index) {
		return ((String) routeProtocols.get(Integer.valueOf(index)));
	}

	public static String getRouteType(int type) {
		if (type == 4)
			return "间接";
		if (type == 3)
			return "直接";
		if (type == 2)
			return "无效";
		if (type == 1)
			return "其他";
		return "";
	}

	public static long ipToLong(String ipAddress) {
		int[] ipSegment = parseIp(ipAddress);
		if (ipSegment == null)
			return 0L;

		long longIp = 0L;
		int k = 24;
		for (int i = 0; i < ipSegment.length; ++i) {
			longIp += (ipSegment[i] << k);
			k -= 8;
		}
		return longIp;
	}

	public static String longToIp(long ip) {
		int[] b = new int[4];
		b[0] = (int) (ip >> 24 & 0xFF);
		b[1] = (int) (ip >> 16 & 0xFF);
		b[2] = (int) (ip >> 8 & 0xFF);
		b[3] = (int) (ip & 0xFF);
		return b[0] + "." + b[1] + "." + b[2] + "." + b[3];
	}

	public static int[] parseIp(String ipAddress) {
		if (!(CheckUtil.checkIp(ipAddress)))
			return null;
		int[] ipSegment = new int[4];

		StringTokenizer st = new StringTokenizer(ipAddress, ".");
		for (int i = 0; i < 4; ++i)
			ipSegment[i] = Integer.parseInt(st.nextToken());
		return ipSegment;
	}

	public static String formatIfDescr(String ifDescr) {
		if (ifDescr == null)
			return "unknown";
		if (ifDescr.length() > 50)
			ifDescr = ifDescr.substring(0, 50);
		if (ifDescr.indexOf("'") >= 0) {
			ifDescr = ifDescr.replace('\'', '_');
		}
		ifDescr = ifDescr.trim();
		if (ifDescr.startsWith("TenGigabitEthernet"))
			return ifDescr.replaceFirst("TenGigabitEthernet", "TGi");
		if (ifDescr.startsWith("GigabitEthernet"))
			return ifDescr.replaceFirst("GigabitEthernet", "Gi");
		if (ifDescr.startsWith("FastEthernet")) {
			return ifDescr.replaceFirst("FastEthernet", "Fa");
		}
		return ifDescr;
	}

	public static int getSubnetIPTotal(String netMask) {
		int[] masks = parseIp(netMask);
		if (masks == null)
			return 0;

		int ipTotal = 0;
		for (int i = 0; i < 4; ++i) {
			if (masks[i] != 255) {
				if (i == 2) {
					ipTotal = (255 - masks[i]) * 256 + 256;
					break;
				}
				if (i != 3)
					break;
				ipTotal = 256 - masks[i];
				break;
			}
		}

		return (ipTotal - 1);
	}

	public static boolean ipInScope(String ip, String startIp, String endIp) {
		long ipLong = ipToLong(ip);
		long startIpLong = ipToLong(startIp);
		long endIpLong = ipToLong(endIp);

		return ((ipLong >= startIpLong) && (ipLong <= endIpLong));
	}

	public static String ifInSubnet(String netAddress, String netMask,
			List<String> ips) {
		for (String ip : ips) {
			if (CheckUtil.isValidIP(netAddress, netMask, ip))
				return ip;
		}
		return null;
	}

	public static String ifInSubnet(String netAddress, String netMask, String ip) {
		if (CheckUtil.isValidIP(netAddress, netMask, ip))
			return ip;
		return null;
	}

	public static String getFdbOid(String mac) {
		StringBuffer oidString = new StringBuffer(30);
		oidString.append("1.3.6.1.2.1.17.4.3.1.2");
		try {
			String[] macs = mac.split(":");
			for (int i = 0; i < 6; ++i) {
				oidString.append(".");
				oidString.append(Integer.parseInt(macs[i], 16));
			}
		} catch (NoSuchElementException e) {
			return null;
		}
		return oidString.toString();
	}

	/**
	 * @Description: 设置 本机IP ,还没完成. 
	 * 参考 : http://blog.sina.com.cn/s/blog_521a28ba0100bkaq.html
	 * @param newip
	 * @throws Exception
	 */
	private static void setIP(String newip) throws Exception {
		Runtime.getRuntime().exec(
				"netsh interface ip set addr \"本地连接\" static " + newip
						+ " 255.255.255.0 192.168.32.1 1");
	}

	static {
		routeProtocols = new HashMap();
		routeProtocols.put(Integer.valueOf(1), "other");
		routeProtocols.put(Integer.valueOf(2), "local");
		routeProtocols.put(Integer.valueOf(3), "netmgmt");
		routeProtocols.put(Integer.valueOf(4), "icmp");
		routeProtocols.put(Integer.valueOf(5), "egp");
		routeProtocols.put(Integer.valueOf(6), "ggp");
		routeProtocols.put(Integer.valueOf(7), "hello");
		routeProtocols.put(Integer.valueOf(8), "rip");
		routeProtocols.put(Integer.valueOf(9), "is-is");
		routeProtocols.put(Integer.valueOf(10), "es-is");
		routeProtocols.put(Integer.valueOf(11), "ciscoIgrp");
		routeProtocols.put(Integer.valueOf(12), "bbnSpfIgp");
		routeProtocols.put(Integer.valueOf(13), "ospf");
		routeProtocols.put(Integer.valueOf(14), "bgp");
	}

	/**
	 * @Description: 得到机机所有的IP V4
	 * @return
	 * @throws SocketException
	 */
	public static List<InetAddress> getAllLocalIP() throws SocketException {
		List<InetAddress> inetAddress = new ArrayList<InetAddress>();
		Enumeration<NetworkInterface> allNetInterfaces;
		allNetInterfaces = NetworkInterface.getNetworkInterfaces();

		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
					.nextElement();
			System.out.println(netInterface.getName());
			Enumeration<InetAddress> addresses = netInterface
					.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					System.out.println("本机的IP = " + ip.getHostAddress());
					inetAddress.add(ip);
				}
			}
		}
		return inetAddress;
	}

	/**
	 * @Description: 查看IP 是否本机的
	 * @param hostAddress
	 * @return
	 */
	public static boolean isLocalIp(String hostAddress) {
		try {
			List<InetAddress> inetAddress = NetworkUtil.getAllLocalIP();

			for (InetAddress inetAddress2 : inetAddress) {
 log.info(inetAddress2.getHostAddress());
				if (inetAddress2.getHostAddress().equals(hostAddress) ) {
					return true;
				}
			}
			return false;

		} catch (SocketException e) {
			return false;
		}

	}
}
