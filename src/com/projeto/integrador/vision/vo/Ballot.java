package com.projeto.integrador.vision.vo;

public class Ballot {
	
	private int id;
	private String name;
	private String song;
	private String imagem;
	
	
	public Ballot(int id, String name, String song, String imagem) {
		super();
		this.id = id;
		this.name = name;
		this.song = song;
		this.imagem = imagem;
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
}
