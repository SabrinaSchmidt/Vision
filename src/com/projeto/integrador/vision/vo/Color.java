package com.projeto.integrador.vision.vo;

public class Color {
	private int id;
	private int r;
	private int g;
	private int b;
	private String name;
	private String song;
	
	public Color(int id, int r, int g, int b, String name, String song) {
		super();
		this.id = id;
		this.r = r;
		this.g = g;
		this.b = b;
		this.name = name;
		this.song = song;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getR() {
		return r;
	}
	
	public void setR(int r) {
		this.r = r;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public int getB() {
		return b;
	}
	
	public void setB(int b) {
		this.b = b;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSong() {
		return song;
	}
	
	public void setSong(String song) {
		this.song = song;
	}	
}
