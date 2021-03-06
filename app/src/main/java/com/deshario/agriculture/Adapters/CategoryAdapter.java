package com.deshario.agriculture.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.deshario.agriculture.Models.Category;
import com.deshario.agriculture.R;
import java.util.ArrayList;


public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> arrayList;
    private LayoutInflater inflater;
    private boolean isListView;
    public int selectedPosition = -1;
    public static int sel_position;
    public Category category;

    public CategoryAdapter(Context context, ArrayList<String> arrayList, boolean isListView) {
        this.context = context;
        this.arrayList = arrayList;
        this.isListView = isListView;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();

            //inflate the layout on basis of boolean
            if (isListView)
                view = inflater.inflate(R.layout.list_custom_row_layout, viewGroup, false);
            else
               // view = inflater.inflate(R.layout.grid_custom_row_layout, viewGroup, false);
                view = inflater.inflate(R.layout.list_custom_row_layout, viewGroup, false);

            viewHolder.label = (TextView) view.findViewById(R.id.label);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            view.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.label.setText(arrayList.get(i));

        //check the radio button if both position and selectedPosition matches
        viewHolder.radioButton.setChecked(i == selectedPosition);

        //Set the position tag to both radio button and label
        viewHolder.radioButton.setTag(i);
        viewHolder.label.setTag(i);

        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
            }
        });

        viewHolder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
            }
        });

        return view;
    }

    //On selecting any view set the current position to selectedPositon and notify adapter
    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
        sel_position = selectedPosition;
    }

    private class ViewHolder {
        private TextView label;
        private RadioButton radioButton;
    }

    // Return the selectedPosition item
    public String getSelectedItem() {
        if (selectedPosition != -1) {
           return arrayList.get(sel_position);
        }
        return null;
    }

    public int getCurrent(){
        if (selectedPosition != -1) {
            return sel_position;
        }else{
            Toast.makeText(context, "กรุณาเลือกรายการใดรายการหนึ่ง", Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    //Delete the selected position from the arrayList
    public void deleteSelectedPosition() {
        if (selectedPosition != -1) {
            arrayList.remove(selectedPosition);
            selectedPosition = -1;//after removing selectedPosition set it back to -1
            notifyDataSetChanged();
        }else{
            Toast.makeText(context, "กรุณาเลือกรายการใดรายการหนึ่ง", Toast.LENGTH_SHORT).show();
        }
    }
}
