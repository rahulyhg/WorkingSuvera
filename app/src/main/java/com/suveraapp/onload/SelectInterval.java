package com.suveraapp.onload;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.suveraapp.R;

/**
 * Created by Hibatop on 30/08/2016.
 */
public class SelectInterval extends Fragment {
    private SelectIntervalListener parentListener;
    private Button btnNext;
    private Spinner mySpinner;
    private int myInterval;

    public SelectInterval() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflate layout
        View view = inflater.inflate(R.layout.fragment_select_interval, container, false);

        //find spinner
        mySpinner = (Spinner) view.findViewById(R.id.interval);

        //populate spinner in drop down manner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.interval_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        //listen for selection
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                myInterval = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //find button
        btnNext = (Button) view.findViewById(R.id.btnConfirmInterval);
        //listen for button action
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //confirm that spinner choice is not empty
                if (myInterval == 0 || myInterval == 1) {
                    parentListener.intervalSelected(myInterval);
                } else{
                    Toast.makeText(getContext(),"Select an intake interval.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectIntervalListener) {
            parentListener = (SelectIntervalListener) context;
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
    public interface SelectIntervalListener {
        void intervalSelected(int interval);
    }

}
