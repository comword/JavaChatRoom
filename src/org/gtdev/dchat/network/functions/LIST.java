package org.gtdev.dchat.network.functions;

import org.gtdev.dchat.DChatMsg.*;
import org.gtdev.dchat.network.ConnMgr;
import org.gtdev.dchat.network.MsgPack;
import org.gtdev.dchat.network.SvcHandler;

public class LIST implements SvcHandler {

    MsgPack from;

    @Override
    public void feedMsg(MsgPack fm) {
        if(ConnMgr.getInstance().selfType == ConnMgr.Connection.PeerType.SERVER) {
            from = fm;
        }
    }

    @Override
    public MsgPack reply() {
        ConnMgr cm = ConnMgr.getInstance();
        if(cm.selfType == ConnMgr.Connection.PeerType.SERVER) {
            MsgHead mh = new MsgHead();
            mh.setTo_uin(from.getMessage().msg_head.from_uin);
            mh.setFrom_uin(cm.selfUIN);
            mh.setMsg_seq(from.getMessage().msg_head.msg_seq);
            mh.setMsg_type(MsgType.LIST.value());
            String res = "";
            for (String s : cm.getPeerlist())
                res = res + s + "<br/>";
            Msg toMsg = new Msg(mh, res);
            MsgPack mp = new MsgPack(toMsg);
            return mp;
        }
        return null;
    }
}
