package com.deshario.agriculture.Fragments;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deshario.agriculture.Models.Records;
import com.deshario.agriculture.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseChart extends Fragment {

    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};

    public final static String[] days = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun",};

    private LineChartView chartTop;
    private ColumnChartView chartBottom;

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;

    private LineChartData lineData;
    private ColumnChartData columnData;


    public ExpenseChart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_line_column, container, false);

        // *** TOP LINE CHART ***
        chartTop = (LineChartView) rootView.findViewById(R.id.chart_top);

        // Generate and set data for line chart
        generateInitialLineData();

        // *** BOTTOM COLUMN CHART ***

        chartBottom = (ColumnChartView) rootView.findViewById(R.id.chart_bottom);

        generateColumnData();

        return rootView;
    }

    private void generateColumnData() {

        int numSubcolumns = 1;
        int numColumns = months.length;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            axisValues.add(new AxisValue(i).setLabel(months[i]));

            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);

        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(2));

        chartBottom.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        chartBottom.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        chartBottom.setValueSelectionEnabled(true);

        chartBottom.setZoomType(ZoomType.HORIZONTAL);

        // chartBottom.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // SelectedValue sv = chartBottom.getSelectedValue();
        // if (!sv.isSet()) {
        // generateInitialLineData();
        // }
        //
        // }
        // });

    }

    /**
     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
     * will select value on column chart.
     */
    private void generateInitialLineData() {
        int numValues = 31;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < numValues; ++i) {
            values.add(new PointValue(i, 0));
            axisValues.add(new AxisValue(i).setLabel("" + (i + 1)));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        lineData = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis(axisValues).setHasLines(true);
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("จำนวนระยะเวลา : เดือน");
                axisY.setName("จำนวนเงิน : บาท");
            }
            lineData.setAxisXBottom(axisX);
            lineData.setAxisYLeft(axisY);
        } else {
            lineData.setAxisXBottom(null);
            lineData.setAxisYLeft(null);
        }


        //lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        //lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

        chartTop.setLineChartData(lineData);

        // For build-up animation you have to disable viewport recalculation.
        chartTop.setViewportCalculationEnabled(false);

        // And set initial max viewport and current viewport- remember to set viewports after data.
        //Viewport v = new Viewport(0, 1500, 30, 0); // left top right bottom
        Viewport viewport = new Viewport(chartTop.getMaximumViewport());
        //viewport.left = 0;
        viewport.right = 20;
        viewport.top = 1500;
        viewport.bottom = 500;
        viewport.top = viewport.top; //example max value
        //viewport.bottom = 0;


        chartTop.setMaximumViewport(viewport);
        chartTop.setCurrentViewport(viewport);
        //chartTop.setCurrentViewportWithAnimation(viewport);
        chartTop.setZoomType(ZoomType.VERTICAL);
        chartTop.setViewportCalculationEnabled(true);

        generateLineData(ChartUtils.COLOR_RED);


    }

    private void generateLineData(int color) {
        // Cancel last animation if not finished.
        chartTop.cancelDataAnimation();

        // Modify data targets
        Line line = lineData.getLines().get(0);// For this example there is always only one line.
        line.setColor(color);
        int datas[] = new int[100];
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat monthonly = new SimpleDateFormat("MMM");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,2017);
        cal.set(Calendar.MONTH,Calendar.AUGUST);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(monthonly.format(cal.getTime())+" :: "+maxDay+" Days");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<String> dates = new ArrayList<>();
        for (int i=0; i<maxDay; i++){
            cal.set(Calendar.DAY_OF_MONTH,i+1);
            String date = df.format(cal.getTime());
            dates.add(i,date);
        }


        List<Records> abc = Records.getDataBy_date_n_Type("2017-08",2);
        ArrayList<String> found_dates = new ArrayList<>();
        for(int i=0; i<abc.size(); i++){
            String my_date = abc.get(i).getData_created();
            found_dates.add(i,my_date);
        }

        int month_datas[] = new int[dates.size()];
        for(int i=0; i<dates.size(); i++){
            boolean status = check_exists(found_dates,dates.get(i));
            if(status == true){
                Records reca = Records.getSingleRecordsByDate(dates.get(i));
                //System.out.println(dates.get(i)+" : "+status);
                //System.out.println("Amount :: "+reca.getData_amount());
                Double d = new Double(reca.getData_amount());
                month_datas[i] = d.intValue();

            }else{
                Double d = new Double(0.0);
                month_datas[i] = d.intValue();
                //System.out.println(dates.get(i)+" : "+status);
            }
        }

        System.out.println(Arrays.toString(month_datas));

//        for(int i=0; i<line.getValues().size(); i++){
        for(int i=0; i<month_datas.length; i++){

//           datas[i] = datas[i]+100;
           datas[i] = month_datas[i];
            //System.out.println("datas[i] = "+datas[i]); = 100
        }
        int i=0;
        for (PointValue value : line.getValues()) {

            // Change target only for Y value.
//            value.setTarget(value.getX(), (float) Math.random() * 500); // Data
            value.setTarget(value.getX(), datas[i]); // Data
            i++;
        }

        // Start new data animation with 300ms duration;
        chartTop.startDataAnimation(300);
    }

    public boolean check_exists(ArrayList datalist,String keyword){
        int index = datalist.indexOf(keyword);
        return (index == -1?false:true);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
           // System.out.println("\n ==================== \n");
            generateLineData(value.getColor());
        }

        @Override
        public void onValueDeselected() {
            generateLineData(ChartUtils.COLOR_GREEN);
        }
    }

}