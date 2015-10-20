package com.example.termo;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import static com.example.termo.helper.ConstantsHelper.*;

import com.example.termo.helper.QueryHelper;
import com.example.termo.mvc.Mdl_QueryInputs;
import com.example.termo.mvc.Mdl_QueryOutputs;
import com.example.termo.mvc.Mdl_Results;

public class MainActivity extends Activity {

	private ArrayList<String> pres_metric_units = 
			new ArrayList<String>(Arrays.asList(METRIC_UNITS_PRES));
	private ArrayList<String> temp_metric_units = 
			new ArrayList<String>(Arrays.asList(METRIC_UNITS_TEMP));
	private ArrayList<String> vol_metric_units = 
			new ArrayList<String>(Arrays.asList(METRIC_UNITS_VOL));
	private ArrayList<String> energy_metric_units = 
			new ArrayList<String>(Arrays.asList(METRIC_UNITS_ENERGY));
	private ArrayList<String> entropy_metric_units = 
			new ArrayList<String>(Arrays.asList(METRIC_UNITS_ENTROPY));
	private ArrayList<String> xvuhs_items = 
			new ArrayList<String>(Arrays.asList(XVUHS_ITEMS));
	
	private ArrayAdapter<String> pres_units_adapter;
	private ArrayAdapter<String> temp_units_adapter;
	private ArrayAdapter<String> xvuhs_adapter;
	private ArrayAdapter<String> xvuhs_units_adapter;
	
	private Mdl_QueryInputs mQueryInputs = new Mdl_QueryInputs();
	private Mdl_QueryOutputs mQueryOutputs = new Mdl_QueryOutputs();
	private Mdl_Results mResults;
	private QueryHelper mQueryHelper = new QueryHelper(this);
	private ViewHelper mViewHelper = new ViewHelper();
	private CalcHelper mCalcHelper = new CalcHelper();
	
	private CheckBox cboxPres, cboxTemp;
	private RadioButton rbtnWater, rbtnR134a;
	private Spinner spinXvuhsUnits, spinXvuhs, spinTempUnits, spinPresUnits;
	private EditText edittextPres, edittextTemp, edittextXvuhs;
	private Button btnShowResults;
	private LinearLayout linlayHiddenPresLine, linlayHiddenTempLine;
	private TextView textviewHeader;
	private LinearLayout linlaySectionFluidType, linlaySectionPresTemp, linlaySectionXvuhs;

	private ArrayList<String> arrayOutputsSat;
	private ArrayList<String> arrayOutputsNotSat;
	
	private int fluidStateIndex;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        
        spinXvuhs.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	
            	String xvuhs_selection = parentView.getItemAtPosition(position).toString();
            	
