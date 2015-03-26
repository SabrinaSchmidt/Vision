package com.projeto.integrador.vision.vo;

public class FeaturesMonetaryBallot {
	private int id;
	private String features;
	private int positionX;
	private int positionY;
	
	public FeaturesMonetaryBallot(int id, String features, int positionX, int positionY) {
		super();
		this.id = id;
		this.features = features;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public FeaturesMonetaryBallot(String features, int positionX, int positionY) {
		super();
		this.features = features;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFeatures() {
		return features;
	}
	
	public void setFeatures(String features) {
		this.features = features;
	}
	
	public int getPositionX() {
		return positionX;
	}
	
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public int getPositionY() {
		return positionY;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	
	
	
}
