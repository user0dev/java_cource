/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jipcalc;

import java.util.regex.*;
import java.util.HashSet;
//import java.lang.IllegalArgumentException;

/**
 *
 * @author user
 */
public class IP4Calc {
    static protected String strRegIP4 = "([0-2]?\\d{1,2})\\.([0-2]?\\d{1,2})\\.([0-2]?\\d{1,2})\\.([0-2]?\\d{1,2})";
    static protected Pattern regIP4 = Pattern.compile("^\\s*" + strRegIP4 + "\\s*$");
    //static protected Pattenr regIP4AndMask = Pattern.compile("^\\s*" + strRegIP4 + "/\\")
    static protected HashSet<Integer> masks;
    static {
        masks = new HashSet<Integer>();
        for (int mask = -1; mask != 0; mask <<= 1) {
            masks.add(mask);
        }
        masks.add(0);
        
    };
    public static boolean maskIsRight(int netMask) {
        return masks.contains(netMask);
    }
    public static Integer strMaskToInt(String netMask) {
        netMask = netMask.trim();
        int mask = 0;
        try {
            mask = Integer.parseInt(netMask);
        } catch (NumberFormatException e) {
            Integer longMask = strIP4ToInt(netMask);
            if (longMask == null) {
                return null;
            }
            return maskIsRight(longMask) ? longMask : null;
        }
        if (mask > 32 || mask < 0) {
            return null;
        }
        return -1 << (32 - mask);
    }
    public static Integer strIP4ToInt(String ip4) {
        Matcher m = regIP4.matcher(ip4);
        if (!m.matches()) {
            return null;
        }
        int result = 0; 
        for (int i = 0; i < 4; i++) {
            int n = Integer.parseInt(m.group(i+1));
            if (n > 255) {
                return null;
            }
            result |= n << ((3 - i) * 8);
        }        
        return result;
    }
    public static Integer computeNetwork(int address, int netMask) {
        if (!maskIsRight(netMask)) {
            return null;
        }
        return address & netMask;
    }
    
    public static boolean compareIPParameters(int address, int netMask, int network) {
        if (!maskIsRight(netMask)) {
            throw new IllegalArgumentException("Wrong 'netMask' parameter with valuet: " + netMask);
        }
        
        return computeNetwork(address, netMask) == network;
    }
    public static String intIP4ToStr(int ip4) {
        String strIp4 = Integer.toString(ip4 & 0xff);
        for (int i = 0; i < 3; i++) {
            ip4 >>= 8;
            strIp4 = Integer.toString(ip4 & 0xff) + "." + strIp4;
        }
        return strIp4;        
    }
    public static void main(String[] args) {
        System.out.println(Integer.toHexString(strMaskToInt("25")));
    }
}
