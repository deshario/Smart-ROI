package com.deshario.agriculture.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deshario.agriculture.Fragments.Charts_Root;
import com.deshario.agriculture.Fragments.Compensation_Per_Date;
import com.deshario.agriculture.Fragments.Compensation_Per_Month;
import com.deshario.agriculture.Fragments.Compensation_Per_Year;
import com.deshario.agriculture.Fragments.Expense_Category_Frag;
import com.deshario.agriculture.Fragments.Expense_Per_Date_Frag;
import com.deshario.agriculture.Fragments.Expense_Per_Month_Frag;
import com.deshario.agriculture.Fragments.Expense_Per_Year_Frag;
import com.deshario.agriculture.Fragments.Income_Category_Frag;
import com.deshario.agriculture.Fragments.Income_Per_Date_Frag;
import com.deshario.agriculture.Fragments.Income_Per_Month_Frag;
import com.deshario.agriculture.Fragments.Income_Per_Year_Frag;
import com.deshario.agriculture.Models.ChartsTypes;
import com.deshario.agriculture.R;

import java.util.List;


public class ReportslistAdapter extends RecyclerView.Adapter<ReportslistAdapter.ContactViewHolder> {

    private List<ChartsTypes> contactList;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Fragment frag;
    Context mContext;
    Toolbar toolbar;
    ImageView imgToolIcon;

    public ReportslistAdapter(List<ChartsTypes> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount(){
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ChartsTypes ci = contactList.get(i);
        contactViewHolder.title.setText(ci.getTitle());
        contactViewHolder.description.setText(ci.getDesc());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.charts_list_layout, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView description;

        public ContactViewHolder(View view) {
            super(view);
            title =  (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.desc);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ChartsTypes chartsTypes = contactList.get(getPosition());
            int id = chartsTypes.getId();
            String title = chartsTypes.getTitle();
            int page_no = Charts_Root.getPage();
            //Toast.makeText(v.getContext(),"Page :: "+ page_no+"\nID :: "+id+"\nTitle :: "+title,Toast.LENGTH_SHORT).show();
            //System.out.println("pageno :: "+page_no);
            Bundle bundle = new Bundle();
            switch (page_no){
                case 0:
                    if(id == 1)
                        frag = new Income_Per_Date_Frag();
                    if(id == 2)
                        frag = new Income_Per_Month_Frag();
                    if(id == 3)
                        frag = new Income_Per_Year_Frag();
                    if(id == 4)
                        frag = new Income_Category_Frag();
                    bundle.putString("title1", title);
                    break;
                case 1:
                    if(id == 1)
                        frag = new Expense_Per_Date_Frag();
                    if(id == 2)
                        frag = new Expense_Per_Month_Frag();
                    if(id == 3)
                        frag = new Expense_Per_Year_Frag();
                    if(id == 4)
                        frag = new Expense_Category_Frag();
                    bundle.putString("title2", title);
                    break;
                case 2:
                    if(id == 1)
                        frag = new Compensation_Per_Date();
                    if(id == 2)
                        frag = new Compensation_Per_Month();
                    if(id == 3)
                        frag = new Compensation_Per_Year();
                    bundle.putString("title3", title);
                    break;
                default:
            }
            change_frag(frag,bundle);

        }

        public void change_frag(Fragment fragment,Bundle bundle){
            mFragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            fragment.setArguments(bundle);
            mFragmentTransaction.replace(R.id.containerView,fragment);
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            mFragmentTransaction.addToBackStack(null);
            mFragmentTransaction.commit();

            toolbar = (Toolbar)((FragmentActivity)mContext).findViewById(R.id.my_toolbar);
            imgToolIcon = (ImageView) toolbar.findViewById(R.id.toolbar_icon);
            imgToolIcon.setImageResource(R.drawable.ic_arrow_back_white_24dp);
            imgToolIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked hai apss");
                    ((FragmentActivity)mContext).onBackPressed();
                }
            });
        }


    }
}