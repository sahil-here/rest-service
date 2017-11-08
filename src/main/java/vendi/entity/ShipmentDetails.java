package vendi.entity;

import java.sql.Date;

public class ShipmentDetails {
	
	private Float actualWeight;
	
	private Float volumetricWeight;
	
	private Float length;
	
	private Float breadth;
	
	private Float height;
	
	private Integer pieces;
	
	private Handling handling;
	
	private Date promisedDeliveryDate;

	public Float getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(Float actualWeight) {
		this.actualWeight = actualWeight;
	}

	public Float getVolumetricWeight() {
		return volumetricWeight;
	}

	public void setVolumetricWeight(Float volumetricWeight) {
		this.volumetricWeight = volumetricWeight;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public Float getBreadth() {
		return breadth;
	}

	public void setBreadth(Float breadth) {
		this.breadth = breadth;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	public Handling getHandling() {
		return handling;
	}

	public void setHandling(Handling handling) {
		this.handling = handling;
	}

	public Date getPromisedDeliveryDate() {
		return promisedDeliveryDate;
	}

	public void setPromisedDeliveryDate(Date promisedDeliveryDate) {
		this.promisedDeliveryDate = promisedDeliveryDate;
	}

	@Override
	public String toString() {
		return "ShipmentDetails [actualWeight=" + actualWeight + ", volumetricWeight=" + volumetricWeight + ", length="
				+ length + ", breadth=" + breadth + ", height=" + height + ", pieces=" + pieces + ", handling="
				+ handling + ", promisedDeliveryDate=" + promisedDeliveryDate + "]";
	}

}
