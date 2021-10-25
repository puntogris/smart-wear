package com.puntogris.whatdoiwear.presentation.weather

import android.annotation.SuppressLint
import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CursorAdapter
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.common.SimpleResult
import com.puntogris.whatdoiwear.common.WeatherResult
import com.puntogris.whatdoiwear.databinding.FragmentWeatherBinding
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.whatdoiwear.presentation.base.BaseFragment
import com.puntogris.whatdoiwear.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
@DelicateCoroutinesApi
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()

    override fun initializeViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        subscribeWeatherUi()
        subscribeRefreshUi()

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.test, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        var suggestions = listOf("Appleeeeeeeeeeeeeeeeeeeeeeeeeeee", "Blueberry", "Carrot", "Daikon")

        binding.locationTextField.suggestionsAdapter = cursorAdapter

        binding.locationTextField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               // hideKeyboard()

                if (!query.isNullOrBlank()){
                    viewModel.setQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })


        viewModel.geocodingLocations.observe(viewLifecycleOwner){
            val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
            it.forEachIndexed { index, suggestion ->
                cursor.addRow(arrayOf(index, suggestion.name))
            }

            cursorAdapter.changeCursor(cursor)
          //  binding.locationTextField.
        }

        binding.locationTextField.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            @SuppressLint("Range")
            override fun onSuggestionClick(position: Int): Boolean {
              //  hideKeyboard()
                val cursor = binding.locationTextField.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                binding.locationTextField.setQuery(selection, false)

                // Do something with selection
                return true
            }
        })

    }

    private fun subscribeWeatherUi(){
        launchAndRepeatWithViewLifecycle {
            viewModel.weather.observe(viewLifecycleOwner){
                when(it){
                    is WeatherResult.Success -> onSuccess(it.data)
                    WeatherResult.Error -> onError()
                    WeatherResult.InProgress -> inProgress()
                }
            }
//            viewModel.weatherResult.collect { result ->
//                when (result) {
//                    is WeatherResult.kt.Success -> onSuccess(result.data)
//                    WeatherResult.kt.Error -> onError()
//                    WeatherResult.kt.InProgress -> inProgress()
//                }
//            }
        }
    }

    private fun inProgress(){
//        binding.apply {
//            bottomSheetLayout.userName.gone()
//            bottomSheetLayout.bottomSheetProgressBar.visible()
//            weatherProgressBar.visible()
//        }
    }

    private fun onError(){
        createSnackBar(getString(R.string.error_weather_api))
   //     binding.weatherProgressBar.gone()
    }

    private fun onSuccess(data: WeatherDto){
     //   viewModel.updateWeather(data)
//        with(binding){
//            weatherProgressBar.gone()
//            bottomSheetLayout.apply {
//                clothingRecommendation.visible()
//                userName.visible()
//                bottomSheetProgressBar.gone()
//            }
//        }
    }

    private fun subscribeRefreshUi(){
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                lifecycleScope.launch {
                    val message = when(viewModel.onRefreshLocation()){
                        SimpleResult.Failure -> "Error"
                        SimpleResult.Success -> "Success"
                    }
                    createSnackBar(message)
                    isRefreshing = false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.settings).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }
}