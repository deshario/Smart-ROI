package com.deshario.agriculture.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.deshario.agriculture.Config.Deshario_Functions;
import com.deshario.agriculture.Models.CustomRecords;
import com.deshario.agriculture.Models.Records;
import com.deshario.agriculture.Config.ProgressBarAnimation;
import com.deshario.agriculture.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Record_Latest extends Fragment {

    private Context context;
    private ListView list_view;
    private String all_items[] = {"รายได้","ค่าใช้จ่าย","กำไร","หนี้สิน/เงินกู้","ชำระหนี้สิน/เงินกู้","คงเหลือ","บันทึกย่อ","วันที่"};
    private ArrayList<String> values = new ArrayList<>();
    private SimpleAdapter phone_adapter;
    private List<HashMap<String, String>> aList;
    String thb = "\u0E3F";

    DateFormat today = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("TH"));
    public static double M_income = 0.0; // รายรับ
    public static double M_expenses = 0.0; // รายจ่าย
    public static double M_profit = 0.0; // กำไร
    public static double M_debt = 0.0; // หนี้สิน
    public static double M_remain = 0.0; // คงเหลือ
    public static boolean getTodayDate = false; // คงเหลือ

    private static List<Records> records;

    public Record_Latest() {
        setRetainInstance(true);
    }

    TextView date1,date2,note_txt;
    TextView income, expense, profit, debt, pay_debt, total_remain, empty_record;
    TextView income_val, expense_val, profit_val, debt_val, pay_debt_val, total_remain_val;
    public static ImageButton btn_refresh;
    View dividerTitle;
    RoundCornerProgressBar prog_income,prog_expense,prog_profit,prog_debt,prog_paydebt,prog_remain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cardview_manual, container, false);
        context = container.getContext();
        bindview(view);
        work();
        return view;
    }

    public static CustomRecords calculation(List<Records> record){
        CustomRecords customRecords = new CustomRecords();
        if(record.size() <= 1){
            customRecords = One_Records(record.get(0));
        }else{
            customRecords = Multiple_Records(record);
        }
        return customRecords;
    }

    private static CustomRecords One_Records(Records records){
        int type = records.getCategory().getCat_type();
        switch (type){
            case 1:
                M_income = 0.0; // ok // ติดลบ
                M_expenses = 0.0; // ok
                M_profit = M_income-M_expenses; // ok
                M_debt = records.getData_amount(); //
                break;
            case 2:
                //  amounts = Records.getCustom("data_amount",2);
                M_income = 0.0; // ok
                M_expenses = records.getData_amount(); // ok
                M_profit = M_income-M_expenses; // ok
                M_debt = 0.0; // ok
                break;
            case 3:
                // amounts = Records.getCustom("data_amount",3);
                M_income = records.getData_amount(); // ok
                M_expenses = 0.0; // ok
                M_profit = M_income-M_expenses; // ok
                M_debt = 0.0; //
                break;
            default:
        }
        String note = records.getShortnote();
        String date = records.getData_created();

        CustomRecords cr = new CustomRecords();
        cr.setIncome(String.valueOf(M_income));
        cr.setExpense(String.valueOf(M_expenses));
        cr.setProfit(String.valueOf(M_profit));
        cr.setDebt(String.valueOf(M_debt));
        cr.setNote(note);
        cr.setDate(date);
        return cr;
    }

    private static CustomRecords Multiple_Records(List<Records> records){
        CustomRecords cr = new CustomRecords();
        double totalIncome  = 0.0;
        double totalExpense  = 0.0;
        double totalProfit  = 0.0;
        double totalDebt  = 0.0;

        for(int i=0; i<records.size(); i++){
            int type = records.get(i).getCategory().getCat_type();
            switch (type){
                case 1:
                    M_income = 0.0; // ok // ติดลบ
                    M_expenses = 0.0; // ok
                    M_debt = records.get(i).getData_amount(); //
                    //System.out.println("\nIncome : "+M_income+"\n Expense : "+M_expenses+"\n Debt : "+M_debt);
                    break;
                case 2:
                    //  amounts = Records.getCustom("data_amount",2);
                    M_income = 0.0; // ok
                    M_expenses = records.get(i).getData_amount(); // ok
                    M_debt = 0.0; // ok
                    //System.out.println("\nIncome : "+M_income+"\n Expense : "+M_expenses+"\n Debt : "+M_debt);
                    break;
                case 3:
                    // amounts = Records.getCustom("data_amount",3);
                    M_income = records.get(i).getData_amount(); // ok
                    M_expenses = 0.0; // ok
                    M_debt = 0.0; //
                    //System.out.println("\nIncome : "+M_income+"\n Expense : "+M_expenses+"\n Debt : "+M_debt);
                    break;
                default:
            }
            String note = records.get(i).getShortnote();
            String date = records.get(i).getData_created();

            if(M_income != 0.0){
                totalIncome = totalIncome+M_income;
            }

            if(M_expenses != 0.0){
                totalExpense = totalExpense+M_expenses;
            }

            if(M_debt != 0.0){
                totalDebt = totalDebt+M_debt;
            }

            cr.setNote(note);
            cr.setDate(date);
        }

        totalProfit = totalIncome-totalExpense; // ok

//        System.out.println("totalIncome total == "+totalIncome);
//        System.out.println("totalExpense total == "+totalExpense);
//        System.out.println("totalDebt total == "+totalDebt);

        double finalAmount = totalIncome-totalExpense;
        finalAmount = finalAmount-totalDebt;

//        System.out.println("finalAmount total == "+finalAmount);

        cr.setIncome(String.valueOf(totalIncome));
        cr.setExpense(String.valueOf(totalExpense));
        cr.setDebt(String.valueOf(totalDebt));
        cr.setProfit(String.valueOf(totalProfit));
        cr.setTotal(String.valueOf(finalAmount));

        return cr;
    }

    public void bindview(View view){
        dividerTitle = (View)view.findViewById(R.id.titleDivider);
        empty_record = (TextView)view.findViewById(R.id.nodata);

        date1 = (TextView)view.findViewById(R.id.date1);
        date2 = (TextView)view.findViewById(R.id.date2);
        note_txt = (TextView)view.findViewById(R.id.note);
        income = (TextView) view.findViewById(R.id.item1);
        expense = (TextView) view.findViewById(R.id.item2);
        profit = (TextView) view.findViewById(R.id.item3);
        debt = (TextView) view.findViewById(R.id.item4);
        //pay_debt = (TextView) view.findViewById(R.id.item5);
        total_remain = (TextView) view.findViewById(R.id.item6);

        income_val = (TextView) view.findViewById(R.id.amount1);
        expense_val = (TextView) view.findViewById(R.id.amount2);
        profit_val = (TextView) view.findViewById(R.id.amount3);
        debt_val = (TextView) view.findViewById(R.id.amount4);
        //pay_debt_val = (TextView) view.findViewById(R.id.amount5);
        total_remain_val = (TextView) view.findViewById(R.id.amount6);

        TextView[] textViews = {income,expense,profit,total_remain,debt,debt_val,income_val,expense_val,profit_val,total_remain_val};
        Deshario_Functions.fillColor(getActivity(),textViews);

        prog_income = (RoundCornerProgressBar)view.findViewById(R.id.progress1);
        prog_expense = (RoundCornerProgressBar)view.findViewById(R.id.progress2);
        prog_profit = (RoundCornerProgressBar)view.findViewById(R.id.progress3);
        prog_debt = (RoundCornerProgressBar)view.findViewById(R.id.progress4);
        //prog_paydebt = (RoundCornerProgressBar)view.findViewById(R.id.progress5);
        prog_remain = (RoundCornerProgressBar)view.findViewById(R.id.progress6);

        btn_refresh = (ImageButton) view.findViewById(R.id.refresh_btn);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                work();
            }
        });
    }

    public void work(){
        income.setText(all_items[0]);
        expense.setText(all_items[1]);
        profit.setText(all_items[2]);
        debt.setText(all_items[3]);
        total_remain.setText(all_items[5]);

        boolean records_exists  = Records.records_exists();
        if(!records_exists){ // No record
            Nofound();
        }else{
            String latestRecordDate = Records.getLatestRecordByDate().getData_created();
            List<Records> temp = Records.getLatestRecordBySameDate(latestRecordDate);
            CustomRecords datas = calculation(temp);

            String date_ = datas.getDate();
            String dates[] = getThaiDate(date_);
            String day = dates[0];
            String month = dates[1];
            String year = dates[2];
            double inc = (datas.getIncome() == null) ? 0.00 : Double.valueOf(datas.getIncome());
            double exp = (datas.getExpense() == null) ? 0.00 : Double.valueOf(datas.getExpense());
            double prof = (datas.getProfit() == null) ? 0.00 : Double.valueOf(datas.getProfit());
            double deb = (datas.getDebt() == null) ? 0.00 : Double.valueOf(datas.getDebt());
            double total = (datas.getTotal() == null) ? 0.00 : Double.valueOf(datas.getTotal());

            setData(income_val,prog_income,inc);
            setData(expense_val,prog_expense,exp);
            setData(profit_val,prog_profit,Double.valueOf(Deshario_Functions.filterNum(Deshario_Functions.getDecimal2Format(prof))));
            setData(total_remain_val,prog_remain,total);

            TextView[] debt_text = {debt,debt_val};
            if(deb > 0){
                fillColor(debt_text,getResources().getColor(R.color.deep_orange));
                setData(debt_val,prog_debt,deb,getResources().getColor(R.color.deep_orange));
            }else{
                fillColor(debt_text,getResources().getColor(R.color.material_primary));
                setData(debt_val,prog_debt,deb,getResources().getColor(R.color.material_primary));
            }

            if(inc > exp){
                profit.setText("กำไร");
                profit.setTextColor(getResources().getColor(R.color.material_primary));
                profit_val.setTextColor(getResources().getColor(R.color.material_primary));
                prog_profit.setProgress(100);
            }else if(income == expense){
                profit.setText("กำไร/ขาดทุน");
                profit.setTextColor(getResources().getColor(R.color.material_primary));
                profit_val.setTextColor(getResources().getColor(R.color.material_primary));
            }else{
                profit.setText("ขาดทุน");
                prog_profit.setProgressColor(getResources().getColor(R.color.material_danger));
                profit.setTextColor(getResources().getColor(R.color.material_danger));
                profit_val.setTextColor(getResources().getColor(R.color.material_danger));
            }

            if(total >= 0){
                prog_remain.setProgressColor(getResources().getColor(R.color.material_primary));
                total_remain.setTextColor(getResources().getColor(R.color.material_primary));
                total_remain_val.setTextColor(getResources().getColor(R.color.material_primary));
            }else{
                //prog_remain.setProgressColor(getResources().getColor(R.color.material_danger));
                //total_remain.setTextColor(getResources().getColor(R.color.material_danger));
                //total_remain_val.setTextColor(getResources().getColor(R.color.material_danger));
                total_remain_val.setText(thb+"0.00");
            }

            System.out.println("inc total : "+inc);
            System.out.println("exp total : "+exp);
            System.out.println("prof total : "+prof);
            System.out.println("deb total : "+deb);

            note_txt.setText("บันทึกย่อ : "+datas.getNote());
            date1.setText(day);
            date2.setText(month+" "+year);

            date1.setVisibility(View.VISIBLE);
            date2.setVisibility(View.VISIBLE);
            note_txt.setVisibility(View.VISIBLE);
            dividerTitle.setVisibility(View.VISIBLE);
            empty_record.setVisibility(View.GONE);
        }
    }

    private void Nofound(){
        date1.setVisibility(View.GONE);
        date2.setVisibility(View.GONE);
        note_txt.setVisibility(View.GONE);
        dividerTitle.setVisibility(View.GONE);
        empty_record.setVisibility(View.VISIBLE);

        income_val.setText(thb+"0.00");
        expense_val.setText(thb+"0.00");
        profit_val.setText(thb+"0.00");
        debt_val.setText(thb+"0.00");
        total_remain_val.setText(thb+"0.00");
        prog_income.setProgress(0);
        prog_expense.setProgress(0);
        prog_profit.setProgress(0);
        prog_debt.setProgress(0);
        prog_remain.setProgress(0);
    }

    private void fillColor(TextView[] textViews, int Color){
        for(int i=0; i<textViews.length; i++){
            textViews[i].setTextColor(Color);
        }
    }

    private void setData(TextView textView, RoundCornerProgressBar bar, double data){
        textView.setText(thb+Deshario_Functions.getDecimal2Format(Double.valueOf(data)));
        bar.setProgressColor(getResources().getColor(R.color.material_primary));
        if(data <= 0){
            bar.setProgress(0);
        }else{
            ProgressBarAnimation anim = new ProgressBarAnimation(bar, 0, 100);
            anim.setDuration(1000);
            bar.startAnimation(anim);
        }
    }

    private void setData(TextView textView, RoundCornerProgressBar bar, double data, int color){
        textView.setText(thb+Deshario_Functions.getDecimal2Format(Double.valueOf(data)));
        bar.setProgressColor(color);
        if(data <= 0){
            bar.setProgress(0);
        }else{
            ProgressBarAnimation anim = new ProgressBarAnimation(bar, 0, 100);
            anim.setDuration(1000);
            bar.startAnimation(anim);
        }
    }

    private String getTodayStringDate(){
        Date today = Calendar.getInstance().getTime();
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String stringDate = sdf.format(today);
        return stringDate;
    }

    private String[] getDefault_date(){
        String full_thaidate = null;
        Date today = Calendar.getInstance().getTime();
        String DATE_FORMAT_NOW = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String stringDate = sdf.format(today);
        String[] output = stringDate.split("-");

        int _day = Integer.valueOf(output[0]);
        int _month = Integer.valueOf(output[1]);
        int _year = Integer.valueOf(output[2]);

        int day_ = _day;
        String month_ = Th_Months(_month-1,true);
        int year_ = Th_Year(_year);
        full_thaidate = day_+" "+month_+" "+year_;

        return new String[]{
                String.valueOf(day_),
                month_,
                String.valueOf(year_),
                full_thaidate
        };
    }

    public String[] getThaiDate(String date){
        String thai_date = date;
        String[] output = thai_date.split("-");
        int _day = Integer.valueOf(output[2]);
        int _month = Integer.valueOf(output[1]);
        int _year = Integer.valueOf(output[0]);
        String month_ = Th_Months(_month-1,true);
        int year_ = Th_Year(_year);
        thai_date = _day+" "+month_+" "+year_;
        return new String[]{
                String.valueOf(Deshario_Functions.add_zero_or_not(_day)),
                month_,
                String.valueOf(year_),
                thai_date
        };
    }

    public String Th_Months(int month, boolean full){
        String[] th_months_short = new String[] {
                "ม.ค.","ก.พ.","มี.ค.","เม.ย.","พ.ค.","มิ.ย.","ก.ค.","ส.ค.","ก.ย.","ต.ค.","พ.ย.","ธ.ค."
        };
        String[] th_months_long = new String[]{
                "มกราคม",
                "กุมภาพันธ์",
                "มีนาคม",
                "เมษายน",
                "พฤษภาคม",
                "มิถุนายน",
                "กรกฎาคม",
                "สิงหาคม",
                "กันยายน",
                "ตุลาคม",
                "พฤศจิกายน",
                "ธันวาคม"
        };
        if(full == true){
            return th_months_long[month];
        }else{
            return th_months_short[month];
        }
    }

    public int Th_Year(int en_year){
        return en_year+543;
    }

}






