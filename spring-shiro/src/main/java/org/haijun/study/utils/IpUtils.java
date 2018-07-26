package org.haijun.study.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 关于其他https://www.cnblogs.com/gotodsp/p/4320928.html
 */
public class IpUtils {

    public static void main(String[] args) {
        String broadCast = getLocalBroadCast();
        System.out.println(broadCast);
        Long ipLong = getIpFromString(broadCast);
        System.out.println(ipLong);
        System.out.println(getIpFromLong(ipLong));
        System.out.println(longToIP(ipLong));
        // 192.168.1.53/27 网路地址=192.168.1.32 ；广播地址=192.168.1.63
        System.out.println("该ip的网路地址为"+netAddress("192.168.1.53","27"));
        System.out.println("该网路的广播地址="+getBroadcastAddress("192.168.1.53",getMaskMap(27+"")));
        System.out.println("该网路掩码能包含ip数量为："+getIpCount(16+""));
        System.out.println("该网路段包含ip="+parseIpMaskRange("192.168.1.32","27"));
    }

    /**
     * 功能：根据位数返回IP总数
     * 格式：parseIpMaskRange("192.192.192.1", "23")
     */
    public static int getIpCount(String mask) {
        return BigDecimal.valueOf(Math.pow(2, 32 - Integer.parseInt(mask))).setScale(0, BigDecimal.ROUND_DOWN).intValue();//IP总数，去小数点
    }

