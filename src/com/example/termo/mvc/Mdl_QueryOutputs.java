package com.example.termo.mvc;

import java.util.ArrayList;

import  com.example.termo.helper.ConstantsHelper;

public class Mdl_QueryOutputs {

	ArrayList<Double> arrayQueryOutputsSat = new ArrayList<Double>();
	ArrayList<Double> arrayQueryOutputsNotSat = new ArrayList<Double>();
	
	
	private int fluidState;
	private double x;
	private double P;
	private double T;
	private double d;
	private double df;
	private double dg;
	private double dfg;
	private double v;
	private double vf;
	private double vg;
	private double vfg;
	private double u;
	private double uf;
	private double ug;
	private double ufg;
	private double h;
	private double hf;
	private double hg;
	private double hfg;
	private double s;
	private double sf;
	private double sg;
	private double sfg;
	private double cv;
	private double cvf;
	private double cvg;
	private double cvfg;
	private double cp;
	private double cpf;
	private double cpg;
	private double cpfg;
	private double ss;
	private double ssf;
	private double ssg;
	private double ssfg;
	private double jt;
	private double jtf;
	private double jtg;
	private double jtfg;
	private double vis;
	private double visf;
	private double visg;
	private double visfg;
	private double tc;
	private double tcf;
	private double tcg;
	private double tcfg;
	private double st;

	public Mdl_QueryOutputs() {
		
	}
	
	
	public int getFluidState() {
		return fluidState;
	}
	
	public double getX() {
		return x;
	}

	public double getP() {
		return P;
	}

	public double getT() {
		return T;
	}

	public double getD() {
		return d;
	}
	
	public double getDf() {
		return df;
	}

	public double getDg() {
		return dg;
	}

	public double getDfg() {
		return dfg;
	}
	
	public double getV() {
		return v;
	}
	
	public double getVf() {
		return vf;
	}

	public double getVg() {
		return vg;
	}
	
	public double getVfg() {
		return vfg;
	}
	
	public double getU() {
		return u;
	}

	public double getUf() {
		return uf;
	}

	public double getUg() {
		return ug;
	}
	
	public double getUfg() {
		return ufg;
	}
	
	public double getH() {
		return h;
	}

	public double getHf() {
		return hf;
	}

	public double getHg() {
		return hg;
	}

	public double getHfg() {
		return hfg;
	}
	
	public double getS() {
		return s;
	}
	
	public double getSf() {
		return sf;
	}

	public double getSg() {
		return sg;
	}

	public double getSfg() {
		return sfg;
	}
	
	public double getCv() {
		return cv;
	}
	
	public double getCvf() {
		return cvf;
	}

	public double getCvg() {
		return cvg;
	}

	public double getCvfg() {
		return cvfg;
	}
	
	public double getCp() {
		return cp;
	}
	
	public double getCpf() {
		return cpf;
	}

	public double getCpg() {
		return cpg;
	}

	public double getCpfg() {
		return cpfg;
	}
	
	public double getSs() {
		return ss;
	}
	
	public double getSsf() {
		return ssf;
	}

	public double getSsg() {
		return ssg;
	}

	public double getSsfg() {
		return ssfg;
	}
	
	public double getJt() {
		return jt;
	}
	
	public double getJtf() {
		return jtf;
	}

	public double getJtg() {
		return jtg;
	}

	public double getJtfg() {
		return jtfg;
	}
	
	public double getVis() {
		return vis;
	}
	
	public double getVisf() {
		return visf;
	}

	public double getVisg() {
		return visg;
	}
	
	public double getVisfg() {
		return visfg;
	}
	
	public double getTc() {
		return tc;
	}

	public double getTcf() {
		return tcf;
	}

	public double getTcg() {
		return tcg;
	}

	public double getTcfg() {
		return tcfg;
	}
	
	public double getSt() {
		return st;
	}