            	if(xvuhs_selection.equals(PROP_X)) {
            		// setting related query inputs
            		mQueryInputs.setXvuhs(PROP_X);
            		mQueryInputs.setIsX(true);
            		mQueryInputs.setOthersFalse(PROP_X);
            		spinXvuhsUnits.setVisibility(View.INVISIBLE);
            		
            	} else if(xvuhs_selection.equals(PROP_V)) {
            		// setting related query inputs
            		mQueryInputs.setXvuhs(PROP_V);
            		mQueryInputs.setIsV(true);
            		mQueryInputs.setOthersFalse(PROP_V);
            		xvuhs_units_adapter.clear();
            		for(String vol_unit : vol_metric_units) {
            			xvuhs_units_adapter.add(vol_unit);
            		}
            		spinXvuhsUnits.setVisibility(View.VISIBLE);
            		
            	} else if(xvuhs_selection.equals(PROP_U)) {
            		// setting related query inputs
            		mQueryInputs.setXvuhs(PROP_U);
            		mQueryInputs.setIsU(true);
            		mQueryInputs.setOthersFalse(PROP_U);
            		xvuhs_units_adapter.clear();
            		for(String energy_unit : energy_metric_units) {
            			xvuhs_units_adapter.add(energy_unit);
            		}
            		spinXvuhsUnits.setVisibility(View.VISIBLE);
            		
            	} else if(xvuhs_selection.equals(PROP_H)) {
            		// setting related query inputs
            		mQueryInputs.setXvuhs(PROP_H);
            		mQueryInputs.setIsH(true);
            		mQueryInputs.setOthersFalse(PROP_H);
            		xvuhs_units_adapter.clear();
            		for(String energy_unit : energy_metric_units) {
            			xvuhs_units_adapter.add(energy_unit);
            		}
            		spinXvuhsUnits.setVisibility(View.VISIBLE);
            		
            	}else if(xvuhs_selection.equals(PROP_S)) {
            		// setting related query inputs
            		mQueryInputs.setXvuhs(PROP_S);
            		mQueryInputs.setIsS(true);
            		mQueryInputs.setOthersFalse(PROP_S);
            		xvuhs_units_adapter.clear();
            		for(String entropy_unit : entropy_metric_units) {
            			xvuhs_units_adapter.add(entropy_unit);
            		}
            		spinXvuhsUnits.setVisibility(View.VISIBLE);
            	}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    public void initializeViews() {
    	
    	textviewHeader = (TextView) findViewById(R.id.textview_head_queryparams);
    	
        rbtnWater = (RadioButton) findViewById(R.id.rbtn_water);
        rbtnR134a = (RadioButton) findViewById(R.id.rbtn_r134a);
        
    	cboxPres = (CheckBox) findViewById(R.id.cbox_pres);
        cboxTemp = (CheckBox) findViewById(R.id.cbox_temp);
        
        spinPresUnits = (Spinner) findViewById(R.id.spin_pres_units);
        spinTempUnits = (Spinner) findViewById(R.id.spin_temp_units);
        spinXvuhs = (Spinner) findViewById(R.id.spin_xvuhs);
        spinXvuhsUnits = (Spinner) findViewById(R.id.spin_xvuhs_units);
    	
    	edittextPres = (EditText) findViewById(R.id.edittext_pres);
        edittextTemp = (EditText) findViewById(R.id.edittext_temp);
    	edittextXvuhs = (EditText) findViewById(R.id.edittext_xvuhs);
    	
    	linlaySectionXvuhs = (LinearLayout) findViewById(R.id.linlay_section_xvuhs);
    	linlaySectionFluidType = (LinearLayout) findViewById(R.id.linlay_section_fluidtype);
    	linlaySectionPresTemp = (LinearLayout) findViewById(R.id.linlay_section_prestemp);
    	linlayHiddenPresLine = (LinearLayout) findViewById(R.id.linlay_hidden_presline);
        linlayHiddenTempLine = (LinearLayout) findViewById(R.id.linlay_hidden_templine);
    	
		pres_units_adapter = new ArrayAdapter<String>(this, R.layout.spin_item, 
	            R.id.spin_item_textview, pres_metric_units);
		temp_units_adapter = new ArrayAdapter<String>(this, R.layout.spin_item, 
				R.id.spin_item_textview, temp_metric_units);
		xvuhs_adapter = new ArrayAdapter<String>(this, R.layout.spin_item, 
				R.id.spin_item_textview, xvuhs_items);
		xvuhs_units_adapter = new ArrayAdapter<String>(this, R.layout.spin_item, 
				R.id.spin_item_textview, new ArrayList<String>());
		
        spinPresUnits.setAdapter(pres_units_adapter);
        spinTempUnits.setAdapter(temp_units_adapter);
        spinXvuhs.setAdapter(xvuhs_adapter);
        spinXvuhsUnits.setAdapter(xvuhs_units_adapter);
        
        // xvuhs seçimini başlangıçta x olarak ayarlar.
        String initial_xvuhs = PROP_X;
        if (!initial_xvuhs.equals(PROP_X)) {
            int spinnerPosition = xvuhs_adapter.getPosition(initial_xvuhs);
            spinXvuhs.setSelection(spinnerPosition);
        }
        
        rbtnWater.setChecked(true);
        cboxPres.performClick();
        
	}
    
    public void onCheckBoxClicked(View view) {
        
    	boolean isTempChecked = cboxTemp.isChecked();
        mQueryInputs.setIsTempSpecified(isTempChecked);
        
        boolean isPresChecked = cboxPres.isChecked();
        mQueryInputs.setIsPresSpecified(isPresChecked);
        
        if(isPresChecked) {
        	linlayHiddenPresLine.setVisibility(View.VISIBLE);
            mQueryInputs.setPresTempSelection("P");
        } else {
        	linlayHiddenPresLine.setVisibility(View.INVISIBLE);
        }
        
        if(isTempChecked) {
        	linlayHiddenTempLine.setVisibility(View.VISIBLE);
            mQueryInputs.setPresTempSelection("T");
        } else {
        	linlayHiddenTempLine.setVisibility(View.INVISIBLE);
        }
        
        if(isPresChecked & isTempChecked) {
        	linlaySectionXvuhs.setVisibility(View.GONE);
        	mQueryInputs.setPresTempSelection("PT");
        } else {
        	linlaySectionXvuhs.setVisibility(View.VISIBLE);
        }
    }
    
