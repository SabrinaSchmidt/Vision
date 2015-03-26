package com.projeto.integrador.vision.vo;

public class FeaturesColorDetection {
	
	private int id;
	private String features;

	public FeaturesColorDetection(int id, String features) {
		super();
		this.id = id;
		this.features = features;
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
}
