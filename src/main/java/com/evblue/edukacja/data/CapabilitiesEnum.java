package com.evblue.edukacja.data;

public enum CapabilitiesEnum {
	INVISIBILITY(0x1),
    INVULNERABILITY(0x2),
    HIDE_NAME(0x4),
    REMOVE_HOSTILES(0x8),
    FAST_SHOOTING(0x10),
    FROZEN(0x20);
	
	private final int code;
	
	private CapabilitiesEnum(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
