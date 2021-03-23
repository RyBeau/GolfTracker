package com.rybeau.golfapp

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import com.github.aachartmodel.aainfographics.aachartcreator.*

class OverallStatsFragment : TransitionFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setLocation(MainActivity.Location.OVERALL_STATS)
        return inflater.inflate(R.layout.fragment_overall_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }

        populateStats(view)
        drawChart(view)
    }

    private fun populateStats(view: View) {
        val averageScore = view.findViewById<TextView>(R.id.averageScore)
        val handicap = view.findViewById<TextView>(R.id.handicap)
        val puttsPerHole = view.findViewById<TextView>(R.id.puttsPerHole)
        val totalRounds = view.findViewById<TextView>(R.id.totalRounds)

        averageScore.text = "+13"
        handicap.text = "13"
        puttsPerHole.text = "3"
        totalRounds.text = "115"
    }

    private fun getThemeColors(): Array<String>{
        val primaryColor = TypedValue()
        val secondaryColor = TypedValue()
        activity?.theme?.resolveAttribute(R.attr.colorPrimary, primaryColor, true)
        activity?.theme?.resolveAttribute(R.attr.colorSecondary, secondaryColor, true)

        val primaryColorString = "#" + Integer.toHexString(primaryColor.data).removePrefix("ff")
        val secondaryColorString = "#" + Integer.toHexString(secondaryColor.data).removePrefix("ff")

        return arrayOf(primaryColorString, secondaryColorString)
    }

    private fun drawChart(view: View){
        val colors : Array<String> = getThemeColors()

        val aaChartView = view.findViewById<AAChartView>(R.id.aa_chart_view)

        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Spline)
            .backgroundColor(colors[0])
            .axesTextColor(colors[1])
            .xAxisVisible(false)
            .yAxisTitle("")
            .tooltipEnabled(false)
            .yAxisAllowDecimals(false)
            .markerRadius(0f)
            .series(arrayOf(
                AASeriesElement()
                        .color(colors[1])
                        .showInLegend(false)
                        .lineWidth(3f)
                        .data(arrayOf(3, 7, 2, 0, -2, 1, 2, -1, 4, 0)),
                )
            )
        aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }

}