	public ArrayList<Double> getSatOutputsAsArray() {

		arrayQueryOutputsSat.add(0, T);
		arrayQueryOutputsSat.add(1, P);
		arrayQueryOutputsSat.add(2, df);
		arrayQueryOutputsSat.add(3, dg);
		arrayQueryOutputsSat.add(4, vf);
		arrayQueryOutputsSat.add(5, vg);
		arrayQueryOutputsSat.add(6, uf);
		arrayQueryOutputsSat.add(7, ug);
		arrayQueryOutputsSat.add(8, hf);
		arrayQueryOutputsSat.add(9, hg);
		arrayQueryOutputsSat.add(10, sf);
		arrayQueryOutputsSat.add(11, sg);
		arrayQueryOutputsSat.add(12, cvf);
		arrayQueryOutputsSat.add(13, cvg);
		arrayQueryOutputsSat.add(14, cpf);
		arrayQueryOutputsSat.add(15, cpg);
		arrayQueryOutputsSat.add(16, ssf);
		arrayQueryOutputsSat.add(17, ssg);
		arrayQueryOutputsSat.add(18, jtf);
		arrayQueryOutputsSat.add(19, jtg);
		arrayQueryOutputsSat.add(20, visf);
		arrayQueryOutputsSat.add(21, visg);
		arrayQueryOutputsSat.add(22, tcf);
		arrayQueryOutputsSat.add(23, tcg);
		arrayQueryOutputsSat.add(24, st);
		
		return arrayQueryOutputsSat;
	}
	
	public ArrayList<Double> getNotSatOutputsAsArray() {

		arrayQueryOutputsNotSat.add(0, T);
		arrayQueryOutputsNotSat.add(1, P);
		arrayQueryOutputsNotSat.add(2, d);
		arrayQueryOutputsNotSat.add(3, v);
		arrayQueryOutputsNotSat.add(4, u);
		arrayQueryOutputsNotSat.add(5, h);
		arrayQueryOutputsNotSat.add(6, s);
		arrayQueryOutputsNotSat.add(7, cv);
		arrayQueryOutputsNotSat.add(8, cp);
		arrayQueryOutputsNotSat.add(9, ss);
		arrayQueryOutputsNotSat.add(10, jt);
		arrayQueryOutputsNotSat.add(11, vis);
		arrayQueryOutputsNotSat.add(12, tc);
		
		return arrayQueryOutputsNotSat;
	}
	
	public double getVuhsValue(int vuhsIndex) {
		switch(vuhsIndex) {
			case ConstantsHelper.PROP_INDEX_V:
				return v;
			case ConstantsHelper.PROP_INDEX_U:
				return u;
			case ConstantsHelper.PROP_INDEX_H:
				return h;
			default: //ConstantHelper.PROP_INDEX_S:
				return s;
		}
	}
	
	public void setSatOutputsArray(int propIndex, double propValue) {
		arrayQueryOutputsSat.add(propIndex, propValue);
		switch(propIndex) {
			case 0:
				T = propValue;
				break;
			case 1:
				P = propValue;
				break;
			case 2:
				df = propValue;
				break;
			case 3:
				dg = propValue;
				break;
			case 4:
				vf = propValue;
				break;
			case 5:
				vg = propValue;
				break;
			case 6:
				uf = propValue;
				break;
			case 7:
				ug = propValue;
				break;
			case 8:
				hf = propValue;
				break;
			case 9:
				hg = propValue;	
				break;
			case 10:
				sf = propValue;
				break;
			case 11:
				sg = propValue;
				break;
			case 12:
				cvf = propValue;
				break;
			case 13:
				cvg = propValue;
				break;
			case 14:
				cpf = propValue;
				break;
			case 15:
				cpg = propValue;
				break;
			case 16:
				ssf = propValue;
				break;
			case 17:
				ssg = propValue;	
				break;
			case 18:
				jtf = propValue;
				break;
			case 19:
				jtg = propValue;
				break;
			case 20:
				visf = propValue;
				break;
			case 21:
				visg = propValue;
				break;
			case 22:
				tcf = propValue;
				break;
			case 23:
				tcg = propValue;	
				break;
			case 24:
				st = propValue;	
				break;
		}
	}
	
