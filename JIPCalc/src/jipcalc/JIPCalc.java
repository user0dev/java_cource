/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jipcalc;

import java.util.Scanner;
import java.util.function.Function;
/**
 *
 * @author user
 */
public class JIPCalc {
    protected static Scanner input = new Scanner(System.in);
    
    protected static Integer inputParameter(String textParameter, Function<String, Integer> strToAddr) {        
        Integer parameter;
        while (true) {
            System.out.print("Input " + textParameter + ": ");
            parameter = strToAddr.apply(input.nextLine());
            if (parameter != null) {
                return parameter;
            } else {
                System.out.println("Wrong " + textParameter);
            }
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String wrongArguments = "Wrong arguments";
        if (args.length == 2 || args.length == 3) {
            Integer address = IP4Calc.strIP4ToInt(args[0]);
            if (address == null) {
                System.out.println("Wrong ip-address");
                System.exit(1);
            }
            Integer netMask = IP4Calc.strMaskToInt(args[1]);
            if (netMask == null) {
                System.out.println("Wrong network mask");
                System.exit(1);
            }
            if (args.length == 2) {
                System.out.println(IP4Calc.intIP4ToStr(IP4Calc.computeNetwork(address, netMask)));                
                
            } else {
                Integer network = IP4Calc.strIP4ToInt(args[2]);
                if (network == null) {
                    System.out.println("Wrong network address");
                    System.exit(1);
                }
                String output = "IP address, nework mask and network address ";
                output += IP4Calc.compareIPParameters(address, netMask, network) ? "is right" : "isn't right";
                System.out.println(output);
            }
        } else if (args.length == 0) {
            
            Integer network = inputParameter("network address", IP4Calc::strIP4ToInt);
            Integer address = inputParameter("ip address", IP4Calc::strIP4ToInt);
            Integer netMask = inputParameter("network mask", IP4Calc::strMaskToInt);
            if (network != null && address != null && netMask != null) {
                System.out.println(IP4Calc.compareIPParameters(address, netMask, network) ? "Network parameters are right." : "Network parameters are wrong.");
            } else {
                System.out.println(wrongArguments);
            }
            
            
        } else {
            System.out.println(wrongArguments);
            System.exit(1);
        }
    }
}
