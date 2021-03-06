package com.deshario.agriculture.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.deshario.agriculture.Adapters.ReportslistAdapter;
import com.deshario.agriculture.Models.ChartsTypes;
import com.deshario.agriculture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Charts_Compensation extends Fragment {

    public static View customview;

    public Charts_Compensation() {
        // Required empty public constructor
    }

    String[] titles = {"ค่าตอบแทนรายวัน","ค่าตอบแทนรายเดือน","ค่าตอบแทนรายปี"};
    String[] desc = {
            "รายละเอียดเกี่ยวกับค่าตอบแทนเจ็ดวันที่ผ่านมา",
            "รายละเอียดเกี่ยวกับค่าตอบแทนตามรายเดือน",
            "รายละเอียดเกี่ยวกับค่าตอบแทนตามรายปี"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts_list, container, false);
        customview = view;
        RecyclerView recList = (RecyclerView)view.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ReportslistAdapter ca = new ReportslistAdapter(createList());
        recList.setAdapter(ca);
        return view;
    }

    public static void changecolor(int color){
        RelativeLayout relativeLayout = (RelativeLayout)customview.findViewById(R.id.chart_list);
        relativeLayout.setBackgroundColor(color);
    }

    private List<ChartsTypes> createList() {
        List<ChartsTypes> result = new ArrayList<ChartsTypes>();
        for (int i=0; i<titles.length; i++) {
            ChartsTypes chartsTypes = new ChartsTypes();
            chartsTypes.setId(i+1);
            chartsTypes.setTitle(titles[i]);
            chartsTypes.setDesc(desc[i]);
            result.add(chartsTypes);
        }
        return result;
    }

}
