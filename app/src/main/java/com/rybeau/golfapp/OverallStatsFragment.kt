package com.rybeau.golfapp

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*

/**
 * Fragment for Overall Stats Page
 */
class OverallStatsFragment : TransitionFragment() {

    /**
     * ViewModel for the Room database
     * */
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

    /**
     * Populates properties from the Room Database
     */
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

    /**
     * Updates the averageScore TextView
     */
    private fun updateAverageScore(view: View){
        val averageScoreText = view.findViewById<TextView>(R.id.averageScore)
        averageScoreText.text = getString(R.string.score_string, if(averageScore > 0) "+ " else "", averageScore)
    }

    /**
     * Updates the averagePutts TextView
     */
    private fun updateAveragePutts(view: View){
        val averagePuttsText = view.findViewById<TextView>(R.id.puttsPerHole)
        averagePuttsText.text = String.format("%.1f", averagePutts)
    }

    /**
     * Updates the totalRounds TextView
     */
    private fun updateTotalRounds(view: View){
        val totalRoundsText = view.findViewById<TextView>(R.id.totalRounds)
        totalRoundsText.text = totalRounds.toString()
    }

    /**
     * Gets the current theme colors to set the correct graph colors
     */
    private fun getThemeColors(): Array<String>{
        val primaryColor = TypedValue()
        val secondaryColor = TypedValue()
        activity?.theme?.resolveAttribute(R.attr.colorPrimary, primaryColor, true)
        activity?.theme?.resolveAttribute(R.attr.colorSecondary, secondaryColor, true)

        val primaryColorString = "#" + Integer.toHexString(primaryColor.data).removePrefix("ff")
        val secondaryColorString = "#" + Integer.toHexString(secondaryColor.data).removePrefix("ff")

        return arrayOf(primaryColorString, secondaryColorString)
    }

    /**
     * Populates the graph with the data from the room database. Configures the properties and
     * appearance of the graph.
     */
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
                        .data(previous10Rounds.reversed().toTypedArray()),
                )
            )
        aaChartView.aa_drawChartWithChartModel(aaChartModel)
    }

}