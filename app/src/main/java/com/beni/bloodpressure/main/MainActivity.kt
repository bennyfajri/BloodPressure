package com.beni.bloodpressure.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.beni.bloodpressure.R
import com.beni.bloodpressure.databinding.ActivityMainBinding
import com.beni.core.data.local.room.PatientEntitiy
import com.beni.core.util.ConstantFunction
import com.beni.core.util.ConstantFunction.divideIntoEachCategory
import com.beni.core.util.ConstantVariable.TAG
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random
import kotlin.random.nextInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPatientData {
            initChart()
            getPatientdata()
        }
    }

    private fun initChart() {
        binding.chart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)

            dragDecelerationFrictionCoef = 0.95f

            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)

            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)

            holeRadius = 58f
            transparentCircleRadius = 61f

            setDrawCenterText(true)

            rotationAngle = 0f

            isRotationEnabled = true
            isHighlightPerTapEnabled = true

            legend.apply {
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.VERTICAL
                setDrawInside(false)
                xEntrySpace = 7f
                yEntrySpace = 0f
                yOffset = 0f
            }
        }
    }

    private fun getPatientdata() {
        viewModel.getAllData().observe(this) { patients ->
            val entries = ArrayList<PieEntry>()

            for(i in bloodPressure.indices) {
                val pieValue = patients.divideIntoEachCategory().count {
                    it == i
                }.toFloat()
                entries.add(PieEntry(pieValue, bloodPressure[i]))
            }

            val listColor = listColor.map {
                ContextCompat.getColor(this@MainActivity, it)
            }
            val pieDataSet = PieDataSet(entries, getString(R.string.app_name)).apply {
                setDrawIcons(false)
                sliceSpace = 3f
                iconsOffset = MPPointF(0f,40f)
                selectionShift = 5f
                colors = listColor
                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            }

            Log.d(TAG, "getPatientdata: ${entries}")
            val data = PieData(pieDataSet).apply {
                setValueFormatter(PercentFormatter())
                setValueTextColor(Color.BLACK)
            }
            binding.chart.apply {
                setData(data)
                highlightValue(null)
                invalidate()
            }
        }
    }


    companion object {
        @StringRes
        private val listColor = listOf(
            R.color.normal,
            R.color.elevated,
            R.color.high_pressure,
            R.color.high_pressure2,
            R.color.hypertensivecrisis,
        )

        private val Context.bloodPressure
            get() = listOf(
                getString(R.string.normal),
                getString(R.string.elevated),
                getString(R.string.high_pressure),
                getString(R.string.high_pressure2),
                getString(R.string.hypertensivecrisis),
            )
    }

    private fun checkPatientData(onDataReady: () -> Unit) {
        viewModel.getAllData().observe(this@MainActivity) { patients ->
            if (patients.isEmpty()) {
                val listPatientData = mutableListOf<PatientEntitiy>()
                for (i in 0 until 100) {
                    val name = ConstantFunction.getRandomString(15)
                    val systole = Random.nextInt(110..200)
                    val diastole = Random.nextInt(70..130)

                    val patient = PatientEntitiy(
                        name = name,
                        systole = systole,
                        diastole = diastole
                    )
                    listPatientData.add(patient)
                }
                viewModel.insertMultipleData(listPatientData)
                onDataReady()
            } else {
                onDataReady()
            }
        }
    }
}