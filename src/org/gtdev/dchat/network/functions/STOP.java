package org.gtdev.dchat.network.functions;

import org.gtdev.dchat.DChatMsg.*;
import org.gtdev.dchat.client.MainUI;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.MsgPack;
import org.gtdev.dchat.network.SvcHandler;

import java.io.IOException;

public class STOP implements SvcHandler {
    @Override
    public void feedMsg(MsgPack fm) {
        Msg from = fm.getMessage();
        ConnMgr cm = ConnMgr.getInstance();
        if(cm.selfType == ConnMgr.Connection.PeerType.SERVER) {
            try {
                fm.getConnection().getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Broadcast
            Msg m = new Msg();
            m.msg_body = "Client "+from.msg_head.getFrom_uin()+" has disconnected.";
            m.msg_head = new MsgHead();
            m.msg_head.from_uin = cm.selfUIN;
            m.msg_head.msg_type = MsgType.STOP.value();
            MsgPack mp = new MsgPack(m);
            cm.doBroadcast(mp);
        }
        if(cm.selfType == ConnMgr.Connection.PeerType.CLIENT) {
            MainUI.msgNotification("<html>"
                    + fm.getMessage().msg_body);
        }
    }

    @Override
    public MsgPack reply() {
        return null;
    }
}
