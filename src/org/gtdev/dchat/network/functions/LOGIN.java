package org.gtdev.dchat.network.functions;

import org.gtdev.dchat.DChatMsg.Msg;
import org.gtdev.dchat.DChatMsg.MsgHead;
import org.gtdev.dchat.DChatMsg.MsgType;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.MsgPack;
import org.gtdev.dchat.network.SvcHandler;

public class LOGIN implements SvcHandler {

    Msg from;

    @Override
    public void feedMsg(MsgPack fm) {
        if(ConnMgr.getInstance().selfType == ConnMgr.Connection.PeerType.SERVER) {
            from = fm.getMessage();
            String regUIN = from.msg_head.getFrom_uin();
            System.out.println("Got new client " + regUIN);
            ConnMgr cm = ConnMgr.getInstance();
            cm.addNewPeer(regUIN, fm.getConnection());
            fm.getConnection().peerUin = regUIN;
            // broadcast new user
            Msg m = new Msg();
            m.msg_body = "New client "+ regUIN;
            m.msg_head = new MsgHead();
            m.msg_head.setMsg_seq(fm.getConnection().getNextSeq());
            m.msg_head.setMsg_type(MsgType.BROADCAST.value());
            m.msg_head.setFrom_uin(cm.selfUIN);
            MsgPack mp = new MsgPack(m);
            cm.doBroadcast(mp);
        }
    }

    @Override
    public MsgPack reply() {
//        if(ConnMgr.getInstance().selfType == ConnMgr.Connection.PeerType.SERVER) {
//            Msg toMsg = new Msg(from.msg_head, "Login successfully.");
//            MsgPack toPack = new MsgPack(toMsg);
//            return toPack;
//        }
        return null;
    }
}