    public void fetchQueryInputs() {
    	
    	// Fluid Type Settings
    	mQueryInputs.setIsWater(rbtnWater.isChecked());
    	mQueryInputs.setIsR134a(rbtnR134a.isChecked());
    	if(rbtnWater.isChecked())
    		mQueryInputs.setFluid("water");
    	else
    		mQueryInputs.setFluid("r134a");
    	
    	// Pressure and Temperature Settings
    	// CheckBox checked status of pres and temp are pulled in onCheckBoxClicked() method
    	if(mQueryInputs.isPresSpecified()) {
    		mQueryInputs.setPresValue(mViewHelper.getEditTextValue(edittextPres));
    		mQueryInputs.setPresUnit(mViewHelper.getSpinnerValue(spinPresUnits));
    	}
    	
    	if(mQueryInputs.isTempSpecified()) {
    		mQueryInputs.setTempValue(mViewHelper.getEditTextValue(edittextTemp));
    		mQueryInputs.setTempUnit(mViewHelper.getSpinnerValue(spinTempUnits));
    	}
    	
    	// xvuhs Settings
    	// Spinner selected status of xvuhs are pullen in onCheckBoxClicked() method
    	String xvuhsUnit = "";
    	if(!mQueryInputs.isX()) {
    		xvuhsUnit = mViewHelper.getSpinnerValue(spinXvuhsUnits);
    	}
    	if(mQueryInputs.isX()) {
    		mQueryInputs.setXValue(mViewHelper.getEditTextValue(edittextXvuhs));
    	} else if(mQueryInputs.isV()) {
    		mQueryInputs.setVValue(mViewHelper.getEditTextValue(edittextXvuhs));
    		mQueryInputs.setVolUnit(xvuhsUnit);
    	} else if(mQueryInputs.isU()) {
    		mQueryInputs.setUValue(mViewHelper.getEditTextValue(edittextXvuhs));
    		mQueryInputs.setEnergyUnit(xvuhsUnit);
    	} else if(mQueryInputs.isH()) {
    		mQueryInputs.setHValue(mViewHelper.getEditTextValue(edittextXvuhs));
    		mQueryInputs.setEnergyUnit(xvuhsUnit);
    	} else if(mQueryInputs.isS()) {
    		mQueryInputs.setSValue(mViewHelper.getEditTextValue(edittextXvuhs));
    		mQueryInputs.setEntropyUnit(xvuhsUnit);
    	}
    }
    
    
    public void setResults() {
    	
    	int fluidTypeIndex;
    	
    	// Use A4,5,6,7 tables if fluid is water
	    if(mQueryInputs.getFluid().equals("water")) {
	    	
	    	fluidTypeIndex = FLUID_TYPE_WATER;
	    	
    		if(mQueryInputs.getPresTempSelection().equals("P")) {
    			
    			String presTempSelection = "P";
    			double presTempValue = mQueryInputs.getPresValue();
    			
    			mCalcHelper.callProperCalcMethod(fluidTypeIndex, presTempSelection, presTempValue);
	    	}
    		
	    	else if(mQueryInputs.getPresTempSelection().equals("T")) {
	    		
	    		String presTempSelection = "T";
	    		double presTempValue = mQueryInputs.getTempValue();
    			
	    		mCalcHelper.callProperCalcMethod(fluidTypeIndex, presTempSelection, presTempValue);
	    	}
    		
	    	else if(mQueryInputs.getPresTempSelection().equals("PT")) {
	    		
	    		String presTempSelection = "PT";
	    	}
	    }
	    // Use A11,12,13 tables if fluid is r134a
	    else if(mQueryInputs.getFluid().equals("r134a")){
	    	
	    	fluidTypeIndex = FLUID_TYPE_R134A;
	    	
	    	if(mQueryInputs.getPresTempSelection().equals("P")) {
    			
	    		String presTempSelection = "P";
    			double presTempValue = mQueryInputs.getPresValue();
    			
    			mCalcHelper.callProperCalcMethod(fluidTypeIndex, presTempSelection, presTempValue);
	    	}
    		
	    	else if(mQueryInputs.getPresTempSelection().equals("T")) {
	    		
	    		String presTempSelection = "T";
	    		double presTempValue = mQueryInputs.getTempValue();
    			
	    		mCalcHelper.callProperCalcMethod(fluidTypeIndex, presTempSelection, presTempValue);
	    	}
    		
	    	else if(mQueryInputs.getPresTempSelection().equals("PT")) {
	    		
	    		String presTempSelection = "PT";
	    	}

	    }
    	
    }
    
    public void showResults(View view) {
    	fetchQueryInputs();
    	setResults();
    	
    	Intent intent = new Intent(this, ResultsActivity.class);
    	intent.putExtra("inputs", mQueryInputs);
    	intent.putExtra("results", mResults);
    	intent.putExtra("fluidState", fluidStateIndex);
        startActivity(intent);
    }
    
    public class ViewHelper {
    	
    	public ViewHelper() {
    		
    	}
    	
