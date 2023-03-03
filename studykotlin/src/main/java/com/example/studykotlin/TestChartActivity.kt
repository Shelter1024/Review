package com.example.studykotlin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate

class TestChartActivity : AppCompatActivity(), OnChartValueSelectedListener {
    lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_chart)


        chart = findViewById(R.id.chart1)
        chart.setOnChartValueSelectedListener(this)

        chart.getDescription().setEnabled(false)
        chart.setTouchEnabled(true)
        chart.setDragDecelerationFrictionCoef(0.9f)
        // enable scaling and dragging
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)
        chart.setHighlightPerDragEnabled(true)
        chart.setDrawBorders(false)

        chart.setPinchZoom(true)
        chart.setBackgroundColor(Color.WHITE)

        chart.animateX(1500)

        // get the legend (only possible after setting data)

        // get the legend (only possible after setting data)
        val l: Legend = chart.getLegend()

        // modify the legend ...

        // modify the legend ...
        l.form = LegendForm.LINE
//        l.typeface = tfLight
        l.textSize = 11f
        l.textColor = Color.BLACK
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
//        l.setYOffset(11f);

        //        l.setYOffset(11f);
        val xAxis: XAxis = chart.getXAxis()
//        xAxis.typeface = tfLight
        xAxis.textSize = 11f
        xAxis.textColor = Color.BLACK
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisValueFormatter()
//        xAxis.enableGridDashedLine(10f, 2f, 2f)

        val leftAxis: YAxis = chart.getAxisLeft()
//        leftAxis.typeface = tfLight
//        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.textColor = Color.BLACK
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = false

        val rightAxis: YAxis = chart.getAxisRight()
        rightAxis.isEnabled = false
//        rightAxis.typeface = tfLight
        rightAxis.textColor = Color.RED
//        rightAxis.axisMaximum = 900f
//        rightAxis.axisMinimum = -200f
//        rightAxis.axisMaximum = 200f
//        rightAxis.axisMinimum = 0f
        rightAxis.setDrawGridLines(true)
        rightAxis.setDrawZeroLine(true)
        rightAxis.isGranularityEnabled = false

        setData()
    }

    private fun setData() {
        val count = 10
        val range = 28
        val values1 = ArrayList<Entry>()

        for (i in 0 until count) {
            val value: Float = (Math.random() * (range / 2f)).toFloat() + 50
            values1.add(Entry(i.toFloat(), value))
        }

        val values2 = ArrayList<Entry>()

        for (i in 0 until count) {
            val value: Float = (Math.random() * (range / 2f)).toFloat() + 70
            values2.add(Entry(i.toFloat(), value))
        }

        val set1: LineDataSet
        val set2: LineDataSet

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set2 = chart.data.getDataSetByIndex(1) as LineDataSet
            set1.values = values1
            set2.values = values2
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values1, "销毁情况")
            set1.axisDependency = AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.BLUE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = LineDataSet(values2, "产出情况")
            set2.axisDependency = AxisDependency.LEFT
            set2.color = Color.RED
            set2.setCircleColor(Color.RED)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f));

            // create a data object with the data sets
            val data = LineData(set1, set2)
            data.setValueTextColor(Color.BLACK)
            data.setValueTextSize(9f)

            // set data
            chart.data = data
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.d("Shelter", "TestChartActivity onValueSelected ${e.toString()}, h=$h")
    }

    override fun onNothingSelected() {
        Log.d("Shelter", "TestChartActivity onNothingSelected")
    }
}