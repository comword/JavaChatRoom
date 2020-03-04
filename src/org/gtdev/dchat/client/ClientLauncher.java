package org.gtdev.dchat.client;

import org.gtdev.dchat.DChatMsg.Msg;
import org.gtdev.dchat.DChatMsg.MsgHead;
import org.gtdev.dchat.DChatMsg.MsgType;
import org.gtdev.dchat.ParametersParser;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.MsgPack;
import org.gtdev.dchat.network.recvThread;

import java.util.Random;

import static org.gtdev.dchat.client.MainUI.createAndShowGUI;

public class ClientLauncher {

    static String currentDir, server_addr = "::", user_id = "newUser" + new Random().nextInt(1000);
    static int server_port = 9055;
    static ParametersParser.arg_handler[] defargs;

    static {
        currentDir = System.getProperty("user.dir");
        //System.out.println("Current dir using System: " + currentDir);

        defargs = new ParametersParser.arg_handler[] {
                new ParametersParser.arg_handler("--ip",
                        "Specify connecting server's IP address.",
                        "<IP Address>",
                        (int argc, String[] argv) -> {
                            System.out.println("Address set to " + argv[0]);
                            server_addr = argv[0];
                            return 1;
                        }),
                new ParametersParser.arg_handler("--port",
                        "Specify connecting port.",
                        "<Port>",
                        (int argc, String[] argv) -> {
                            System.out.println("Port set to " + argv[0]);
                            server_port = Integer.parseInt(argv[0]);
                            return 1;
                        }),
                new ParametersParser.arg_handler("--uname",
                        "Specify this client's username.",
                        "<username>",
                        (int argc, String[] argv) -> {
                            System.out.println("Username set to " + argv[0]);
                            user_id = argv[0];
                            return 1;
                        })
        };
    }

    public static void main(String[] args) {
        ParametersParser.procArgs(defargs, args);
        //Start
        ConnMgr cm = ConnMgr.getInstance();
        cm.selfType = ConnMgr.Connection.PeerType.CLIENT;
        cm.selfUIN = user_id;
        ConnMgr.Connection s = cm.makeConnection(server_addr, server_port);
        if(s == null) {
            System.out.println("Server connected failed.");
            System.exit(0);
        }
        new Thread(new recvThread(s)).start(); //start receiving thread
        s.peerUin = "SERVER-0";
        MsgHead head = new MsgHead();
        head.setMsg_seq(s.getNextSeq());
        head.setMsg_type(MsgType.LOGIN.value());
        head.setFrom_uin(user_id);
        head.setTo_uin(s.peerUin);
        MsgPack loginpack = new MsgPack(new Msg(head, ""));
        s.SendPack(loginpack.getNetPack(0),0);
        cm.addNewPeer("SERVER-0", s); //add to peer list
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