    	public double getEditTextValue(EditText edittext) {
        	return Double.parseDouble(edittext.getText().toString());
        }
        
        public String getSpinnerValue(Spinner spinner) {
        	return spinner.getSelectedItem().toString();
        }
        
    }

    public class CalcHelper {
    	
    	public CalcHelper() {
    		
    	}
    	
    	public void callProperCalcMethod(int fluidTypeIndex, String presTempSelection, double presTempValue) {
    		
    		if(mQueryInputs.isX()) {
    			double xValue = mQueryInputs.getXValue();
    			mCalcHelper.calculateWaterPandX(fluidTypeIndex, presTempSelection, presTempValue, xValue);

    		} else if(mQueryInputs.isV()) {
    			double vValue = mQueryInputs.getVValue();
    			mCalcHelper.calculateWaterPandVUHS(fluidTypeIndex, presTempSelection, presTempValue, PROP_INDEX_V, PROP_V, vValue);
    			
    		} else if(mQueryInputs.isU()) {
    			double uValue = mQueryInputs.getUValue();
    			mCalcHelper.calculateWaterPandVUHS(fluidTypeIndex, presTempSelection, presTempValue, PROP_INDEX_U, PROP_U, uValue);
    			
    		} else if(mQueryInputs.isH()) {
    			double hValue = mQueryInputs.getHValue();
    			mCalcHelper.calculateWaterPandVUHS(fluidTypeIndex, presTempSelection, presTempValue, PROP_INDEX_H, PROP_H, hValue);
    			
    		} else {
    			double sValue = mQueryInputs.getSValue();
    			mCalcHelper.calculateWaterPandVUHS(fluidTypeIndex, presTempSelection, presTempValue, PROP_INDEX_S, PROP_S, sValue);
    			
    		} 
    	}
    	
    	public void calculateWaterPandX(int fluidTypeIndex, String presTempSelection, double presTempValue, double xValue) {
    		
    		fluidStateIndex = FLUID_STATE_SATURATED;
    		
			mQueryOutputs = mQueryHelper.getQueryOutputsFromSatP(fluidTypeIndex, presTempSelection, presTempValue);
        	mQueryOutputs.setFGs();
        	mQueryOutputs.setX(xValue);
        	mQueryOutputs.setFluidState(fluidStateIndex);
        	
        	mResults = mQueryHelper.getResults(mQueryOutputs, fluidStateIndex);
    	}
    	
    	public void calculateWaterPandVUHS(int fluidTypeIndex, String presTempSelection, double presTempValue, 
    									   		int vuhsIndex, String vuhs, double vuhsValue) {
    		
    		mQueryOutputs = mQueryHelper.getQueryOutputsFromSatP(fluidTypeIndex, presTempSelection, presTempValue);
        	mQueryOutputs.setFGs();
        	
        	takeResultsWhenStateUnknown(fluidTypeIndex, presTempSelection, presTempValue, vuhsIndex, vuhs, vuhsValue);
    	}
    	
    	private void takeResultsWhenStateUnknown(int fluidTypeIndex, String presTempSelection, double presTempValue, int vuhsIndex, String vuhs, double vuhsValue) {
			
    		// findstate() finds fluid state of outputs and sets x if is saturated
        	mQueryOutputs = mQueryHelper.findState(vuhsIndex, vuhsValue, mQueryOutputs);
    		
			switch(mQueryOutputs.getFluidState())
			{
				case FLUID_STATE_COMPRESSED:
				{
					fluidStateIndex = FLUID_STATE_COMPRESSED;
					mQueryOutputs = new Mdl_QueryOutputs();
					mQueryOutputs = mQueryHelper.getQueryOutputsFromSupP(fluidTypeIndex, presTempSelection, presTempValue, 
																			vuhsIndex, vuhs, vuhsValue, fluidStateIndex);
					mResults = mQueryHelper.getResults(mQueryOutputs, fluidStateIndex);
					break;
				}
				case FLUID_STATE_SATURATED:
				{
					fluidStateIndex = FLUID_STATE_SATURATED;
					mQueryInputs.setXValue(mQueryOutputs.getX());
					mResults = mQueryHelper.getResults(mQueryOutputs, fluidStateIndex);
    				break;
				}
				case FLUID_STATE_SUPERHEATED:
				{
					fluidStateIndex = FLUID_STATE_SUPERHEATED;
					mQueryOutputs = new Mdl_QueryOutputs();
					mQueryOutputs = mQueryHelper.getQueryOutputsFromSupP(fluidTypeIndex, presTempSelection, presTempValue, 
																			vuhsIndex, vuhs, vuhsValue, fluidStateIndex);
					mResults = mQueryHelper.getResults(mQueryOutputs, fluidStateIndex);
					break;
				}
			}
    	}
    	
    } 
}
