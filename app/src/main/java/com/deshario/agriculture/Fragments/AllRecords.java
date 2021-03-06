package com.deshario.agriculture.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.deshario.agriculture.Adapters.RecordAdapter;
import com.deshario.agriculture.Models.Records;
import com.deshario.agriculture.R;
import com.franmontiel.fullscreendialog.FullScreenDialogContent;
import com.franmontiel.fullscreendialog.FullScreenDialogController;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllRecords extends Fragment implements FullScreenDialogContent {

    GridView gridview;
    List<Records> allItems;
    RecordAdapter customAdapter;
    Context context;

    public AllRecords() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_cardview, container, false);
        context = container.getContext();
        gridview = (GridView)view.findViewById(R.id.gridview);
         allItems = Records.getAllRecords();

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat monthonly = new SimpleDateFormat("MMM");
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR,2017);
//        cal.set(Calendar.MONTH,Calendar.AUGUST);
//        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(monthonly.format(cal.getTime())+" :: "+maxDay+" Days");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//        ArrayList<String> dates = new ArrayList<>();
//        for (int i=0; i<maxDay; i++){
//            cal.set(Calendar.DAY_OF_MONTH,i+1);
//            String date = df.format(cal.getTime());
//            dates.add(i,date);
//        }
//
//
//        List<Records> abc = Records.getDataBy_date_n_Type("2017-08",1);
//        ArrayList<String> found_dates = new ArrayList<>();
//        for(int i=0; i<abc.size(); i++){
//            String my_date = abc.get(i).getData_created();
//            found_dates.add(i,my_date);
//        }
//
//        double month_datas[] = new double[dates.size()];
//        for(int i=0; i<dates.size(); i++){
//            boolean status = check_exists(found_dates,dates.get(i));
//            if(status == true){
//                Records reca = Records.getSingleRecordsByDate(dates.get(i));
//                //System.out.println(dates.get(i)+" : "+status);
//                //System.out.println("Amount :: "+reca.getData_amount());
//                month_datas[i] = reca.getData_amount();
//            }else{
//                month_datas[i] = 0.0;
//                //System.out.println(dates.get(i)+" : "+status);
//            }
//        }
//
//        System.out.println(Arrays.toString(month_datas));

        customAdapter = new RecordAdapter(context,allItems);
        gridview.setAdapter(customAdapter);
        return view;
    }

    public boolean check_exists(ArrayList datalist,String keyword){
        int index = datalist.indexOf(keyword);
        return (index == -1?false:true);
    }

    @Override
    public void onDialogCreated(FullScreenDialogController dialogController) {

    }

    @Override
    public boolean onConfirmClick(FullScreenDialogController dialogController) {
        return true;
    }

    @Override
    public boolean onDiscardClick(FullScreenDialogController dialogController) {
        return false;
    }
}
