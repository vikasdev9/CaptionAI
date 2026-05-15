package com.example.captionai.feature.planner

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.captionai.core_ui.components.*
import com.example.captionai.core_ui.theme.*
import com.example.captionai.core.PlannerContentType
import com.example.captionai.core.PlannerStatus
import com.example.captionai.domain.model.PlannerItem
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerScreen(
    onBack: () -> Unit,
    viewModel: PlannerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date()),
                            style = MaterialTheme.typography.labelSmall,
                            color = TextGray
                        )
                        Text(
                            text = "Content Planner",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showAddSheet = true },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(PrimaryPurple.copy(alpha = 0.2f))
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HorizontalCalendar(
                    selectedDate = uiState.selectedDate,
                    onDateSelected = { viewModel.onDateSelected(it) }
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PlannerStatCard(value = uiState.scheduledCount, label = "Scheduled", modifier = Modifier.weight(1f))
                    PlannerStatCard(value = uiState.draftsCount, label = "Drafts", modifier = Modifier.weight(1f))
                    PlannerStatCard(value = uiState.postedCount, label = "Posted", modifier = Modifier.weight(1f))
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Today's schedule",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = TextGray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reminders", color = TextGray, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            val filteredItems = uiState.items.filter { isSameDay(it.date, uiState.selectedDate) }
            
            if (filteredItems.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                        Text("No tasks for this day.", color = TextGray)
                    }
                }
            } else {
                items(filteredItems) { item ->
                    PlannerTaskCard(
                        title = item.title,
                        time = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(item.date)),
                        type = item.type,
                        status = item.status,
                        onClick = { /* Edit item */ }
                    )
                }
            }

            item {
                AIInsightCard()
            }

            item {
                Button(
                    onClick = { showAddSheet = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryGradient, RoundedCornerShape(28.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+ Schedule new post", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }

        if (showAddSheet) {
            AddPlannerItemSheet(
                onDismiss = { showAddSheet = false },
                onAdd = { title, desc, date, type, status ->
                    viewModel.addItem(title, desc, date, type, status)
                    showAddSheet = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlannerItemSheet(
    onDismiss: () -> Unit,
    onAdd: (String, String, Long, PlannerContentType, PlannerStatus) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(PlannerContentType.POST) }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = BackgroundDark,
        scrimColor = Color.Black.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Schedule New Post", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
            
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            // Simple Type Selector
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PlannerContentType.values().forEach { type ->
                    val isSelected = selectedType == type
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedType = type },
                        label = { Text(type.name) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryPurple,
                            labelColor = if (isSelected) Color.White else TextGray
                        )
                    )
                }
            }

            Button(
                onClick = { onAdd(title, desc, System.currentTimeMillis(), selectedType, PlannerStatus.SCHEDULED) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Schedule", fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun isSameDay(timeMillis: Long, calendar: Calendar): Boolean {
    val itemCal = Calendar.getInstance().apply { timeInMillis = timeMillis }
    return itemCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
            itemCal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)
}
