package org.gtdev.dchat.server;

import org.gtdev.dchat.ParametersParser;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.recvThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLauncher {

    static String currentDir, server_addr = "::";
    static int server_port = 9055;
    static ParametersParser.arg_handler[] defargs;

    static {
        currentDir = System.getProperty("user.dir");
        //System.out.println("Current dir using System: " + currentDir);

        defargs = new ParametersParser.arg_handler[] {
                new ParametersParser.arg_handler("--ip",
                        "Specify this server's IP address.",
                        "<IP Address>",
                        (int argc, String[] argv) -> {
                            System.out.println("Server address set to " + argv[0]);
                            server_addr = argv[0];
                            return 1;
                        }),
                new ParametersParser.arg_handler("--port",
                        "Specify this server's listen port.",
                        "<Port>",
                        (int argc, String[] argv) -> {
                            System.out.println("Server port set to " + argv[0]);
                            server_port = Integer.parseInt(argv[0]);
                            return 1;
                        })
        };
    }

    public static void main(String[] args) {
        ParametersParser.procArgs(defargs, args);
        //Start
        ConnMgr cm = ConnMgr.getInstance();
        cm.selfType = ConnMgr.Connection.PeerType.SERVER;
        cm.selfUIN = "SERVER-0";
        ServerSocket sSocket = cm.makeServerSock(server_addr, server_port);
        System.out.println("Server is started.");
        while (true) {
            try {
                Socket s = sSocket.accept();
                System.out.println("New client connected");
                ConnMgr.Connection c = new ConnMgr.Connection(s, ConnMgr.Connection.PeerType.CLIENT);
                new Thread(new recvThread(c)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
