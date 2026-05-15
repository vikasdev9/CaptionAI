package com.example.captionai.feature.planner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.captionai.core_ui.theme.*
import com.example.captionai.domain.model.PlannerNote
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerScreen(
    onBack: () -> Unit,
    viewModel: PlannerViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundBlack,
        topBar = {
            TopAppBar(
                title = { Text("Content Planner", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = PrimaryPurple,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            
            // Productivity Dashboard Summary
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp)),
                color = BackgroundCard
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = PrimaryPurple,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "${notes.count { it.isCompleted }}/${notes.size} Posts Ready",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Keep up the great work!",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Schedule",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (notes.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("No content planned yet.", color = TextGray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(notes) { note ->
                        PlannerNoteItem(
                            note = note,
                            onDelete = { viewModel.deleteNote(note) },
                            onToggle = { viewModel.toggleNoteCompletion(note) }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AddNoteDialog(
                onDismiss = { showDialog = false },
                onAdd = { title, desc ->
                    viewModel.addNote(title, desc, System.currentTimeMillis())
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun PlannerNoteItem(
    note: PlannerNote,
    onDelete: () -> Unit,
    onToggle: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        color = BackgroundCard
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = note.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = PrimaryPurple,
                    uncheckedColor = TextGray,
                    checkmarkColor = Color.White
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (note.isCompleted) TextGray else Color.White,
                        textDecoration = if (note.isCompleted) TextDecoration.LineThrough else null
                    )
                )
                if (note.description.isNotBlank()) {
                    Text(
                        text = note.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextGray,
                        maxLines = 1
                    )
                }
                Text(
                    text = dateFormat.format(Date(note.date)),
                    style = MaterialTheme.typography.labelSmall,
                    color = PrimaryPurple.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.6f))
            }
        }
    }
}

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = BackgroundCard,
        title = { Text("Plan New Content", color = Color.White, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryPurple,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onAdd(title, desc) },
                enabled = title.isNotBlank()
            ) {
                Text("Plan It", color = PrimaryPurple, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextGray)
            }
        }
    )
}
