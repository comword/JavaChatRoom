package org.gtdev.dchat.network.functions;

import org.gtdev.dchat.DChatMsg.Msg;
import org.gtdev.dchat.DChatMsg.MsgType;
import org.gtdev.dchat.client.MainUI;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.MsgPack;
import org.gtdev.dchat.network.SvcHandler;

public class BROADCAST implements SvcHandler {

    Msg from;

    @Override
    public void feedMsg(MsgPack fm) {
        from = fm.getMessage();
        ConnMgr cm = ConnMgr.getInstance();
        if(cm.selfType == ConnMgr.Connection.PeerType.SERVER) { //I am server
            Msg m = new Msg();
            m.msg_body = from.msg_body;
            m.msg_head = from.msg_head;
            m.msg_head.setMsg_seq(fm.getConnection().getNextSeq());
            m.msg_head.setMsg_type(MsgType.BROADCAST.value());
            MsgPack mp = new MsgPack(m);
            cm.doBroadcast(mp);
        } else if (cm.selfType == ConnMgr.Connection.PeerType.CLIENT) {
            MainUI.msgNotification( "New broadcast received from <b>" + fm.getMessage().msg_head.from_uin + "</b>:<br/>"
                        + fm.getMessage().msg_body +"<br/>");
        }
    }

    @Override
    public MsgPack reply() {
        return null;
    }
}
