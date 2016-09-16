package com.suveraapp.onload;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.suveraapp.R;
import com.suveraapp.objects.Interval;

public class SelectInterval extends Fragment {
    private SelectIntervalListener parentListener;
    private int myInterval;
    private Interval interval = new Interval(false);

    public SelectInterval() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflate layout
        View view = inflater.inflate(R.layout.fragment_select_interval, container, false);

        //find spinner
        Spinner mySpinner = (Spinner) view.findViewById(R.id.interval);

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
        ImageButton btnNext = (ImageButton) view.findViewById(R.id.btnConfirmInterval);
        //listen for button action
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sets the interval object to true, if "Specific days" is selected and
                //false if "Everyday" is selected
                if(myInterval == 1){
                    interval.setInterval(true);
                } else {
                    interval.setInterval(false);
                }

                //pass through users choice as an interval object
                //false - everyday [default], true - specific days)
                parentListener.intervalSelected(interval);
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
        void intervalSelected(Interval interval);
    }

}
