package com.puntogris.smartwear.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.puntogris.smartwear.R
import com.puntogris.smartwear.presentation.weather.SmartWearTheme

private data class SettingChoice(val label: String, val value: String)

@Composable
fun SettingsScreen(
    selectedTheme: String,
    selectedUnits: String,
    version: String,
    onThemeSelected: (String) -> Unit,
    onUnitsSelected: (String) -> Unit,
    onRateApp: () -> Unit,
    onVersionClick: () -> Unit,
    onWeatherDataClick: () -> Unit,
    onLicensesClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onTermsClick: () -> Unit
) {
    var dialog by remember { mutableStateOf<SettingsDialog?>(null) }
    val themeChoices = stringArrayResource(R.array.theme_names)
        .zip(stringArrayResource(R.array.theme_values)) { label, value -> SettingChoice(label, value) }
    val unitChoices = stringArrayResource(R.array.units_names)
        .zip(stringArrayResource(R.array.units_values)) { label, value -> SettingChoice(label, value) }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SettingsSection(stringResource(R.string.appearance)) {
                    SettingsRow(
                        icon = Icons.Outlined.Palette,
                        title = stringResource(R.string.theme),
                        summary = themeChoices.labelFor(selectedTheme),
                        onClick = { dialog = SettingsDialog.Theme }
                    )
                    SectionDivider()
                    SettingsRow(
                        icon = Icons.Outlined.Straighten,
                        title = stringResource(R.string.units),
                        summary = unitChoices.labelFor(selectedUnits),
                        onClick = { dialog = SettingsDialog.Units }
                    )
                }
            }
            item {
                SettingsSection(stringResource(R.string.settings_app_section)) {
                    SettingsRow(
                        icon = Icons.Outlined.StarOutline,
                        title = stringResource(R.string.rate_us),
                        summary = stringResource(R.string.rate_the_app),
                        onClick = onRateApp
                    )
                    SectionDivider()
                    SettingsRow(
                        icon = Icons.Outlined.Info,
                        title = stringResource(R.string.version),
                        summary = version,
                        onClick = onVersionClick,
                        showChevron = false
                    )
                }
            }
            item {
                SettingsSection(stringResource(R.string.transparency)) {
                    SettingsRow(Icons.Outlined.CloudQueue, stringResource(R.string.weather_data_attribution), stringResource(R.string.weather_data_attribution_summary), onWeatherDataClick)
                    SectionDivider()
                    SettingsRow(Icons.Outlined.Code, stringResource(R.string.open_source_licenses), null, onLicensesClick)
                    SectionDivider()
                    SettingsRow(Icons.Outlined.PrivacyTip, stringResource(R.string.privacy_policy), null, onPrivacyClick)
                    SectionDivider()
                    SettingsRow(Icons.Outlined.Description, stringResource(R.string.terms_and_conditions), null, onTermsClick)
                }
            }
        }
    }

    when (dialog) {
        SettingsDialog.Theme -> ChoiceDialog(
            title = stringResource(R.string.theme),
            choices = themeChoices,
            selectedValue = selectedTheme,
            onDismiss = { dialog = null },
            onSelected = { dialog = null; onThemeSelected(it) }
        )
        SettingsDialog.Units -> ChoiceDialog(
            title = stringResource(R.string.units),
            choices = unitChoices,
            selectedValue = selectedUnits,
            onDismiss = { dialog = null },
            onSelected = { dialog = null; onUnitsSelected(it) }
        )
        null -> Unit
    }
}

private enum class SettingsDialog { Theme, Units }

@Composable
private fun SettingsSection(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            title,
            modifier = Modifier.padding(horizontal = 4.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        ) { Column { content() } }
    }
}

@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    summary: String?,
    onClick: () -> Unit,
    showChevron: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(23.dp))
        Spacer(Modifier.size(16.dp))
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            summary?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }
        }
        if (showChevron) Icon(Icons.Outlined.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SectionDivider() {
    HorizontalDivider(Modifier.padding(start = 57.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f))
}

@Composable
private fun ChoiceDialog(
    title: String,
    choices: List<SettingChoice>,
    selectedValue: String,
    onDismiss: () -> Unit,
    onSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                choices.forEach { choice ->
                    Row(
                        Modifier.fillMaxWidth().clickable { onSelected(choice.value) }.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = choice.value == selectedValue, onClick = { onSelected(choice.value) })
                        Text(choice.label, Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_close)) } }
    )
}

private fun List<SettingChoice>.labelFor(value: String): String =
    firstOrNull { it.value == value }?.label.orEmpty()

@PreviewLightDark
@Composable
private fun SettingsScreenPreview() {
    SmartWearTheme {
        SettingsScreen(
            selectedTheme = "system",
            selectedUnits = "metric",
            version = "1.0.0 (17)",
            onThemeSelected = {}, onUnitsSelected = {}, onRateApp = {}, onVersionClick = {},
            onWeatherDataClick = {}, onLicensesClick = {}, onPrivacyClick = {}, onTermsClick = {}
        )
    }
}
