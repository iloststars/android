package com.ilostsarts.reportforms;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
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
public class HomeFragment extends Fragment {
    private static final String TAG = "LOG";
    SharedPreferences shp;
    float delta_flow1 = 0;
    float delta_flow2 = 0;
    float sum_flow = 0;
    float watt4 = 0;
    float watt2 = 0;
    float power = 0;
    float last_flow1 = 0;
    float last_flow2 = 0;
    float last_index4 = 0;
    float last_index2 = 0;
    MyData myData;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    EditText editText9;
    EditText editText10;
    EditText editText11;
    EditText editText12;
    EditText editText13;
    Button btn_cal;
    Button btn_clear;
    Button btn_record;
    Button btn_handover;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.d(TAG, "onActivityCreated: 创建");
        editText1 = getView().findViewById(R.id.editText1);
        editText2 = getView().findViewById(R.id.editText2);
        editText3 = getView().findViewById(R.id.editText3);
        editText4 = getView().findViewById(R.id.editText4);
        editText5 = getView().findViewById(R.id.editText5);
        editText6 = getView().findViewById(R.id.editText6);
        editText7 = getView().findViewById(R.id.editText7);
        editText8 = getView().findViewById(R.id.editText8);
        editText9 = getView().findViewById(R.id.editText9);
        editText10 = getView().findViewById(R.id.editText10);
        editText11 = getView().findViewById(R.id.editText11);
        editText12 = getView().findViewById(R.id.editText12);
        editText13 = getView().findViewById(R.id.editText13);
        btn_cal = getView().findViewById(R.id.button);
        btn_clear = getView().findViewById(R.id.button2);
        btn_record = getView().findViewById(R.id.button3);
        btn_handover = getView().findViewById(R.id.button7);
        editText7.setText(String.format(Locale.getDefault(),"%.0f",last_flow1));
        editText8.setText(String.format(Locale.getDefault(),"%.0f",last_flow2));
        editText9.setText(String.format(Locale.getDefault(),"%.0f",last_index4));
        editText10.setText(String.format(Locale.getDefault(),"%.2f",last_index2));
        load();
        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    calculate();
                }catch (Exception e) {

                }
                Bundle bundle = new Bundle();
                bundle.putFloat("flow1",myData.flow1);
                bundle.putFloat("delta_flow1",delta_flow1);
                bundle.putFloat("flow2",myData.flow2);
                bundle.putFloat("delta_flow2",delta_flow2);
                bundle.putFloat("sum_flow",sum_flow);
                bundle.putFloat("output_pressure",myData.out_pressure);
                bundle.putFloat("level",myData.level);
                bundle.putFloat("index2",myData.index2); //2#
                bundle.putFloat("watt2",watt2);
                bundle.putFloat("index4",myData.index4); //4#
                bundle.putFloat("watt4",watt4);
                bundle.putFloat("power",power);
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_homeFragment_to_resultFragment,bundle);
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                editText5.setText("");
                editText6.setText("");
                editText7.setText("");
                editText8.setText("");
                editText9.setText("");
                editText10.setText("");
                editText11.setText("");
                editText12.setText("");
                editText12.setText("");
                editText13.setText("");
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_homeFragment_to_secondFragment);
            }
        });

        btn_handover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_homeFragment_to_handoutFragment);
            }
        });
    }

    private void calculate(){
        myData = new MyData();
        myData.flow1 = parse(editText1);
        myData.flow2 = parse(editText2);
        myData.index4 = parse(editText3);
        myData.index2 = parse(editText4);
        myData.frequency = parse(editText5);
        myData.elec = parse(editText6);
        myData.last_flow1 = parse(editText7);
        myData.last_flow2 = parse(editText8);
        myData.last_index4 = parse(editText9);
        myData.last_index2 = parse(editText10);
        myData.level = parse(editText11);
        myData.pressure = parse(editText12);
        myData.out_pressure = parse(editText13);

        last_flow1 = myData.flow1;
        last_flow2 = myData.flow2;
        last_index4 = myData.index4;
        last_index2 = myData.index2;
        save();

        delta_flow1 = myData.flow1 - myData.last_flow1;
        delta_flow2 = myData.flow2 - myData.last_flow2;
        sum_flow = delta_flow1 + delta_flow2;
        watt4 = (myData.index4 - myData.last_index4)*getActivity().getApplication().getResources().getInteger(R.integer.varyFreq_factor);
        watt2 = (myData.index2 - myData.last_index2)*getActivity().getApplication().getResources().getInteger(R.integer.fixFreq_factor);
        power = myData.pressure * sum_flow / getActivity().getApplication().getResources().getInteger(R.integer.power_factor);
    }

    private float parse(EditText editText){
        float result;
        try{
            result = Float.parseFloat(editText.getText().toString());
        }
        catch (Exception e){
            result = 0;
        }
        return result;
    }

    private void save(){
        shp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        editor.putFloat("last_flow1",last_flow1);
        editor.putFloat("last_flow2",last_flow2);
        editor.putFloat("last_index1",last_index4);
        editor.putFloat("last_index2",last_index2);
        editor.apply();
    }

    private void load(){
        shp = getActivity().getPreferences(Context.MODE_PRIVATE);
        last_flow1 = shp.getFloat("last_flow1",0);
        last_flow2 = shp.getFloat("last_flow2",0);
        last_index4 = shp.getFloat("last_index1",0);
        last_index2 = shp.getFloat("last_index2",0);
        editText7.setText(String.format(Locale.getDefault(),"%.0f",last_flow1));
        editText8.setText(String.format(Locale.getDefault(),"%.0f",last_flow2));
        editText9.setText(String.format(Locale.getDefault(),"%.0f",last_index4));
        editText10.setText(String.format(Locale.getDefault(),"%.2f",last_index2));
    }

    private void show(){

    }

}
