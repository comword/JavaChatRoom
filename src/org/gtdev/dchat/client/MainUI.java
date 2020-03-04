package org.gtdev.dchat.client;

import org.gtdev.dchat.DChatMsg.Msg;
import org.gtdev.dchat.DChatMsg.MsgHead;
import org.gtdev.dchat.DChatMsg.MsgType;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.MsgPack;
import org.gtdev.dchat.network.NetPack;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainUI extends JPanel {

    JButton sendButton;
    static JFrame frame;
    static JTextPane tfChatHistory;
    static String chatHistory = "";

    private MainUI() {
        super(new BorderLayout());
        JPanel selectbox = new JPanel();
        JLabel label = new JLabel("Select a server command, and input content", JLabel.CENTER);

        selectbox.setLayout(new BoxLayout(selectbox, BoxLayout.PAGE_AXIS));
        selectbox.add(label);

        JRadioButton[] radioButtons = new JRadioButton[5];

        radioButtons[0] = new JRadioButton("BROADCAST");
        radioButtons[0].setActionCommand("BROADCAST");
        radioButtons[1] = new JRadioButton("STOP");
        radioButtons[1].setActionCommand("STOP");
        radioButtons[2] = new JRadioButton("LIST");
        radioButtons[2].setActionCommand("LIST");
        radioButtons[3] = new JRadioButton("KICK");
        radioButtons[3].setActionCommand("KICK");
        radioButtons[4] = new JRadioButton("STATS");
        radioButtons[4].setActionCommand("STATS");

        final ButtonGroup group = new ButtonGroup();

        for (int i = 0; i < 5; i++) {
            group.add(radioButtons[i]);
            selectbox.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);

        JTextField tfContents = new JTextField();
        tfContents.setPreferredSize( new Dimension( 300, 24 ) );
        tfChatHistory = new JTextPane();
        tfChatHistory.setPreferredSize( new Dimension( 300, 100 ) );
        tfChatHistory.setEditable(false);
        tfChatHistory.setContentType("text/html");
        JScrollPane tfScrollChatHistory = new JScrollPane(tfChatHistory);

        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String command = group.getSelection().getActionCommand();
            String content = tfContents.getText();
            ConnMgr cm = ConnMgr.getInstance();
            ConnMgr.Connection s = cm.getPeer("SERVER-0");
            Msg m = new Msg();
            MsgPack mp;
            NetPack reply;
            switch (command){
                case "BROADCAST":
                    m.msg_head = new MsgHead();
                    m.msg_head.setMsg_type(MsgType.BROADCAST.value());
                    m.msg_head.setMsg_seq(s.getNextSeq());
                    m.msg_head.setFrom_uin(cm.selfUIN);
                    m.msg_head.setTo_uin("SERVER-0");
                    m.msg_body = content;
                    mp = new MsgPack(m);
                    s.SendPack(mp.getNetPack(0), 0); //no reply needed.
                    return;
                case "STOP":
                    m.msg_head = new MsgHead();
                    m.msg_head.setMsg_type(MsgType.STOP.value());
                    m.msg_head.setMsg_seq(s.getNextSeq());
                    m.msg_head.setFrom_uin(cm.selfUIN);
                    m.msg_head.setTo_uin("SERVER-0");
                    m.msg_body = "";
                    mp = new MsgPack(m);
                    s.SendPack(mp.getNetPack(0), 0); //no reply needed.
                    return;
                case "LIST":
                    m.msg_head = new MsgHead();
                    m.msg_head.setMsg_type(MsgType.LIST.value());
                    m.msg_head.setMsg_seq(s.getNextSeq());
                    m.msg_head.setFrom_uin(cm.selfUIN);
                    m.msg_head.setTo_uin("SERVER-0");
                    m.msg_body = "";
                    mp = new MsgPack(m);
                    reply = s.SendPack(mp.getNetPack(0), 1000); //Receive the reply.
                    if(reply != null)
                        msgNotification("<b>Server return list:</b><br>"
                                +new MsgPack(reply, reply.fromConnection).getMessage().msg_body);
                    return;
                case "KICK":
                    m.msg_head = new MsgHead();
                    m.msg_head.setMsg_type(MsgType.KICK.value());
                    m.msg_head.setMsg_seq(s.getNextSeq());
                    m.msg_head.setFrom_uin(cm.selfUIN);
                    m.msg_head.setTo_uin("SERVER-0");
                    m.msg_body = content;
                    mp = new MsgPack(m);
                    reply = s.SendPack(mp.getNetPack(0), 1000); //Receive the reply.
                    if(reply != null)
                        msgNotification("<b>KICK result:</b><br>"
                                +new MsgPack(reply, reply.fromConnection).getMessage().msg_body);
                    return;
                case "STATS":
                    m.msg_head = new MsgHead();
                    m.msg_head.setMsg_type(MsgType.STATS.value());
                    m.msg_head.setMsg_seq(s.getNextSeq());
                    m.msg_head.setFrom_uin(cm.selfUIN);
                    m.msg_head.setTo_uin("SERVER-0");
                    m.msg_body = content;
                    mp = new MsgPack(m);
                    reply = s.SendPack(mp.getNetPack(0), 1000); //Receive the reply.
                    if(reply != null)
                        msgNotification("<b>STATS result:</b><br>"
                                +new MsgPack(reply, reply.fromConnection).getMessage().msg_body);
                    return;
            }
        });

        JPanel pane = new JPanel(new BorderLayout());
        JPanel sendArea = new JPanel(new FlowLayout());

        pane.add(selectbox, BorderLayout.PAGE_START);
        pane.add(tfScrollChatHistory, BorderLayout.CENTER);
        sendArea.add(tfContents);
        sendArea.add(sendButton);
        pane.add(sendArea, BorderLayout.PAGE_END);
        add(pane);
    }

    public static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("DChatClient");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        MainUI newContentPane = new MainUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void msgNotification(String str) {
//        final JDialog dialog = new JDialog(frame, "New message");
//        JButton closeButton = new JButton("Dismiss");
//        closeButton.addActionListener(e -> {
//                dialog.setVisible(false);
//                dialog.dispose();
//        });
//
////        JLabel label = new JLabel("<html><p align=center>"
////                + "<b>New message received:</b><br>"
////                + str);
//        JLabel label = new JLabel(str);
//        label.setHorizontalAlignment(JLabel.CENTER);
//
//        JPanel contentPane = new JPanel(new BorderLayout());
//        contentPane.add(label, BorderLayout.CENTER);
//        contentPane.add(closeButton, BorderLayout.PAGE_END);
//        contentPane.setOpaque(true);
//        dialog.setContentPane(contentPane);
//
//        //dialog.setSize(new Dimension(300, 150));
//        dialog.pack();
//        dialog.setLocationRelativeTo(frame);
//        dialog.setVisible(true);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        chatHistory += strDate;
        chatHistory += "<br/>";
        chatHistory += str;
        if(tfChatHistory!=null)
            tfChatHistory.setText(chatHistory);
    }
}
