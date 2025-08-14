package com.iftikar.mediadmin.presentation.screens.users_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.iftikar.mediadmin.R
import com.iftikar.mediadmin.domain.model.User
import com.iftikar.mediadmin.util.initialsFrom


@Composable
fun AllUsersScreen(
    viewModel: UsersScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when(val usersState = state.value) {
            is UsersScreenState.Failure -> {
                // todo handle failure
            }
            UsersScreenState.Idle -> {}
            UsersScreenState.Loading -> {
                // todo shimmer effect
            }
            is UsersScreenState.Success -> {
                usersState.users?.let { users ->
                    items(users.filter { it.isApproved == 1 && it.block == 0 }) {user ->
                        UserItem(
                            user = user,
                            onClick = {
                                navHostController.navigate(BottomBarScreensRoute.SpecificUserScreen)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun UserItem(
    user: User,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = R.drawable.user_profile,
                    contentDescription = user.name,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = initialsFrom(user.name),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = user.name.uppercase(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = user.phoneNumber.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun TestSpecificUser() {
    Text(
        text = "Specific User Screen",
        modifier = Modifier.systemBarsPadding()
    )
}
































