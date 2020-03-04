// **********************************************************************
// This file was generated by a TARS parser!
// TARS version 1.0.1.
// **********************************************************************

package org.gtdev.dchat.DChatMsg;

public enum MsgType {

	BROADCAST(1),
	STOP(2),
	LIST(3),
	KICK(4),
	STATS(5),
	LOGIN(6);

	private final int value;

	private MsgType(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.name() + ":" + this.value;
	}

	public static MsgType convert(int value) {
		for(MsgType v : values()) {
			if(v.value() == value) {
				return v;
			}
		}
		return null;
	}
}