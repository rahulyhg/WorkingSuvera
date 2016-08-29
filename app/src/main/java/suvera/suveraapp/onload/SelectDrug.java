package suvera.suveraapp.onload;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import suvera.suveraapp.MainActivity;
import suvera.suveraapp.R;
import suvera.suveraapp.drug.Drug;

public class SelectDrug extends Fragment{
    private AutoCompleteTextView txtDrugName;
    private View parentView;
    private Button btnNext;
    private SelectDrugListener parentListener;


    public interface SelectDrugListener{
        public void drugSelected(Drug selection);
    }

    public SelectDrug() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_select_drug, container, false);
        //save the view for later use
        parentView = view;
        //create an adapter and fill it with the loaded drug names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, MainActivity.drugLoader.getNameArray());
        txtDrugName = (AutoCompleteTextView) view.findViewById(R.id.txtDrugName);
        txtDrugName.setThreshold(1); // will start search after 1 char
        txtDrugName.setAdapter(adapter); // setup the adapter
        btnNext = (Button) view.findViewById(R.id.btnConfirmDrugName); // get the button
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //check the drug exists and send it back up to the parent activity
                if(MainActivity.drugLoader.doesDrugExist(txtDrugName.getText().toString())){
                    parentListener.drugSelected(MainActivity.drugLoader.getDrug(txtDrugName.toString()));
                }else{
                    Toast.makeText(getActivity(), "Invalid drug name.", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectDrugListener) {
            //get the SelectDrugListener on the parent activity
            parentListener = (SelectDrugListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }
}
