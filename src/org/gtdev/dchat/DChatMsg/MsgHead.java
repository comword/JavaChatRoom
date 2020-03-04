// **********************************************************************
// This file was generated by a TARS parser!
// TARS version 1.0.1.
// **********************************************************************

package org.gtdev.dchat.DChatMsg;

//import com.qq.tars.protocol.util.*;
//import com.qq.tars.protocol.annotation.*;
import com.qq.tars.protocol.tars.*;
//import com.qq.tars.protocol.tars.annotation.*;

//@TarsStruct
public class MsgHead extends TarsStructBase{

	//@TarsStructProperty(order = 1, isRequire = true)
	public String from_uin = "";
	//@TarsStructProperty(order = 2, isRequire = true)
	public String to_uin = "";
	//@TarsStructProperty(order = 3, isRequire = true)
	public int msg_type = 0;
	//@TarsStructProperty(order = 4, isRequire = true)
	public long msg_seq = 0L;
	//@TarsStructProperty(order = 5, isRequire = true)
	public long msg_time = 0L;

	public String getFrom_uin() {
		return from_uin;
	}

	public void setFrom_uin(String from_uin) {
		this.from_uin = from_uin;
	}

	public String getTo_uin() {
		return to_uin;
	}

	public void setTo_uin(String to_uin) {
		this.to_uin = to_uin;
	}

	public int getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}

	public long getMsg_seq() {
		return msg_seq;
	}

	public void setMsg_seq(long msg_seq) {
		this.msg_seq = msg_seq;
	}

	public long getMsg_time() {
		return msg_time;
	}

	public void setMsg_time(long msg_time) {
		this.msg_time = msg_time;
	}

	public MsgHead() {
	}

	public MsgHead(String from_uin, String to_uin, int msg_type, long msg_seq, long msg_time) {
		this.from_uin = from_uin;
		this.to_uin = to_uin;
		this.msg_type = msg_type;
		this.msg_seq = msg_seq;
		this.msg_time = msg_time;
	}

	//@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + TarsUtil.hashCode(from_uin);
		result = prime * result + TarsUtil.hashCode(to_uin);
		result = prime * result + TarsUtil.hashCode(msg_type);
		result = prime * result + TarsUtil.hashCode(msg_seq);
		result = prime * result + TarsUtil.hashCode(msg_time);
		return result;
	}

	//@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MsgHead)) {
			return false;
		}
		MsgHead other = (MsgHead) obj;
		return (
			TarsUtil.equals(from_uin, other.from_uin) &&
			TarsUtil.equals(to_uin, other.to_uin) &&
			TarsUtil.equals(msg_type, other.msg_type) &&
			TarsUtil.equals(msg_seq, other.msg_seq) &&
			TarsUtil.equals(msg_time, other.msg_time) 
		);
	}

	public void writeTo(TarsOutputStream _os) {
		_os.write(from_uin, 1);
		_os.write(to_uin, 2);
		_os.write(msg_type, 3);
		_os.write(msg_seq, 4);
		_os.write(msg_time, 5);
	}


	public void readFrom(TarsInputStream _is) {
		this.from_uin = _is.readString(1, true);
		this.to_uin = _is.readString(2, true);
		this.msg_type = _is.read(msg_type, 3, true);
		this.msg_seq = _is.read(msg_seq, 4, true);
		this.msg_time = _is.read(msg_time, 5, true);
	}

}