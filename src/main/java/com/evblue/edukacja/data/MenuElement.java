package com.evblue.edukacja.data;

public class MenuElement {
	public final String menu;
	public final ItemListNode action;
	
	public MenuElement(String menu, ItemListNode action) {
		this.menu = menu;
		this.action = action;
	}
	
	public static MenuElement create(String menu, ItemListNode action) {
		return new MenuElement(menu, action);
	}

}
