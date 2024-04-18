package presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import utils.Constant.FORMAT_DATE

@OptIn(ExperimentalMaterial3Api::class, FormatStringsInDatetimeFormats::class)
@Composable
fun DateRangePickerDialog(
    totalValue: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
        },
        shape = RoundedCornerShape(16.dp),
        confirmButton = {
            TextButton(onClick = {
                if (dateRangePickerState.selectedStartDateMillis != null && dateRangePickerState.selectedEndDateMillis != null) {
                    onDismiss()

                    val selectedStartDateMillis = dateRangePickerState.selectedStartDateMillis
                    val selectedStartDate: LocalDate? = selectedStartDateMillis?.let { millis ->
                        val instant = Instant.fromEpochMilliseconds(millis)
                        val timeZone = TimeZone.currentSystemDefault()
                        val zonedDateTime = instant.toLocalDateTime(timeZone)
                        val localDate = zonedDateTime.date
                        localDate
                    }

                    val selectedEndDateMillis = dateRangePickerState.selectedEndDateMillis
                    val selectedEndDate: LocalDate? = selectedEndDateMillis?.let { millis ->
                        val instant = Instant.fromEpochMilliseconds(millis)
                        val timeZone = TimeZone.currentSystemDefault()
                        val zonedDateTime = instant.toLocalDateTime(timeZone)
                        val localDate = zonedDateTime.date
                        localDate
                    }

                    startDate = selectedStartDate?.format(LocalDate.Format { byUnicodePattern(FORMAT_DATE) }).toString()
                    endDate = selectedEndDate?.format(LocalDate.Format { byUnicodePattern(FORMAT_DATE) }).toString()

                    totalValue(selectedStartDate?.daysUntil(selectedEndDate!!).toString())
                    onConfirm(startDate, endDate)
                }
            }) {
                Text(text = "Confirm")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {},
            headline = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Select Date Leave",
                    textAlign = TextAlign.Center
                )
            },
            showModeToggle = false,
            modifier = Modifier.height(height = 500.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, FormatStringsInDatetimeFormats::class)
@Composable
fun DateRangePickerBottomSheet(
    totalValue: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(16.dp),
        content = {
            DateRangePicker(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .height(height = 450.dp),
                state = dateRangePickerState,
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Select Date Leave",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                },
                headline = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(end = 24.dp)
                        ) {
                            TextButton(onClick = {
                                coroutineScope.launch {
                                    modalBottomSheetState.hide()
                                    onDismiss()
                                }
                            }) {
                                Text(text = "Cancel")
                            }
                            TextButton(onClick = {
                                if (dateRangePickerState.selectedStartDateMillis != null && dateRangePickerState.selectedEndDateMillis != null) {
                                    onDismiss()

                                    val selectedStartDateMillis = dateRangePickerState.selectedStartDateMillis
                                    val selectedStartDate: LocalDate? = selectedStartDateMillis?.let { millis ->
                                        val instant = Instant.fromEpochMilliseconds(millis)
                                        val timeZone = TimeZone.currentSystemDefault()
                                        val zonedDateTime = instant.toLocalDateTime(timeZone)
                                        val localDate = zonedDateTime.date
                                        localDate
                                    }

                                    val selectedEndDateMillis = dateRangePickerState.selectedEndDateMillis
                                    val selectedEndDate: LocalDate? = selectedEndDateMillis?.let { millis ->
                                        val instant = Instant.fromEpochMilliseconds(millis)
                                        val timeZone = TimeZone.currentSystemDefault()
                                        val zonedDateTime = instant.toLocalDateTime(timeZone)
                                        val localDate = zonedDateTime.date
                                        localDate
                                    }

                                    startDate = selectedStartDate?.format(LocalDate.Format { byUnicodePattern(FORMAT_DATE) }).toString()
                                    endDate = selectedEndDate?.format(LocalDate.Format { byUnicodePattern(FORMAT_DATE) }).toString()

                                    totalValue(selectedStartDate?.daysUntil(selectedEndDate!!) ?: 0)
                                    onConfirm(startDate, endDate)

                                    coroutineScope.launch {
                                        modalBottomSheetState.hide()
                                        onDismiss()
                                    }
                                }
                            }) {
                                Text(text = "Confirm")
                            }
                        }
                    }
                },
                showModeToggle = false,
            )
        }
    )
}
