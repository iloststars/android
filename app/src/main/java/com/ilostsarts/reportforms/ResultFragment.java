package com.ilostsarts.reportforms;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    TextView  tv_flow1;
    TextView  tv_delta_flow1;
    TextView  tv_flow2;
    TextView  tv_delta_flow2;
    TextView  tv_sum_flow;
    TextView  tv_out_pressure;
    TextView  tv_level;
    TextView  tv_index2;
    TextView  tv_watt2;
    TextView  tv_index4;
    TextView  tv_watt4;
    TextView  tv_power;


    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        float flow1 = parseFloat("flow1",0);
        float delta_flow1 = parseFloat("delta_flow1",0);
        float flow2 = parseFloat("flow2",0);
        float delta_flow2 = parseFloat("delta_flow2",0);
        float sum_flow = parseFloat("sum_flow",0);
        float out_pressure = parseFloat("output_pressure",0);
        float level = parseFloat("level",0);
        float index2 = parseFloat("index2",0);
        float watt2 = parseFloat("watt2",0);
        float index4 = parseFloat("index4",0);
        float watt4 = parseFloat("watt4",0);
        float power = parseFloat("power",0);

        tv_flow1 = getView().findViewById(R.id.textView42);
        tv_delta_flow1 = getView().findViewById(R.id.textView43);
        tv_flow2 = getView().findViewById(R.id.textView44);
        tv_delta_flow2 = getView().findViewById(R.id.textView45);
        tv_sum_flow = getView().findViewById(R.id.textView46);
        tv_out_pressure = getView().findViewById(R.id.textView47);
        tv_level = getView().findViewById(R.id.textView48);
        tv_index2 = getView().findViewById(R.id.textView49);
        tv_watt2 = getView().findViewById(R.id.textView50);
        tv_index4 = getView().findViewById(R.id.textView51);
        tv_watt4 = getView().findViewById(R.id.textView52);
        tv_power = getView().findViewById(R.id.textView53);

        tv_flow1.setText(String.format(Locale.getDefault(),"%.0f",flow1));
        tv_delta_flow1.setText(String.format(Locale.getDefault(),"%.0f",delta_flow1));
        tv_flow2.setText(String.format(Locale.getDefault(),"%.0f",flow2));
        tv_delta_flow2.setText(String.format(Locale.getDefault(),"%.0f",delta_flow2));
        tv_sum_flow.setText(String.format(Locale.getDefault(),"%.0f",sum_flow));
        tv_out_pressure.setText(String.format(Locale.getDefault(),"%.2f",out_pressure));
        tv_level.setText(String.format(Locale.getDefault(),"%.2f",level));
        tv_index2.setText(String.format(Locale.getDefault(),"%.2f",index2));
        tv_watt2.setText(String.format(Locale.getDefault(),"%.0f",watt2));
        tv_index4.setText(String.format(Locale.getDefault(),"%.0f",index4));
        tv_watt4.setText(String.format(Locale.getDefault(),"%.0f",watt4));
        tv_power.setText(String.format(Locale.getDefault(),"%.2f",power));

        Button btn_back = getView().findViewById(R.id.button5);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_resultFragment_to_homeFragment);
            }
        });
    }

    private float parseFloat(String key,float defaultValue){
        float result;
        try {
            result = getArguments().getFloat(key,defaultValue);
        }catch (Exception e){
            result = 0;
        }
        return result;
    }


}
