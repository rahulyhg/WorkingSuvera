package com.suveraapp.onload;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.suveraapp.MainActivity;
import com.suveraapp.R;
import com.suveraapp.objects.Interval;
import com.suveraapp.objects.Reason;

public class AddReason extends Fragment {

    private AddReasonListener parentListener;
    private int DrugId = -1;
    private ImageButton btnNext;
    private TextView lblTitle;
    private EditText txtReason;
    private Reason myReason;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


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

        //splitting the drug name into an array of strings
        String [] name = MainActivity.drugLoader.getDrug(DrugId).getName().split(" ") ;
        title = "Why do you take " + name[0] + "?"; //taking the first string of each element in array
                                                           //as this represents the drug

        //find title and editable text
        lblTitle = (TextView) view.findViewById(R.id.lblAddReasonTitle);
        txtReason = (EditText) view.findViewById(R.id.txtDrugReason);

        //set text in frag screen
        lblTitle.setText(title);

        myReason = new Reason("");

        //find button
        btnNext = (ImageButton) view.findViewById(R.id.btnConfirmReason);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate that the reason is longer than 2 letters
                if(txtReason.getText().toString().length() > 2){
                    //set Reason object to the reason entered
                    myReason.setReason(txtReason.getText().toString());
                    //pass through to parent fragactivity
                    parentListener.reasonGiven(myReason);
                }else{
                    Toast.makeText(getContext(), "Reason must be longer than 2 letters.", Toast.LENGTH_LONG).show();
                }
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
        void reasonGiven(Reason reason);
    }
}
