package com.deshario.agriculture.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deshario.agriculture.Adapters.ReportslistAdapter;
import com.deshario.agriculture.Models.ContactInfo;
import com.deshario.agriculture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Charts_Expense_Lists_Frag extends Fragment {


    public Charts_Expense_Lists_Frag() {
        // Required empty public constructor
    }

    String[] titles = {"ค่าใช้จ่ายรายวัน","ค่าใช้จ่ายรายเดือน","ค่าใช้จ่ายรายปี"};
    String[] desc = {
            "รายละเอียดเกี่ยวกับค่าใช้จ่ายเจ็ดวันที่ผ่านมา",
            "รายละเอียดเกี่ยวกับค่าใช้จ่ายตามรายเดือน",
            "รายละเอียดเกี่ยวกับค่าใช้จ่ายตามรายปี"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts_list, container, false);
        RecyclerView recList = (RecyclerView)view.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ReportslistAdapter ca = new ReportslistAdapter(createList());
        recList.setAdapter(ca);
        return view;
    }

    private List<ContactInfo> createList() {
        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=0; i<titles.length; i++) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setTitle(titles[i]);
            contactInfo.setDesc(desc[i]);
            result.add(contactInfo);
        }
        return result;
    }

}