package com.puntogris.smartwear.presentation.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Thunderstorm
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.Current
import com.puntogris.smartwear.domain.model.Daily
import com.puntogris.smartwear.domain.model.Forecast
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.Weather
import com.puntogris.smartwear.domain.model.conditions.Temperature
import com.puntogris.smartwear.domain.model.events.RecommendationEvent
import com.puntogris.smartwear.presentation.util.EmptyLocationException
import com.puntogris.smartwear.presentation.util.TimeOfDay
import com.puntogris.smartwear.utils.Result

private val Coral = Color(0xFFFF6F74)
@Composable
fun SmartWearTheme(content: @Composable () -> Unit) {
    val background = colorResource(R.color.color_background)
    val surface = colorResource(R.color.color_surface)
    val contentColor = colorResource(R.color.color_secondary)
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme(
            primary = Color(0xFFFFA4A7),
            background = background,
            surface = surface,
            onBackground = contentColor,
            onSurface = contentColor,
            surfaceContainer = contentColor.copy(alpha = 0.06f)
        ) else lightColorScheme(
            primary = Coral,
            background = background,
            surface = surface,
            onBackground = contentColor,
            onSurface = contentColor,
            surfaceContainer = contentColor.copy(alpha = 0.04f)
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    location: Location?, weatherResult: Result<Weather>?, suggestions: List<Location>,
    isSearching: Boolean, showSuggestions: Boolean, onSearch: (String) -> Unit,
    onUseCurrentLocation: () -> Unit, onSuggestionSelected: (Location) -> Unit,
    onDismissSuggestions: () -> Unit, onRefresh: () -> Unit
) {
    val context = LocalContext.current
    val weather = (weatherResult as? Result.Success)?.data
    var query by remember(location?.name) { mutableStateOf(location?.name.orEmpty()) }
    val focusManager = LocalFocusManager.current

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        PullToRefreshBox(isRefreshing = weatherResult is Result.Loading, onRefresh = onRefresh) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(stringResource(R.string.app_name), fontSize = 30.sp, fontWeight = FontWeight.Black)
                        Text(
                            stringResource(R.string.app_slogan),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedTextField(
                            value = query,
                            onValueChange = { query = it; if (it.isBlank()) onDismissSuggestions() },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(stringResource(R.string.search_location_hint)) },
                            singleLine = true,
                            shape = RoundedCornerShape(18.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus(); onSearch(query) }),
                            trailingIcon = {
                                if (isSearching) CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp)
                                else IconButton(onClick = { focusManager.clearFocus(); onSearch(query) }) {
                                    Icon(Icons.Outlined.Search, stringResource(R.string.action_search))
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                            )
                        )
                        TextButton(onClick = onUseCurrentLocation) {
                            Icon(Icons.Outlined.LocationSearching, null, Modifier.size(18.dp))
                            Spacer(Modifier.size(8.dp))
                            Text(stringResource(R.string.action_use_current_location_plain))
                        }
                    }
                }
                if (showSuggestions) item {
                    SuggestionsCard(suggestions, onDismissSuggestions) {
                        query = it.name
                        focusManager.clearFocus()
                        onSuggestionSelected(it)
                    }
                }
                when {
                    location == null -> item { EmptyLocation(onUseCurrentLocation) }
                    weather != null -> {
                        item { CurrentWeather(location, weather) }
                        item {
                            WeatherSection(
                                stringResource(R.string.today_forecast_title_compose),
                                weather.forecast.events.filter { it.isValid() }
                                    .joinToString(" ") { it.buildSummary(context) }
                            )
                        }
                        item {
                            WeatherSection(
                                stringResource(R.string.recommendation_title_compose),
                                weather.forecast.events.filterIsInstance<RecommendationEvent>()
                                    .filter { it.isValid() }.joinToString(" ") { it.buildRecommendation(context) },
                                accented = true
                            )
                        }
                    }
                    weatherResult is Result.Failure && weatherResult.exception !is EmptyLocationException -> item {
                        Text(stringResource(R.string.weather_error_inline), color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrentWeather(location: Location, weather: Weather) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(location.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(10.dp))
            Text(weather.current.temperature.asString(), fontSize = 46.sp, fontWeight = FontWeight.Light)
            Text(weather.current.description, style = MaterialTheme.typography.titleMedium)
        }
        WeatherIcon(weather.current.icon)
    }
}

@Composable
private fun WeatherIcon(icon: String) {
    val image = when (icon.take(2)) {
        "01" -> if (icon.endsWith("n")) Icons.Outlined.NightsStay else Icons.Outlined.WbSunny
        "02", "03", "04" -> Icons.Outlined.Cloud
        "09" -> Icons.Outlined.Grain
        "10" -> Icons.Outlined.WaterDrop
        "11" -> Icons.Outlined.Thunderstorm
        "13" -> Icons.Outlined.AcUnit
        "50" -> Icons.Outlined.Air
        else -> Icons.Outlined.Cloud
    }
    Icon(
        imageVector = image,
        contentDescription = stringResource(R.string.weather_icon_description),
        modifier = Modifier.size(72.dp),
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun WeatherSection(title: String, body: String, accented: Boolean = false) {
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(22.dp))
            .background(if (accented) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent)
            .padding(if (accented) 18.dp else 4.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(body, style = MaterialTheme.typography.bodyLarge, lineHeight = 25.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SuggestionsCard(suggestions: List<Location>, onDismiss: () -> Unit, onSelected: (Location) -> Unit) {
    Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(Modifier.fillMaxWidth().padding(start = 18.dp, top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.search_results), style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
            IconButton(onClick = onDismiss) { Icon(Icons.Outlined.Close, stringResource(R.string.action_close)) }
        }
        if (suggestions.isEmpty()) {
            Text(stringResource(R.string.no_locations_found), Modifier.padding(18.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
        } else suggestions.forEachIndexed { index, location ->
            Text(
                location.displayName.ifBlank { location.name },
                Modifier.fillMaxWidth().clickable { onSelected(location) }.padding(horizontal = 18.dp, vertical = 14.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            if (index != suggestions.lastIndex) HorizontalDivider(Modifier.padding(horizontal = 18.dp))
        }
    }
}

@Composable
private fun EmptyLocation(onUseCurrentLocation: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(vertical = 52.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(stringResource(R.string.empty_location_title), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.empty_location_body), color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextButton(onClick = onUseCurrentLocation) { Text(stringResource(R.string.action_use_current_location_plain)) }
        }
    }
}

@PreviewLightDark
@Composable
private fun WeatherScreenPreview() {
    val locationName = stringResource(R.string.preview_location_name)
    val temperature = Temperature.Celsius(22)
    val weather = Weather(
        current = Current(
            temperature = temperature,
            description = stringResource(R.string.preview_weather_description),
            icon = "clear-day"
        ),
        daily = Daily(
            min = Temperature.Celsius(17),
            max = Temperature.Celsius(25)
        ),
        forecast = Forecast(
            events = emptyList(),
            time = TimeOfDay.Afternoon(R.string.afternoon)
        )
    )

    SmartWearTheme {
        WeatherScreen(
            location = Location(
                displayName = stringResource(R.string.preview_location_display_name),
                name = locationName
            ),
            weatherResult = Result.Success(weather),
            suggestions = emptyList(),
            isSearching = false,
            showSuggestions = false,
            onSearch = {},
            onUseCurrentLocation = {},
            onSuggestionSelected = {},
            onDismissSuggestions = {},
            onRefresh = {}
        )
    }
}
