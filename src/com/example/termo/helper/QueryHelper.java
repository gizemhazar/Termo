package com.example.termo.helper;

import static com.example.termo.helper.ConstantsHelper.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.termo.mvc.Mdl_QueryOutputs;
import com.example.termo.mvc.Mdl_Results;

public class QueryHelper {
	
	private Map<Double, String> a6Tables = new HashMap<Double, String>();
	private Map<Double, String> a7Tables = new HashMap<Double, String>();
	private Map<Double, String> a13Tables = new HashMap<Double, String>();
	
	protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;
	
    public QueryHelper(Context context)
    {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
        setNotSaturatedTableNames();
    }
    
    private void setNotSaturatedTableNames() {
    	DecimalFormat numFormat = new DecimalFormat("###.###");
    	
    	for(int i = 0; i < TABLE_WATER_SUP_P_VALUES.length; i++) {
			a6Tables.put(TABLE_WATER_SUP_P_VALUES[i], ("a6_" + numFormat.format(TABLE_WATER_SUP_P_VALUES[i]))
																	.replace(".", "_").replace(",", "_"));
		}
    	
    	for(int i = 0; i < TABLE_WATER_COM_P_VALUES.length; i++) {
    		a7Tables.put(TABLE_WATER_COM_P_VALUES[i], ("a7_" + numFormat.format(TABLE_WATER_COM_P_VALUES[i]))
    																.replace(".", "_").replace(",", "_"));
		}
    	
    	for(int i = 0; i < TABLE_R134A_SUP_P_VALUES.length; i++) {
    		a13Tables.put(TABLE_R134A_SUP_P_VALUES[i], ("a13_" + numFormat.format(TABLE_R134A_SUP_P_VALUES[i]))
    																.replace(".", "_").replace(",", "_"));
		}
    }
    
    private Map<Double, String> getNotSaturatedTableNames(String table) {
    	if(table.equals(TABLE_WATER_SUP_P)) {
    		return a6Tables;
    	} else if(table.equals(TABLE_WATER_COM_P)) {
    		return a7Tables;
    	} else if(table.equals(TABLE_R134A_SUP_P)) {
    		return a13Tables;
    	} else {
    		return null;
    	}
    }
	
    public QueryHelper createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public QueryHelper open() throws SQLException
    {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }
    
