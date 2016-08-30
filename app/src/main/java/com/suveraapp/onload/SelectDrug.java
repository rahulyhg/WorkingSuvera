package com.suveraapp.onload;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.suveraapp.MainActivity;
import com.suveraapp.R;
import com.suveraapp.drug.Drug;
import com.suveraapp.drug.DrugType;

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
                //make sure they've typed something
                if(txtDrugName.getText().toString().length() > 1) {
                    //check the drug exists and send it back up to the parent activity
                    if (MainActivity.drugLoader.doesDrugExist(txtDrugName.getText().toString())) {
                        submitDrug(MainActivity.drugLoader.getDrug(txtDrugName.getText().toString()));
                    } else {
                        checkCustomDrugName();
                    }
                }else{
                    Toast.makeText(getContext(), "Please enter a name.", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    public void submitDrug(Drug d){
        parentListener.drugSelected(d);
    }

    public void checkCustomDrugName(){
        //create a listener for the yes/no
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Drug temp = new Drug(MainActivity.drugLoader.getDrugsList().size(), txtDrugName.getText().toString(), DrugType.OTHER, "null");
                        MainActivity.drugLoader.addDrug(temp);
                        submitDrug(temp);
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //build a alert message
        builder.setTitle("Custom Drug");
        builder.setMessage("This drug isn't in our database, do you still want to use it?\n\nUsage, storage and saftey information will be unavailable.").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });

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
