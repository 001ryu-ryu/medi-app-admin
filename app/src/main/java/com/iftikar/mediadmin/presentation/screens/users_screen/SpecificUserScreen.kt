package com.iftikar.mediadmin.presentation.screens.users_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iftikar.mediadmin.domain.model.User
import com.iftikar.mediadmin.shared.SharedUiEventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecificUserScreen(
    sharedUiEventViewModel: SharedUiEventViewModel,
    specificUserViewModel: SpecificUserViewModel
) {
    val context = LocalContext.current
    val state = specificUserViewModel.state.collectAsStateWithLifecycle()
    var userState by remember { mutableStateOf<User?>(null) }
    val approveUserFlow = specificUserViewModel.approveUserState
    LaunchedEffect(key1 = approveUserFlow) {
        approveUserFlow.collect { event ->
            when (event) {
                is ApproveUserEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

            }

            sharedUiEventViewModel.emitUserApproved()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        userState?.let { user ->
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ElevatedCard(
                        modifier = Modifier.padding(5.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                    if (user.isApproved == 0) {
                        TextButton(
                            onClick = {
                                specificUserViewModel.approveUser(1)
                            }
                        ) {
                            Text(
                                text = "Approve",
                                color = Color.Green
                            )
                        }
                    }

                    if (user.isApproved == 1 && user.block == 0) {
                        TextButton(
                            onClick = {

                            }
                        ) {
                            Text(
                                text = "Block",
                                color = Color.Red
                            )
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(50.dp)) }

        }

        item {
            when {
                state.value.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.value.user != null -> {
                    userState = state.value.user

                    if (userState != null) {
                        UserDetails(
                            user = userState!!
                        )
                    }
                }

                state.value.error != null -> {
                    Text(
                        text = state.value.error ?: "Unknown"
                    )
                }
            }
        }

    }

}

@Composable
private fun UserDetails(
    user: User
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(12.dp)
                    .background(Color.Green, CircleShape)
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = user.userId
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Name: ${user.name}"
        )

        Text(
            text = "Email: ${user.email}"
        )

        Text(
            text = "Phone Number: ${user.phoneNumber}"
        )

        Text(
            text = "Address: ${user.address}"
        )

        Text(
            text = "Pin Code: ${user.pinCode}"
        )
    }
}














