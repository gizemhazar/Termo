package com.example.termo.mvc;

import java.io.Serializable;
import static  com.example.termo.helper.ConstantsHelper.*;

public class Mdl_QueryInputs implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String unitSystem; // SI or imperial
	
	// fluid type settings
	private String fluid;
	private boolean isWater;
	private boolean isR134a;
	
	
	// pressure temperature settings
	private String presTempSelection;
	private boolean isPresSpecified;
	private double presValue;
	private String presUnit;
	
	private boolean isTempSpecified;
	private double tempValue;
	private String tempUnit;
	
	
	// xvuhs settings
	private String xvuhs; // Selected x,v,u,h,s from spinner
	
	private boolean isX;
	private double xValue;
	
	private boolean isV;
	private double vValue;
	private String volUnit;
	
	private boolean isU;
	private double uValue;
	
	private boolean isH;
	private double hValue;
	private String energyUnit;
	
	private boolean isS;
	private double sValue;
	private String entropyUnit;
	
	public Mdl_QueryInputs() {
		
	}
	
	public String getUnitSystem() {
		return unitSystem;
	}
	public String getFluid() {
		return fluid;
	}
	public boolean isWater() {
		return isWater;
	}
	public boolean isR134a() {
		return isR134a;
	}
	public boolean isPresSpecified() {
		return isPresSpecified;
	}
	public double getPresValue() {
		return presValue;
	}
	public String getPresUnit() {
		return presUnit;
	}
	public boolean isTempSpecified() {
		return isTempSpecified;
	}
	public double getTempValue() {
		return tempValue;
	}
	public String getTempUnit() {
		return tempUnit;
	}
	public String getXvuhs() {
		return xvuhs;
	}
	public boolean isX() {
		return isX;
	}
	public double getXValue() {
		return xValue;
	}
	public boolean isV() {
		return isV;
	}
	public double getVValue() {
		return vValue;
	}
	public String getVolUnit() {
		return volUnit;
	}
	public boolean isU() {
		return isU;
	}
	public double getUValue() {
		return uValue;
	}
	public boolean isH() {
		return isH;
	}
	public double getHValue() {
		return hValue;
	}
	public String getEnergyUnit() {
		return energyUnit;
	}
	public boolean isS() {
		return isS;
	}
	public double getSValue() {
		return sValue;
	}
	public String getEntropyUnit() {
		return entropyUnit;
	}
	public void setUnitSystem(String unitSystem) {
		this.unitSystem = unitSystem;
	}
	public void setFluid(String fluid) {
		this.fluid = fluid;
	}
	public void setIsWater(boolean isWater) {
		this.isWater = isWater;
	}
	public void setIsR134a(boolean isR134a) {
		this.isR134a = isR134a;
	}
	public String getPresTempSelection() {
		return presTempSelection;
	}

	public void setPresTempSelection(String presTempSelection) {
		this.presTempSelection = presTempSelection;
	}

	public void setIsPresSpecified(boolean isPresSpecified) {
		this.isPresSpecified = isPresSpecified;
	}
	public void setPresValue(double presValue) {
		this.presValue = presValue;
	}
	public void setPresUnit(String presUnit) {
		this.presUnit = presUnit;
	}
	public void setIsTempSpecified(boolean isTempSpecified) {
		this.isTempSpecified = isTempSpecified;
	}
	public void setTempValue(double tempValue) {
		this.tempValue = tempValue;
	}
	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}
	public void setXvuhs(String xvuhs) {
		this.xvuhs = xvuhs;
	}
	public void setIsX(boolean isX) {
		this.isX = isX;
	}
	public void setXValue(double xValue) {
		this.xValue = xValue;
	}
	public void setIsV(boolean isV) {
		this.isV = isV;
	}
	public void setVValue(double vValue) {
		this.vValue = vValue;
	}
	public void setVolUnit(String volUnit) {
		this.volUnit = volUnit;
	}
	public void setIsU(boolean isU) {
		this.isU = isU;
	}
	public void setUValue(double uValue) {
		this.uValue = uValue;
	}
	public void setIsH(boolean isH) {
		this.isH = isH;
	}
	public void setHValue(double hValue) {
		this.hValue = hValue;
	}
	public void setEnergyUnit(String energyUnit) {
		this.energyUnit = energyUnit;
	}
	public void setIsS(boolean isS) {
		this.isS = isS;
	}
	public void setSValue(double sValue) {
		this.sValue = sValue;
	}
	public void setEntropyUnit(String entropyUnit) {
		this.entropyUnit = entropyUnit;
	}
	
	public void setOthersFalse(String xvuhs) {
		if(xvuhs.equals(PROP_X)) {
			isV = false;
			isU = false;
			isH = false;
			isS = false;
		} else if(xvuhs.equals(PROP_V)) {
			isX = false;
			isU = false;
			isH = false;
			isS = false;
		} else if(xvuhs.equals(PROP_U)) {
			isV = false;
			isX = false;
			isH = false;
			isS = false;
		} else if(xvuhs.equals(PROP_H)) {
			isV = false;
			isU = false;
			isX = false;
			isS = false;
		} else if(xvuhs.equals(PROP_S)) {
			isV = false;
			isU = false;
			isH = false;
			isX = false;
		}
	}
}
