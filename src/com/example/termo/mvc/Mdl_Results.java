package com.example.termo.mvc;


import java.io.Serializable;
import java.util.ArrayList;

import com.example.termo.helper.ConstantsHelper;

public class Mdl_Results implements Serializable{
private static final long serialVersionUID = 1L;
	
	private double T;
	private double P;
	private double d;
	private double v;
	private double u;
	private double h;
	private double s;
	private double cv;
	private double cp;
	private double ss;
	private double jt;
	private double vis;
	private double tc;
	private double st;
	
	public Mdl_Results() {
		
	}

	public double getT() {
		return T;
	}

	public double getP() {
		return P;
	}
	
	public double getD() {
		return d;
	}

	public double getV() {
		return v;
	}

	public double getU() {
		return u;
	}

	public double getH() {
		return h;
	}

	public double getS() {
		return s;
	}
	
	public double getCv() {
		return cv;
	}

	public double getCp() {
		return cp;
	}

	public double getSs() {
		return ss;
	}

	public double getJt() {
		return jt;
	}

	public double getVis() {
		return vis;
	}

	public double getTc() {
		return tc;
	}
	
	public double getSt() {
		return st;
	}
	
	public void setT(double T) {
		this.T = T;
	}

	public void setP(double P) {
		this.P = P;
	}

	public void setD(double d) {
		this.d = d;
	}
	
	public void setV(double v) {
		this.v = v;
	}

	public void setU(double u) {
		this.u = u;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void setS(double s) {
		this.s = s;
	}

	public void setCv(double cv) {
		this.cv = cv;
	}

	public void setCp(double cp) {
		this.cp = cp;
	}

	public void setSs(double ss) {
		this.ss = ss;
	}

	public void setJt(double jt) {
		this.jt = jt;
	}

	public void setVis(double vis) {
		this.vis = vis;
	}

	public void setTc(double tc) {
		this.tc = tc;
	}
	
	public void setSt(double st) {
		this.st = st;
	}
	
	public ArrayList<String> getResultsAsArray(int arrayType) {
		
		ArrayList<String> arrayResults = new ArrayList<String>();
		
		arrayResults.add(0, "T = " + getT());
		arrayResults.add(1, "P = " + getP());
		arrayResults.add(2, "d = " + getD());
		arrayResults.add(3, "v = " + getV());
		arrayResults.add(4, "u = " + getU());
		arrayResults.add(5, "h = " + getH());
		arrayResults.add(6, "s = " + getS());
		
		if(arrayType != ConstantsHelper.ARRAY_RESULTS_BASIC) {
			arrayResults.add(7, "cv = " + getCv());
			arrayResults.add(8, "cp = " + getCp());
			arrayResults.add(9, "ss = " + getSs());
			arrayResults.add(10, "jt = " + getJt());
			arrayResults.add(11, "vis = " + getVis());
			arrayResults.add(12, "tc = " + getTc());
		} 
		
		if(arrayType == ConstantsHelper.ARRAY_RESULTS_ALLSAT) {
			arrayResults.add(13, "st = " + getSt());
		}
		
		return arrayResults;
	}
	
}
