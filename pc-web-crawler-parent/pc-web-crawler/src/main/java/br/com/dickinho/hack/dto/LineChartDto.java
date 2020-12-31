package br.com.dickinho.hack.dto;

import java.util.List;

public class LineChartDto {
	private String label;
	private List<Double> data;
	private String borderColor;
	private String backgroundColor;
	private Boolean fill;
	private Double lineTension;
	private Double borderDashOffset;
	private Integer pointHoverRadius;
	private Integer pointHoverBorderWidth;
	private Integer pointHitRadius;
	
	
	
	public Integer getPointHitRadius() {
		return 8;
	}
	public void setPointHitRadius(Integer pointHitRadius) {
		this.pointHitRadius = pointHitRadius;
	}
	public Integer getPointHoverBorderWidth() {
		return 3;
	}
	public void setPointHoverBorderWidth(Integer pointHoverBorderWidth) {
		this.pointHoverBorderWidth = pointHoverBorderWidth;
	}
	public Integer getPointHoverRadius() {
		return 8;
	}
	public void setPointHoverRadius(Integer pointHoverRadius) {
		this.pointHoverRadius = pointHoverRadius;
	}
	public Double getBorderDashOffset() {
		return 0.5;
	}
	public void setBorderDashOffset(Double borderDashOffset) {
		this.borderDashOffset = borderDashOffset;
	}
	public Double getLineTension() {
		return 0.0;
	}
	public void setLineTension(Double lineTension) {
		this.lineTension = lineTension;
	}
	public Boolean getFill() {
		return false;
	}
	public void setFill(Boolean fill) {
		this.fill = fill;
	}
	public String getBackgroundColor() {
		return this.borderColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	
}
