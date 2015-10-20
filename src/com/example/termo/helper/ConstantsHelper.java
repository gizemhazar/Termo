package com.example.termo.helper;

import java.util.Map;

public class ConstantsHelper {
	public final static String[] METRIC_UNITS_PRES    = {"MPa", "kPa", "Pa"};
	public final static String[] METRIC_UNITS_TEMP 	  = {"°C", "K"};
	public final static String[] METRIC_UNITS_VOL  	  = {"m3/kg", "l/g", "dm3/g"};
	public final static String[] METRIC_UNITS_ENERGY  = {"kJ/kg", "J/g"};
	public final static String[] METRIC_UNITS_ENTROPY = {"kJ/kg*K", "J/g*K"};
	
	public final static String[] XVUHS_ITEMS 		  = {"x","v","u","h","s"};
	
	public final static int PROP_INDEX_V = 1;
	public final static int PROP_INDEX_U = 2;
	public final static int PROP_INDEX_H = 3;
	public final static int PROP_INDEX_S = 4;
	
	public static final String PROP_V = "v";
	public static final String PROP_U = "u";
	public static final String PROP_H = "h";
	public static final String PROP_S = "s";
	public static final String PROP_X = "x";
	
	public final static int FLUID_STATE_ERROR = -1;
	public final static int FLUID_STATE_SATURATED = 0;
	public final static int FLUID_STATE_NOTSATURATED = 1;
	public final static int FLUID_STATE_COMPRESSED = 2;
	public final static int FLUID_STATE_SUPERHEATED = 3;
	
	public final static int FLUID_TYPE_WATER = 0;
	public final static int FLUID_TYPE_R134A = 1;
	
	public final static int ARRAY_RESULTS_BASIC = 0;
	public final static int ARRAY_RESULTS_ALLSAT = 1;
	public final static int ARRAY_RESULTS_ALLNOTSAT = 2;
	
	public static final int ADAPTER_BASIC = 0;
	public static final int ADAPTER_SAT = 1;
	public static final int ADAPTER_NOTSAT = 2;
	
	public final static String[] PROPERTIES_BASIC = {"P", "T", "d", "v", "u", "h", "s"};
	public final static String[] PROPERTIES_NOTSAT = 
									{"P", "T", "d", "v", "u", "h", "s", "cv", "cp", "ss", "jt", "vis", "tc"};
	public final static String[] PROPERTIES_SAT = 
									{"P", "T", "d", "v", "u", "h", "s", "cv", "cp", "ss", "jt", "vis", "tc", "st"};
	
	public final static String TABLE_WATER_SAT_T = "a4";
	public final static String TABLE_WATER_SUP_T = "";
	public final static String TABLE_WATER_COM_T = "";
	public final static String TABLE_WATER_SAT_P = "a5";
	public final static String TABLE_WATER_SUP_P = "a6";
	public final static String TABLE_WATER_COM_P = "a7";
	public final static String TABLE_R134A_SAT_T = "a11";
	public final static String TABLE_R134A_SUP_T = "";
	public final static String TABLE_R134A_COM_T = "";
	public final static String TABLE_R134A_SAT_P = "a12";
	public final static String TABLE_R134A_SUP_P = "a13";
	public final static String TABLE_R134A_COM_P = "a14";
	
	public final static double[] TABLE_WATER_SUP_P_VALUES = { 0.01, 0.03, 0.05, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 
													  0.9, 1, 1.2, 1.4, 1.6, 1.8, 2, 2.5, 3, 3.5, 4, 4.5, 
													  5, 6, 7, 8, 9, 10, 12.5, 15, 17.5, 20, 25, 30,
													  35, 40, 45, 50, 55, 60, 80, 100 };
	
	public final static double[] TABLE_WATER_COM_P_VALUES = { 5, 10, 15, 20, 30, 50 };
	public final static double[] TABLE_WATER_SAT_T_VALUES = {  };
	public final static double[] TABLE_WATER_COM_T_VALUES = {  };
	
	public final static double[] TABLE_R134A_SUP_P_VALUES = { 0.06, 0.08, 0.1, 0.12, 0.14, 0.16, 0.18, 0.2, 0.24, 0.28,
													   0.32, 0.36, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.2, 1.4, 1.6 };
	public final static double[] TABLE_R134A_COM_P_VALUES = {  };
	public final static double[] TABLE_R134A_SUP_T_VALUES = {  };
	public final static double[] TABLE_R134A_COM_T_VALUES = {  };
}
