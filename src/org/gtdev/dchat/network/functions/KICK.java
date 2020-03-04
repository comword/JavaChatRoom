package org.gtdev.dchat.network.functions;

import org.gtdev.dchat.DChatMsg.*;
import org.gtdev.dchat.client.MainUI;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.MsgPack;
import org.gtdev.dchat.network.SvcHandler;

import java.io.IOException;

public class KICK implements SvcHandler {

    MsgPack from;
    String reply;

    @Override
    public void feedMsg(MsgPack fm) {
        ConnMgr cm = ConnMgr.getInstance();
        if(cm.selfType == ConnMgr.Connection.PeerType.SERVER) {
            from = fm;
            String UIDtoKick = from.getMessage().msg_body;
            if(cm.getPeer(UIDtoKick) == null) { //Specified a wrong UID
                reply = "The user does not connected to the server.";
            } else {
                ConnMgr.Connection c = cm.getPeer(UIDtoKick);
                try {
                    c.getSocket().close();
                    Msg m = new Msg();
                    m.msg_body = "Client "+from.getMessage().msg_head.getFrom_uin()+" has been kicked from the server.";
                    reply = m.msg_body;
                    m.msg_head = new MsgHead();
                    m.msg_head.from_uin = cm.selfUIN;
                    m.msg_head.msg_type = MsgType.KICK.value();
                    MsgPack mp = new MsgPack(m);
                    cm.doBroadcast(mp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(cm.selfType == ConnMgr.Connection.PeerType.CLIENT) {
            MainUI.msgNotification("<html><p align=center>"
                    + fm.getMessage().msg_body);
        }
    }

    @Override
    public MsgPack reply() {
        if(ConnMgr.getInstance().selfType == ConnMgr.Connection.PeerType.SERVER) {
            ConnMgr cm = ConnMgr.getInstance();
            MsgHead mh = new MsgHead();
            mh.setTo_uin(from.getMessage().msg_head.from_uin);
            mh.setFrom_uin(cm.selfUIN);
            mh.setMsg_seq(from.getMessage().msg_head.msg_seq);
            mh.setMsg_type(MsgType.KICK.value());
            Msg toMsg = new Msg(mh, reply);
            MsgPack mp = new MsgPack(toMsg);
            return mp;
        }
        return null;
    }
}
