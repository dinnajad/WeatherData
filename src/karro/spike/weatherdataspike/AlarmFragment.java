/**
 * 
 */
package karro.spike.weatherdataspike;

import karro.spike.weatherdata.R;
import karro.spike.weatherdataspike.model.Alarm;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * @author Karro
 *
 */
public class AlarmFragment extends Fragment implements OnItemSelectedListener {
	private Spinner mParameterSpinner;
	private Spinner mLogicSpinner;
	private EditText limit2;
	private EditText limit1;
	private Button okbutton;
	
	private String selectedParameter;
	private String selectedLogic;
	
	/***
	 * called from the OS when creating the fragment. 
	 * Initialise essentials here
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	/***
	 * creates the view, connect listeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v= inflater.inflate(R.layout.alarm_fragment, container,false);
		
		limit1= (EditText) v.findViewById(R.id.editText1);
		limit2= (EditText) v.findViewById(R.id.editText2);
		
		
		
		setUpSpinners(v);
		setUpButton(v);
	return v;
	}

	/**
	 * @param v
	 */
	private void setUpButton(View v) {
		okbutton = (Button) v.findViewById(R.id.OKbutton);
		okbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// skapa alarm, Spara undan alla v�rden,st�ng f�nstret
				Alarm alarm = new Alarm();
				alarm.setParameter(selectedParameter);
				alarm.setLogicOperator(selectedLogic);
				alarm.setLimit(limit1.getText().toString());
				alarm.setLimit2(limit2.getText().toString());		
				//TODO spara undan Alarmet i lista n�nstans
				getActivity().finish();
			}
		});
	}

	/**
	 * @param v
	 */
	private void setUpSpinners(View v) {
		//get a handle on the spinner
		mParameterSpinner = (Spinner) v.findViewById(R.id.parameterSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this.getActivity(), R.array.parameters, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mParameterSpinner.setAdapter(adapter);
		mParameterSpinner.setOnItemSelectedListener(this);
		
		//get a handle on the spinner
		mLogicSpinner = (Spinner) v.findViewById(R.id.logicOperatorSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this.getActivity(), R.array.logicalOperators, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mLogicSpinner.setAdapter(adapter2);
		mLogicSpinner.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// An item was selected. You can retrieve the selected item using
	
        String selected = (String) parent.getItemAtPosition(position);
        if(parent.equals(mParameterSpinner)){
        	selectedParameter = selected;        	
        }else{
        	selectedLogic = selected;
        }		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		 // Another interface callback
	}
}