    /**
     * 功能：根据IP和位数返回该IP网段的所有IP
     * 格式：parseIpMaskRange("192.192.192.1.", "23")
     */
    public static List<String> parseIpMaskRange(String ip,String mask){
        List<String> list=new ArrayList<>();
        if ("32".equals(mask)) {
            list.add(ip);
        }else{
            String startIp=getBeginIpStr(ip, mask);
            String endIp=getEndIpStr(ip, mask);
            if (!"31".equals(mask)) {
                String subStart=startIp.split("\\.")[0]+"."+startIp.split("\\.")[1]+"."+startIp.split("\\.")[2]+".";
                String subEnd=endIp.split("\\.")[0]+"."+endIp.split("\\.")[1]+"."+endIp.split("\\.")[2]+".";
                startIp=subStart+(Integer.parseInt(startIp.split("\\.")[3])+1);
                endIp=subEnd+(Integer.parseInt(endIp.split("\\.")[3])-1);
            }
            list=parseIpRange(startIp, endIp);
        }
        return list;
    }
    public static List<String> parseIpRange(String ipfrom, String ipto) {
        List<String> ips = new ArrayList<String>();
        String[] ipfromd = ipfrom.split("\\.");
        String[] iptod = ipto.split("\\.");
        int[] int_ipf = new int[4];
        int[] int_ipt = new int[4];
        for (int i = 0; i < 4; i++) {
            int_ipf[i] = Integer.parseInt(ipfromd[i]);
            int_ipt[i] = Integer.parseInt(iptod[i]);
        }
        for (int A = int_ipf[0]; A <= int_ipt[0]; A++) {
            for (int B = (A == int_ipf[0] ? int_ipf[1] : 0); B <= (A == int_ipt[0] ? int_ipt[1]
                    : 255); B++) {
                for (int C = (B == int_ipf[1] ? int_ipf[2] : 0); C <= (B == int_ipt[1] ? int_ipt[2]
                        : 255); C++) {
                    for (int D = (C == int_ipf[2] ? int_ipf[3] : 0); D <= (C == int_ipt[2] ? int_ipt[3]
                            : 255); D++) {
                        ips.add(A + "." + B + "." + C + "." + D);
                    }
                }
            }
        }
        return ips;
    }
    /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的字符串表示
     */
    public static String getEndIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getEndIpLong(ip, maskBit));
    }
    /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的长整型表示
     */
    public static Long getEndIpLong(String ip, String maskBit)
    {
        return getBeginIpLong(ip, maskBit)
                + ~getIpFromString(getMaskByMaskBit(maskBit));
    }

    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的字符串表示
     */
    public static String getBeginIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getBeginIpLong(ip, maskBit));
    }
    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的长整型表示
     */
    public static Long getBeginIpLong(String ip, String maskBit)
    {
        return getIpFromString(ip) & getIpFromString(getMaskByMaskBit(maskBit));
    }
    /**
     * 根据掩码位获取掩码
     *
     * @param maskBit
     *            掩码位数，如"28"、"30"
     * @return
     */
    public static String getMaskByMaskBit(String maskBit)
    {
        return "".equals(maskBit) ? "error, maskBit is null !" : getMaskMap(maskBit);
    }


    /**
     * 功能：根据位数返回IP总数
     * 格式：isIP("192.192.192.1")
     */
    public static boolean isIP(String str) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }


    /**
     * 功能：判断一个IP是不是在一个网段下的
     * 格式：isInRange("192.168.8.3", "192.168.9.10/22");
     */
    public static boolean isInRange(String ip, String cidr) {
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);
        return (ipAddr & mask) == (cidrIpAddr & mask);
    }
    /**
     * 获取网络地址和广播地址
     * @param ip
     * @param mask
     * @return
     */
    public static String netAddress(String ip, String mask){
        String[] ipspit = ip.split("\\.");
        String[] maskspit = getMaskMap(mask).split("\\.");

        String netStr = "";
        int length = ipspit.length;
        for(int j=0 ; j<length;j++){
            int temp = Integer.valueOf(ipspit[j])&Integer.valueOf(maskspit[j]);
            netStr+=temp;
            if(j == length-1){
                continue;
            }
            netStr+=".";
        }
        return netStr;
    }

    /**
     * int 转二进制
     * @param data
     * @return
     */
    public static String ipConvertBety(int data){
        String binaryStr = "";
        if(data<0){
            //int quanduiz = ~data;
            binaryStr = Integer.toBinaryString(data);

            //int binaryInt = Integer.parseInt(binaryStr);//1111
            //System.out.println("该负数为"+binaryInt);
            /*binaryStr = String.format("%08d",binaryInt);
            int size = binaryStr.length();
            String coverStr = "";
            for(int i=0;i<size;i++){
                switch (binaryStr.charAt(i)){
                    case '0':
                        coverStr+="1";
                        break;
                    case '1':
                        coverStr+="0";
                        break;
                }
            }
            binaryStr = coverStr;*/
        }else{
            //System.out.println(Integer.bitCount(data));
            binaryStr = Integer.toBinaryString(data);
            int binaryInt = Integer.parseInt(binaryStr);//1111
            binaryStr = String.format("%08d",binaryInt);
        }

        return binaryStr;
    }
    /**
     * 获取本机广播地址，并自动区分Windows还是Linux操作系统
     * @return
     */
    public static String getLocalBroadCast(){
        String broadCastIp = null;
        try {
            Enumeration<?> netInterfaces = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) netInterfaces.nextElement();
                if (!netInterface.isLoopback()&& netInterface.isUp()) {
                    System.out.println("网卡名称="+netInterface.getName()+"   |   " + netInterface.getDisplayName() );
                    List<InterfaceAddress> interfaceAddresses = netInterface.getInterfaceAddresses();
                    for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                        //只有 IPv4 网络具有广播地址，因此对于 IPv6 网络将返回 null。
                        if(interfaceAddress.getBroadcast()!= null){
                            short yanma = interfaceAddress.getNetworkPrefixLength();
                            System.out.println(yanma+" 掩码："+getMaskMap(yanma+""));
                            System.out.println("本机 ip 地址="+interfaceAddress.getAddress().getHostAddress());
                            String temp = interfaceAddress.getBroadcast().getHostAddress();
                            if(! StringUtils.isEmpty(temp)){
                                broadCastIp = temp;
                            }

                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return broadCastIp;
    }

    /**
     * 把xx.xx.xx.xx类型的转为long类型的
     * @param ip
     * @return
     */
    public static Long getIpFromString(String ip) {
        Long ipLong = 0L;
        String ipTemp = ip;
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1, ipTemp.length());
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1, ipTemp.length());
        ipLong = ipLong * 256 + Long.parseLong(ipTemp);
        return ipLong;
    }

    // 将10进制整数形式转换成127.0.0.1形式的IP地址
    public static String longToIP(long longIP) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf(longIP >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }

    /**
     * 把long类型的Ip转为一般Ip类型：xx.xx.xx.xx
     * @param ip
     * @return
     */
    public static String getIpFromLong(Long ip) {
        String s1 = String.valueOf((ip & 4278190080L) / 16777216L);
        String s2 = String.valueOf((ip & 16711680L) / 65536L);
        String s3 = String.valueOf((ip & 65280L) / 256L);
        String s4 = String.valueOf(ip & 255L);
        return s1 + "." + s2 + "." + s3 + "." + s4;
    }

    public static String getMaskMap(String maskBit) {

        if ("1".equals(maskBit)) return "128.0.0.0";

        if ("2".equals(maskBit)) return "192.0.0.0";

        if ("3".equals(maskBit)) return "224.0.0.0";

        if ("4".equals(maskBit)) return "240.0.0.0";

        if ("5".equals(maskBit)) return "248.0.0.0";

        if ("6".equals(maskBit)) return "252.0.0.0";

        if ("7".equals(maskBit)) return "254.0.0.0";

        if ("8".equals(maskBit)) return "255.0.0.0";

        if ("9".equals(maskBit)) return "255.128.0.0";

        if ("10".equals(maskBit)) return "255.192.0.0";

        if ("11".equals(maskBit)) return "255.224.0.0";

        if ("12".equals(maskBit)) return "255.240.0.0";

        if ("13".equals(maskBit)) return "255.248.0.0";

        if ("14".equals(maskBit)) return "255.252.0.0";

        if ("15".equals(maskBit)) return "255.254.0.0";

        if ("16".equals(maskBit)) return "255.255.0.0";

        if ("17".equals(maskBit)) return "255.255.128.0";

        if ("18".equals(maskBit)) return "255.255.192.0";

        if ("19".equals(maskBit)) return "255.255.224.0";

        if ("20".equals(maskBit)) return "255.255.240.0";

        if ("21".equals(maskBit)) return "255.255.248.0";

        if ("22".equals(maskBit)) return "255.255.252.0";

        if ("23".equals(maskBit)) return "255.255.254.0";

        if ("24".equals(maskBit)) return "255.255.255.0";

        if ("25".equals(maskBit)) return "255.255.255.128";

        if ("26".equals(maskBit)) return "255.255.255.192";

        if ("27".equals(maskBit)) return "255.255.255.224";

        if ("28".equals(maskBit)) return "255.255.255.240";

        if ("29".equals(maskBit)) return "255.255.255.248";

        if ("30".equals(maskBit)) return "255.255.255.252";

        if ("31".equals(maskBit)) return "255.255.255.254";

        if ("32".equals(maskBit)) return "255.255.255.255";

        return "-1";

    }

    /**
     * 根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
     * @param netmarks
     * @return
     */
    public static int getNetMask(String netmarks) {
        StringBuilder sbf;
        String str;
        int inetmask = 0;
        int count = 0;
        String[] ipList = netmarks.split("\\.");
        for (int n = 0; n < ipList.length; n++)
        {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++)
            {
                i = str.indexOf('1', i);
                if (i == -1)
                {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }
    private static StringBuilder toBin(int x) {
        StringBuilder result = new StringBuilder();
        result.append(x % 2);
        x /= 2;
        while (x > 0)
        {
            result.append(x % 2);
            x /= 2;
        }
        return result;
    }

    public static String getBroadcastAddress(String ip,String netmask) {
        String[] ips = ip.split("\\.");
        String[] masks = netmask.split("\\.");
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < ips.length; i++) {
            ips[i] = String.valueOf((~Integer.parseInt(masks [i]))|(Integer.parseInt(ips[i])));
            sb.append(turnToStr(Integer.parseInt(ips[i])));
            if(i != (ips.length-1))
                sb.append(".");
        }
        return turnToIp(sb.toString());
    }

    /**
     * 把带符号整形转换为二进制
     * @param num
     * @return
     */
    private static String turnToStr(int num) {
        String str = "";
        str = Integer.toBinaryString(num);
        int len = 8 - str.length();
        // 如果二进制数据少于8位,在前面补零.
        for (int i = 0; i < len; i++) {
            str = "0" + str;
        }
        //如果num为负数，转为二进制的结果有32位，如1111 1111 1111 1111 1111 1111 1101 1110
        //则只取最后的8位.
        if (len < 0)
            str = str.substring(24, 32);
        return str;
    }
    /**
     * 把二进制形式的ip，转换为十进制形式的ip
     * @param str
     * @return
     */
    private static String turnToIp(String str){
        String[] ips = str.split("\\.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ips.length; i++) {
            sb.append(turnToInt(ips[i]));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 把二进制转换为十进制
     * @param str
     * @return
     */
    private static int turnToInt(String str){
        int total = 0;
        int top = str.length();
        for (int i = 0; i < str.length(); i++) {
            String h = String.valueOf(str.charAt(i));
            top--;
            total += ((int) Math.pow(2, top)) * (Integer.parseInt(h));
        }
        return total;
    }
}
