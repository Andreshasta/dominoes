/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DominoInicio;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edwin
 */
public class DatosServidor {

    public static String consultaIPServidor() {
        String dirIP = "";
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            NetworkInterface ni = null;
            while (nets.hasMoreElements()) {
                ni = nets.nextElement();
                if (ni.isUp()) {
                    List<InterfaceAddress> ias = ni.getInterfaceAddresses();
                    for (InterfaceAddress ia : ias) {
                        InetAddress ian = ia.getAddress();
                        if (ian.isSiteLocalAddress()) {
                            if (ni.getDisplayName().contains("LAN")) {
                                //System.out.println("nombre interface: " + ni.getDisplayName() + " virtual: " + ni.getIndex() + " corriendo " + ni.isUp());
                                dirIP = ian.getHostAddress();
                                //System.out.println("host address " + ian.getHostAddress());
                                System.out.println("host address " + dirIP);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            dirIP="";
        }
        return dirIP;
    }
}
