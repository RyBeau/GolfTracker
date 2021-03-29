package com.rybeau.golfapp

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*

class OverallStatsFragment : TransitionFragment() {

    private val viewModel: RoundViewModel by activityViewModels() {
        RoundViewModelFactory((requireActivity().application as GolfTrackerRoomApplication).repository)
    }

    private var totalRounds: Int = 0
    private var averageScore: Double = 0.0
    private var averagePutts: Double = 0.0
    private lateinit var previous10Rounds: List<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setLocation(MainActivity.Location.OVERALL_STATS)

        val view = inflater.inflate(R.layout.fragment_overall_stats, container, false)


        viewModel.averageScore.observe(viewLifecycleOwner, { newAverageScore ->
            averageScore = newAverageScore ?: 0.0
            Log.d("Testing", averageScore.toString())
            updateAverageScore(view)
        })
        viewModel.averagePutts.observe(viewLifecycleOwner, { newAveragePutts ->
            averagePutts = newAveragePutts ?: 0.0
            Log.d("Testing", averagePutts.toString())
            updateAveragePutts(view)
        })
        viewModel.totalRounds.observe(viewLifecycleOwner, { newTotal ->
            totalRounds = newTotal ?: 0
            updateTotalRounds(view)
        })
        viewModel.previous10Rounds.observe(viewLifecycleOwner, { newPrevious10 ->
            previous10Rounds = newPrevious10
            Log.d("Testing", previous10Rounds.toString())
            drawChart(view)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    private fun updateAverageScore(view: View){
        val averageScoreText = view.findViewById<TextView>(R.id.averageScore)
        averageScoreText.text = String.format("%.1f", averageScore)
    }

    private fun updateAveragePutts(view: View){
        val averagePuttsText = view.findViewById<TextView>(R.id.puttsPerHole)
        averagePuttsText.text = String.format("%.1f", averagePutts)
    }

    private fun updateTotalRounds(view: View){
        val totalRoundsText = view.findViewById<TextView>(R.id.totalRounds)
        totalRoundsText.text = totalRounds.toString()
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

        Log.d("Testing", arrayOf(previous10Rounds).toString())
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
                        .data(previous10Rounds.toTypedArray()),
                )
            )
        aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }

}