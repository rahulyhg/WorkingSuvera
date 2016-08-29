package com.suveraapp.onload;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.suveraapp.MainActivity;
import com.suveraapp.R;


public class AddReason extends Fragment {

    private AddReasonListener parentListener;
    private int DrugId = -1;
    private Button btnNext;
    private TextView lblTitle;
    public AddReason() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_reason, container, false);
        if(getArguments()!= null){
            DrugId = getArguments().getInt("DrugID");
        }
        lblTitle = (TextView) view.findViewById(R.id.lblAddReasonTitle);
        String title = "Why do you take " + MainActivity.drugLoader.getDrug(DrugId).getName()+ "?";
        lblTitle.setText(title);
        btnNext = (Button) view.findViewById(R.id.btnConfirmReason);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddReasonListener) {
            parentListener = (AddReasonListener) context;
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

    public interface AddReasonListener {
        void reasonGiven(String reason);
    }
}