    public Cursor getQueryResponse(String sql)
    {
        try
        {
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "getQueryResponse >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }

	/**
	 * Get P value and calculates all properties corresponding to this P value
	 * and returns this output object
	 * @param presTempValue P value which is not directly on table A5 
	 * @return mQueryOutputsSat query outputs for given P value
	 */
	public Mdl_QueryOutputs getQueryOutputsFromSatP(int fluidTypeIndex, String presTempSelection, double presTempValue) {
		
		Mdl_QueryOutputs mQueryOutputs = new Mdl_QueryOutputs();
		Mdl_QueryOutputs mQueryOutputsLowerPT = new Mdl_QueryOutputs();
		Mdl_QueryOutputs mQueryOutputsUpperPT = new Mdl_QueryOutputs();
		
		String table;
		String PorT;
		
		if(presTempSelection.equals("P")) {
			PorT = "P";
			switch(fluidTypeIndex) {
				case FLUID_TYPE_WATER:
				{
					table = TABLE_WATER_SAT_P;
					break;
				}
				default: //case FLUID_TYPE_R134A:
				{
					table = TABLE_R134A_SAT_P;
					break;
				}
			}
		} else {
			PorT = "T";
			switch(fluidTypeIndex) {
				case FLUID_TYPE_WATER:
				{
					table = TABLE_WATER_SAT_T;
					break;
				}
				default: //case FLUID_TYPE_R134A:
				{
					table = TABLE_R134A_SAT_T;
					break;
				}
			}
		}
		
		String sqlLower ="SELECT * FROM " + table + " WHERE " + PorT + "<=" + presTempValue + " ORDER BY " + PorT + " DESC";
		String sqlUpper ="SELECT * FROM " + table + " WHERE " + PorT + ">" + presTempValue + " ORDER BY " + PorT + " ASC";
		mQueryOutputsLowerPT = getQueryOutputs(sqlLower, FLUID_STATE_SATURATED);
		mQueryOutputsUpperPT = getQueryOutputs(sqlUpper, FLUID_STATE_SATURATED);
		
		double presTempLower;
		double presTempUpper;
		if(PorT.equals("P")) {
			presTempLower = mQueryOutputsLowerPT.getP();
			presTempUpper = mQueryOutputsUpperPT.getP();
		} else {
			presTempLower = mQueryOutputsLowerPT.getT();
			presTempUpper = mQueryOutputsUpperPT.getT();
		}
		double inpPT = (presTempValue - presTempLower) / (presTempUpper - presTempLower);
		
		ArrayList<Double> arrayOutputsLowerPT = mQueryOutputsLowerPT.getSatOutputsAsArray();
		ArrayList<Double> arrayOutputsUpperPT = mQueryOutputsUpperPT.getSatOutputsAsArray();
		
		/**
		 * Gets each properties from upper and lower arrays
		 * by using interpolation calculates all properties 
		 * and sets up desired Mdl_QueryOutputsSat object
		 */
		for(int index = 0; index < arrayOutputsLowerPT.size(); index++) {
			double lowerProp = arrayOutputsLowerPT.get(index);
			double upperProp = arrayOutputsUpperPT.get(index);
			double prop = lowerProp + inpPT * (upperProp - lowerProp);
			mQueryOutputs.setSatOutputsArray(index, prop);
		}
		
		return mQueryOutputs;
	}
	
	/**
	 * Get P value and calculates all properties corresponding to this P value
	 * and returns this output object
	 * @param presTempValue P value which is not directly on table A5 
	 * @return mQueryOutputsSat query outputs for given P value
	 */
	public Mdl_QueryOutputs getQueryOutputsFromSupP(int fluidTypeIndex, String presTempSelection, double presTempValue, 
									int vuhsIndex, String vuhs, double vuhsValue, int fluidStateIndex) {
		
		Mdl_QueryOutputs mQueryOutputs = new Mdl_QueryOutputs();
		Mdl_QueryOutputs mQONSLowerPTLowerVUHS = new Mdl_QueryOutputs();
		Mdl_QueryOutputs mQONSLowerPTUpperVUHS = new Mdl_QueryOutputs();
		Mdl_QueryOutputs mQONSUpperPTLowerVUHS = new Mdl_QueryOutputs();
		Mdl_QueryOutputs mQONSUpperPTUpperVUHS = new Mdl_QueryOutputs();
		
		String table;
		String PorT;
		String sortByPorT;
		if(presTempSelection.equals("P")) {
			sortByPorT = "T";
			switch(fluidTypeIndex) {
				case FLUID_TYPE_WATER:
				{
					if (fluidStateIndex == FLUID_STATE_SUPERHEATED) {
						table = TABLE_WATER_SUP_P;
					} else {
						table = TABLE_WATER_COM_P;
					}
					break;
				}
				default: //case FLUID_TYPE_R134A:
				{
					if (fluidStateIndex == FLUID_STATE_SUPERHEATED) {
						table = TABLE_R134A_SUP_P;
					} else {
						table = TABLE_R134A_COM_P;
					}
					break;
				}
			}
		} else {
			sortByPorT = "P";
			switch(fluidTypeIndex) {
				case FLUID_TYPE_WATER:
				{
					if (fluidStateIndex == FLUID_STATE_SUPERHEATED) {
						table = TABLE_WATER_SUP_T;
					} else {
						table = TABLE_WATER_COM_T;
					}
					break;
				}
				default: //case FLUID_TYPE_R134A:
				{
					if (fluidStateIndex == FLUID_STATE_SUPERHEATED) {
						table = TABLE_R134A_SUP_T;
					} else {
						table = TABLE_R134A_COM_T;
					}
					break;
				}
			}
		}
		
		double[] lowUp = getLowUpPTValuesAndTables(presTempValue, table);
		double presTempLower = lowUp[0];
		double presTempUpper = lowUp[1];
		// TODO : Methods for getting table names will be updated after revising database
		String tableLowerPT = getNotSaturatedTableNames(table).get(presTempLower);
		String tableUpperPT = getNotSaturatedTableNames(table).get(presTempUpper);
		
		String sqlLowerPTLowerVUHS =
				"SELECT * FROM " + tableLowerPT + " WHERE " + vuhs + "<=" + vuhsValue + " ORDER BY " + sortByPorT + " DESC";
		String sqlLowerPTUpperVUHS =
				"SELECT * FROM " + tableLowerPT + " WHERE " + vuhs + ">"  + vuhsValue + " ORDER BY " + sortByPorT + " ASC";
		String sqlUpperPTLowerVUHS =
				"SELECT * FROM " + tableUpperPT + " WHERE " + vuhs + "<=" + vuhsValue + " ORDER BY " + sortByPorT + " DESC";
		String sqlUpperPTUpperVUHS =
				"SELECT * FROM " + tableUpperPT + " WHERE " + vuhs + ">"  + vuhsValue + " ORDER BY " + sortByPorT + " ASC";
		
		mQONSLowerPTLowerVUHS = getQueryOutputs(sqlLowerPTLowerVUHS, FLUID_STATE_NOTSATURATED);
		mQONSLowerPTUpperVUHS = getQueryOutputs(sqlLowerPTUpperVUHS, FLUID_STATE_NOTSATURATED);
		mQONSUpperPTLowerVUHS = getQueryOutputs(sqlUpperPTLowerVUHS, FLUID_STATE_NOTSATURATED);
		mQONSUpperPTUpperVUHS = getQueryOutputs(sqlUpperPTUpperVUHS, FLUID_STATE_NOTSATURATED);
		
		ArrayList<Double> arrayOutpusLowerPTLowerVUHS = mQONSLowerPTLowerVUHS.getNotSatOutputsAsArray();
		ArrayList<Double> arrayOutputsLowerPTUpperVUHS = mQONSLowerPTUpperVUHS.getNotSatOutputsAsArray();
		ArrayList<Double> arrayOutpusUpperPTLowerVUHS = mQONSUpperPTLowerVUHS.getNotSatOutputsAsArray();
		ArrayList<Double> arrayOutputsUpperPTUpperVUHS = mQONSUpperPTUpperVUHS.getNotSatOutputsAsArray();
		ArrayList<Double> arrayOutputsLowerPT = new ArrayList<Double>();
		ArrayList<Double> arrayOutputsUpperPT = new ArrayList<Double>();
		
		double lowerPTLowerVUHS = mQONSLowerPTLowerVUHS.getVuhsValue(vuhsIndex);
		double lowerPTUpperVUHS = mQONSLowerPTUpperVUHS.getVuhsValue(vuhsIndex);
		double upperPTLowerVUHS = mQONSUpperPTLowerVUHS.getVuhsValue(vuhsIndex);
		double upperPTUpperVUHS = mQONSUpperPTUpperVUHS.getVuhsValue(vuhsIndex);
		
		double inpVUHSLowerPT = (vuhsValue - lowerPTLowerVUHS) / (lowerPTUpperVUHS - lowerPTLowerVUHS);
		double inpVUHSUpperPT = (vuhsValue - upperPTLowerVUHS) / (upperPTUpperVUHS - upperPTLowerVUHS);
		
		for(int index = 0; index < arrayOutpusLowerPTLowerVUHS.size(); index++) {
			double lowerPTLowerProp = arrayOutpusLowerPTLowerVUHS.get(index);
			double lowerPTUpperProp = arrayOutputsLowerPTUpperVUHS.get(index);
			double lowerPTProp = lowerPTLowerProp + inpVUHSLowerPT * (lowerPTUpperProp - lowerPTLowerProp);
			arrayOutputsLowerPT.add(index, lowerPTProp);
			
			double upperPTLowerProp = arrayOutpusUpperPTLowerVUHS.get(index);
			double upperPTUpperProp = arrayOutputsUpperPTUpperVUHS.get(index);
			double upperPTProp = upperPTLowerProp + inpVUHSUpperPT * (upperPTUpperProp - upperPTLowerProp);
			arrayOutputsUpperPT.add(index, upperPTProp);
		}
		
		double inpPT = (presTempValue - presTempLower) / (presTempUpper - presTempLower);

		/**
		 * Gets each properties from upper and lower arrays
		 * by using interpolation calculates all properties 
		 * and sets up desired Mdl_QueryOutputsSat object
		 */
		for(int index = 0; index < arrayOutputsLowerPT.size(); index++) {
			double lowerPTProp = arrayOutputsLowerPT.get(index);
			double upperPTProp = arrayOutputsUpperPT.get(index);
			double prop = lowerPTProp + inpPT * (upperPTProp - lowerPTProp);
			mQueryOutputs.setNotSatOutputsArray(index, prop);
		}
		
		return mQueryOutputs;
	}
	
	public Mdl_Results getResults(Mdl_QueryOutputs mQueryOutputs, int fluidStateIndex) {
		
		Mdl_Results mResults = new Mdl_Results();
		
		mResults.setP(mQueryOutputs.getP());
    	mResults.setT(mQueryOutputs.getT());
        
		if(fluidStateIndex == FLUID_STATE_SUPERHEATED || fluidStateIndex == FLUID_STATE_COMPRESSED) {
			mResults.setV(mQueryOutputs.getV());
	    	mResults.setD(mQueryOutputs.getD());
	    	mResults.setU(mQueryOutputs.getU());
	    	mResults.setH(mQueryOutputs.getH());
	    	mResults.setS(mQueryOutputs.getS());
	    	mResults.setCv(mQueryOutputs.getCv());
	    	mResults.setCp(mQueryOutputs.getCp());
	    	mResults.setSs(mQueryOutputs.getSs());
	    	mResults.setJt(mQueryOutputs.getJt());
	    	mResults.setVis(mQueryOutputs.getVis());
	    	mResults.setTc(mQueryOutputs.getTc());
		} else {
	    	double vf = mQueryOutputs.getVf();
	    	double vfg = mQueryOutputs.getVfg();
	    	double uf = mQueryOutputs.getUf();
	    	double ufg = mQueryOutputs.getUfg();
	    	double hf = mQueryOutputs.getHf();
	    	double hfg = mQueryOutputs.getHfg();
	    	double sf = mQueryOutputs.getSf();
	    	double sfg = mQueryOutputs.getSfg();
	    	double cvf = mQueryOutputs.getCvf();
	    	double cvfg = mQueryOutputs.getCvfg();
	    	double cpf = mQueryOutputs.getCpf();
	    	double cpfg = mQueryOutputs.getCpfg();
	    	double ssf = mQueryOutputs.getSsf();
	    	double ssfg = mQueryOutputs.getSsfg();
	    	double jtf = mQueryOutputs.getJtf();
	    	double jtfg = mQueryOutputs.getJtfg();
	    	double visf = mQueryOutputs.getVisf();
	    	double visfg = mQueryOutputs.getVisfg();
	    	double tcf = mQueryOutputs.getTcf();
	    	double tcfg = mQueryOutputs.getTcfg();
	    	
	    	double x = mQueryOutputs.getX();
	    	
	    	mResults.setV(vf + x*vfg);
	    	mResults.setD(1 / mResults.getV());
	    	mResults.setU(uf + x*ufg);
	    	mResults.setH(hf + x*hfg);
	    	mResults.setS(sf + x*sfg);
	    	mResults.setCv(cvf + x*cvfg);
	    	mResults.setCp(cpf + x*cpfg);
	    	mResults.setSs(ssf + x*ssfg);
	    	mResults.setJt(jtf + x*jtfg);
	    	mResults.setVis(visf + x*visfg);
	    	mResults.setTc(tcf + x*tcfg);
	    	mResults.setSt(mQueryOutputs.getSt());
		}
        
        return mResults;
	}

	/**
	 * Finds fluid state and sets x and fluidStateIndex of mQueryOutput if is saturated
	 * @param vuhsIndex
	 * @param vuhsValue
	 * @param mQueryOutputs
	 * @return
	 */
	public Mdl_QueryOutputs findState(int vuhsIndex, double vuhsValue, Mdl_QueryOutputs mQueryOutputs) {
		
		double vuhsF = 0;
		double vuhsG = 0;
		
		switch(vuhsIndex) {
			case PROP_INDEX_V:
			{
				vuhsF = mQueryOutputs.getVf();
				vuhsG = mQueryOutputs.getVg();
				break;
			}
			case PROP_INDEX_U:
			{
				vuhsF = mQueryOutputs.getUf();
				vuhsG = mQueryOutputs.getUg();
				break;
			}
			case PROP_INDEX_H:
			{
				vuhsF = mQueryOutputs.getHf();
				vuhsG = mQueryOutputs.getHg();
				break;
			}
			case PROP_INDEX_S:
			{
				vuhsF = mQueryOutputs.getSf();
				vuhsG = mQueryOutputs.getSg();
				break;
			}
		}
		
		if(vuhsValue < vuhsF) {
			mQueryOutputs.setFluidState(FLUID_STATE_COMPRESSED);
			return mQueryOutputs;
		} else if(vuhsValue > vuhsG) {
			mQueryOutputs.setFluidState(FLUID_STATE_SUPERHEATED);
			return mQueryOutputs;
		} else {
			mQueryOutputs.setFluidState(FLUID_STATE_SATURATED);
			mQueryOutputs.setX((vuhsValue - vuhsF) / (vuhsG - vuhsF));
			return mQueryOutputs;
		}
	}

	private Mdl_QueryOutputs getQueryOutputs(String sql, int fluidStateIndex) {
		
		Mdl_QueryOutputs mQueryOutputs = new Mdl_QueryOutputs();
		
		createDatabase();
        open();
        Cursor testdata = getQueryResponse(sql);
        
        if(testdata.moveToFirst()) {
        	
        	mQueryOutputs.setT(testdata.getDouble(0));
        	mQueryOutputs.setP(testdata.getDouble(1));
        	
	        if(fluidStateIndex == FLUID_STATE_SATURATED) {
	        	mQueryOutputs.setDf(testdata.getDouble(2));
	        	mQueryOutputs.setDg(testdata.getDouble(3));
	        	mQueryOutputs.setVf(testdata.getDouble(4));
	        	mQueryOutputs.setVg(testdata.getDouble(5));
	        	mQueryOutputs.setUf(testdata.getDouble(6));
	        	mQueryOutputs.setUg(testdata.getDouble(7));
	        	mQueryOutputs.setHf(testdata.getDouble(8));
	        	mQueryOutputs.setHg(testdata.getDouble(9));
	        	mQueryOutputs.setSf(testdata.getDouble(10));
	        	mQueryOutputs.setSg(testdata.getDouble(11));
	        	mQueryOutputs.setCvf(testdata.getDouble(12));
	        	mQueryOutputs.setCvg(testdata.getDouble(13));
	        	mQueryOutputs.setCpf(testdata.getDouble(14));
	        	mQueryOutputs.setCpg(testdata.getDouble(15));
	        	mQueryOutputs.setSsf(testdata.getDouble(16));
	        	mQueryOutputs.setSsg(testdata.getDouble(17));
	        	mQueryOutputs.setJtf(testdata.getDouble(18));
	        	mQueryOutputs.setJtg(testdata.getDouble(19));
	        	mQueryOutputs.setVisf(testdata.getDouble(20));
	        	mQueryOutputs.setVisg(testdata.getDouble(21));
	        	mQueryOutputs.setTcf(testdata.getDouble(22));
	        	mQueryOutputs.setTcg(testdata.getDouble(23));
	        	mQueryOutputs.setSt(testdata.getDouble(24));
	        } else {
            	mQueryOutputs.setD(testdata.getDouble(2));
            	mQueryOutputs.setV(testdata.getDouble(3));
            	mQueryOutputs.setU(testdata.getDouble(4));
            	mQueryOutputs.setH(testdata.getDouble(5));
            	mQueryOutputs.setS(testdata.getDouble(6));
            	mQueryOutputs.setCv(testdata.getDouble(7));
            	mQueryOutputs.setCp(testdata.getDouble(8));
            	mQueryOutputs.setSs(testdata.getDouble(9));
            	mQueryOutputs.setJt(testdata.getDouble(10));
            	mQueryOutputs.setVis(testdata.getDouble(11));
            	mQueryOutputs.setTc(testdata.getDouble(12));
	        }
        }

        mDbHelper.close();
        
		return mQueryOutputs;
	}

	private double[] getLowUpPTValuesAndTables(double presValue, String table) {
		
		double[] lowUpP = new double[2];
		
		if(table.equals(TABLE_WATER_SUP_P)) {
			for(int i = 0; i < TABLE_WATER_SUP_P_VALUES.length - 1; i++) {
				if(presValue >= TABLE_WATER_SUP_P_VALUES[i] & presValue <= TABLE_WATER_SUP_P_VALUES[i+1]) {
					lowUpP[0] = TABLE_WATER_SUP_P_VALUES[i];
					lowUpP[1] = TABLE_WATER_SUP_P_VALUES[i+1];
				}
			}
		} else if(table.equals(TABLE_WATER_COM_P)) {
			for(int i = 0; i < TABLE_WATER_COM_P_VALUES.length - 1; i++) {
				if(presValue >= TABLE_WATER_COM_P_VALUES[i] & presValue <= TABLE_WATER_COM_P_VALUES[i+1]) {
					lowUpP[0] = TABLE_WATER_COM_P_VALUES[i];
					lowUpP[1] = TABLE_WATER_COM_P_VALUES[i+1];
				}
			}
		} else if(table.equals(TABLE_R134A_SUP_P)) {
			for(int i = 0; i < TABLE_R134A_SUP_P_VALUES.length - 1; i++) {
				if(presValue >= TABLE_R134A_SUP_P_VALUES[i] & presValue <= TABLE_R134A_SUP_P_VALUES[i+1]) {
					lowUpP[0] = TABLE_R134A_SUP_P_VALUES[i];
					lowUpP[1] = TABLE_R134A_SUP_P_VALUES[i+1];
				}
			}
		} else if(table.equals(TABLE_R134A_COM_P)) {
			for(int i = 0; i < TABLE_R134A_COM_P_VALUES.length - 1; i++) {
				if(presValue >= TABLE_R134A_COM_P_VALUES[i] & presValue <= TABLE_R134A_COM_P_VALUES[i+1]) {
					lowUpP[0] = TABLE_R134A_COM_P_VALUES[i];
					lowUpP[1] = TABLE_R134A_COM_P_VALUES[i+1];
				}
			}
		}
		
		return lowUpP;
	}
}