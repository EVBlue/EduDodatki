package com.evblue.edukacja.data;

public enum ListensEnum {
	PROP,
	FLY,
	GAMEMODE_CREATIVE,
	GAMEMODE_SURVIVAL,
	GAMEMODE_ADVENTURE;
	
	public static ListensEnum parseElement(String s) {
		if (s == null) return null;
		try {
			return valueOf(s);
		} catch (IllegalArgumentException ignored) {
		}
		return null;
	}

}
