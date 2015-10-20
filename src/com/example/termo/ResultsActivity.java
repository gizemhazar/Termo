package com.example.termo;

import java.util.ArrayList;
import java.util.TreeSet;

import com.example.termo.helper.ConstantsHelper;
import com.example.termo.mvc.Mdl_QueryInputs;
import com.example.termo.mvc.Mdl_Results;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import static com.example.termo.helper.ConstantsHelper.*;

public class ResultsActivity extends Activity {

	private CustomAdapter resultsAdapterBasic;
	private CustomAdapter resultsAdapterSat;
	private CustomAdapter resultsAdapterNotSat;
	private ArrayList<String> arrayResultsAllSat = new ArrayList<String>();;
	private ArrayList<String> arrayResultsAllNotSat = new ArrayList<String>();
	private ArrayList<String> arrayResultsBasic = new ArrayList<String>();
	
	ListView listView;
	private boolean isMoreProps = false;
	int fluidStateIndex;
	String fluidState;
	Mdl_Results mResults;
	Mdl_QueryInputs mQueryInputs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		mResults = (Mdl_Results) getIntent().getSerializableExtra("results");
		mQueryInputs = (Mdl_QueryInputs) getIntent().getSerializableExtra("inputs");
		fluidStateIndex = getIntent().getIntExtra("fluidState", ConstantsHelper.FLUID_STATE_ERROR);
		
		switch(fluidStateIndex) {
			case ConstantsHelper.FLUID_STATE_SATURATED:
				fluidState = "SATURATED";
				break;
			case ConstantsHelper.FLUID_STATE_COMPRESSED:
				fluidState = "COMPRESSED";
				break;
			case ConstantsHelper.FLUID_STATE_SUPERHEATED:
				fluidState = "SUPERHEATED";
				break;
		}
		
		takeResults();
		
		resultsAdapterBasic = new CustomAdapter(arrayResultsBasic, ADAPTER_BASIC, R.layout.list_morebutton_results);
		resultsAdapterSat = new CustomAdapter(arrayResultsAllSat, ADAPTER_SAT, R.layout.list_lessbutton_results);
		resultsAdapterNotSat = new CustomAdapter(arrayResultsAllNotSat, ADAPTER_NOTSAT, R.layout.list_lessbutton_results);
		
        listView = (ListView) findViewById(R.id.listview_results);
        listView.setAdapter(resultsAdapterBasic);
	}
	
	private void takeResults() {
		
		arrayResultsBasic = mResults.getResultsAsArray(ConstantsHelper.ARRAY_RESULTS_BASIC );
		arrayResultsBasic.add(0, "Fluid State = " + fluidState);
		
		if(fluidStateIndex == ConstantsHelper.FLUID_STATE_SATURATED) {
			arrayResultsAllSat = mResults.getResultsAsArray(ConstantsHelper.ARRAY_RESULTS_ALLSAT);
			arrayResultsAllSat.add(0, "Fluid State = " + fluidState);
			arrayResultsAllSat.add(1, "Vapor Quality 'x' = " + mQueryInputs.getXValue());
			arrayResultsBasic.add(1, "Vapor Quality 'x' = " + mQueryInputs.getXValue());
		} else {
			arrayResultsAllNotSat = mResults.getResultsAsArray(ConstantsHelper.ARRAY_RESULTS_ALLNOTSAT);
			arrayResultsAllNotSat.add(0, "Fluid State = " + fluidState);
		}
	}

	
	public void showMoreProperties(View view) {
		isMoreProps = true;
		if(fluidStateIndex == ConstantsHelper.FLUID_STATE_SATURATED) {
			listView.setAdapter(resultsAdapterSat);
		} else {
			listView.setAdapter(resultsAdapterNotSat);
		}
	}
	
	public void showLessProperties(View view) {
		isMoreProps = false;
		listView.setAdapter(resultsAdapterBasic);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class CustomAdapter extends BaseAdapter {
		 
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_MOREBUTTON = 1;
        private static final int TYPE_LESSBUTTON = 2;
        private static final int TYPE_MAX_COUNT = 3;
 
        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;
        
        private int mAdapterType;
 
        public CustomAdapter(ArrayList<String> data, int adapterType, int layoutId) {
        	mData = data;
        	addItem("" + layoutId);
        	mAdapterType = adapterType;
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
 
        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }
 
        @Override
        public int getItemViewType(int position) {
        	
        	if(position == getCount() - 1) {
        		
        		if(mAdapterType == ADAPTER_BASIC) {
					return TYPE_MOREBUTTON; 
        		} else {
					return TYPE_LESSBUTTON;
				}
        		
        	} 
        	else {
        		
        		return TYPE_ITEM;
        	}
        }
 
        @Override
        public int getViewTypeCount() {
            return TYPE_MAX_COUNT;
        }
 
        @Override
        public int getCount() {
            return mData.size();
        }
 
        @Override
        public String getItem(int position) {
            return (String) mData.get(position);
        }
 
        @Override
        public long getItemId(int position) {
            return position;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int type = getItemViewType(position);
            System.out.println("getView " + position + " " + convertView + " type = " + type);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (type) {
                    case TYPE_ITEM:
                        convertView = mInflater.inflate(R.layout.list_item_results, null);
                        holder.textView = (TextView)convertView.findViewById(R.id.list_item_results_textview);
                        break;
                    case TYPE_MOREBUTTON:
                        convertView = mInflater.inflate(R.layout.list_morebutton_results, null);
                        holder.button = (Button)convertView.findViewById(R.id.btn_showMoreProperties);
                        break;
                    case TYPE_LESSBUTTON:
                        convertView = mInflater.inflate(R.layout.list_lessbutton_results, null);
                        holder.button = (Button)convertView.findViewById(R.id.btn_showLessProperties);
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            
            if(position != getCount() - 1) {
            	holder.textView.setText((String) mData.get(position));
            }
            
            return convertView;
        }
 
    }
 
    public static class ViewHolder {
        public TextView textView;
        public Button button;
    }
}