	public void setNotSatOutputsArray(int propIndex, double propValue) {
		arrayQueryOutputsNotSat.add(propIndex, propValue);
		switch(propIndex) {
			case 0:
				T = propValue;
				break;
			case 1:
				P = propValue;
				break;
			case 2:
				d = propValue;
				break;
			case 3:
				v = propValue;
				break;
			case 4:
				u = propValue;
				break;
			case 5:
				h = propValue;
				break;
			case 6:
				s = propValue;
				break;
			case 7:
				cv = propValue;
				break;
			case 8:
				cp = propValue;
				break;
			case 9:
				ss = propValue;	
				break;
			case 10:
				jt = propValue;
				break;
			case 11:
				vis = propValue;
				break;
			case 12:
				tc = propValue;
				break;
		}
	}
	
	public void setFluidState(int fluidState) {
		this.fluidState = fluidState;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setP(double p) {
		P = p;
	}

	public void setT(double t) {
		T = t;
	}
	
	public void setD(double d) {
		this.d = d;
	}

	public void setDf(double df) {
		this.df = df;
	}

	public void setDg(double dg) {
		this.dg = dg;
	}
	
	public void setV(double v) {
		this.v = v;
	}

	public void setVf(double vf) {
		this.vf = vf;
	}

	public void setVg(double vg) {
		this.vg = vg;
	}
	
	public void setU(double u) {
		this.u = u;
	}

	public void setUf(double uf) {
		this.uf = uf;
	}

	public void setUg(double ug) {
		this.ug = ug;
	}
	
	public void setH(double h) {
		this.h = h;
	}

	public void setHf(double hf) {
		this.hf = hf;
	}

	public void setHg(double hg) {
		this.hg = hg;
	}

	public void setS(double s) {
		this.s = s;
	}
	
	public void setSf(double sf) {
		this.sf = sf;
	}

	public void setSg(double sg) {
		this.sg = sg;
	}
	
	public void setCv(double cv) {
		this.cv = cv;
	}

	public void setCvf(double cvf) {
		this.cvf = cvf;
	}

	public void setCvg(double cvg) {
		this.cvg = cvg;
	}
	
	public void setCp(double cp) {
		this.cp = cp;
	}

	public void setCpf(double cpf) {
		this.cpf = cpf;
	}

	public void setCpg(double cpg) {
		this.cpg = cpg;
	}
	
	public void setSs(double ss) {
		this.ss = ss;
	}

	public void setSsf(double ssf) {
		this.ssf = ssf;
	}

	public void setSsg(double ssg) {
		this.ssg = ssg;
	}
	
	public void setJt(double jt) {
		this.jt = jt;
	}

	public void setJtf(double jtf) {
		this.jtf = jtf;
	}

	public void setJtg(double jtg) {
		this.jtg = jtg;
	}
	
	public void setVis(double vis) {
		this.vis = vis;
	}

	public void setVisf(double visf) {
		this.visf = visf;
	}

	public void setVisg(double visg) {
		this.visg = visg;
	}
	
	public void setTc(double tc) {
		this.tc = tc;
	}

	public void setTcf(double tcf) {
		this.tcf = tcf;
	}

	public void setTcg(double tcg) {
		this.tcg = tcg;
	}

	public void setSt(double st) {
		this.st = st;
	}
	
	public void setFGs() {
		this.dfg = dg - df;
		this.vfg = vg - vf;
		this.ufg = ug - uf;
		this.hfg = hg - hf;
		this.sfg = sg - sf;
		this.cvfg = cvg - cvf;
		this.cpfg = cpg - cpf;
		this.ssfg = ssg - ssf;
		this.jtfg = jtg - jtf;
		this.visfg = visg - visf;
		this.tcfg = tcg - tcf;
	}
}
