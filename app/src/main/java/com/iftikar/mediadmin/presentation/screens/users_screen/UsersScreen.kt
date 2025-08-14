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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.iftikar.mediadmin.R
import com.iftikar.mediadmin.util.initialsFrom

@Composable
fun UsersScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(dummyList) { user ->
                UserItem(
                    text = user.name,
                    phoneNumber = user.phoneNumber
                ) { }
            }
        }
    }
}

@Composable
private fun UserItem(
    text: String,
    phoneNumber: String,
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
                    contentDescription = text,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = initialsFrom(text),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = text.uppercase(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = phoneNumber.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )


            }
        }
    }
}

data class SampleUser(
    val name: String,
    val phoneNumber: String
)



val dummyList: List<SampleUser> = listOf(
    SampleUser(name = "Alice Johnson", phoneNumber = "+1 415-555-0101"),
    SampleUser(name = "Benjamin Lee", phoneNumber = "+1 415-555-0102"),
    SampleUser(name = "Charlotte Davis", phoneNumber = "+1 415-555-0103"),
    SampleUser(name = "Daniel Martinez", phoneNumber = "+1 415-555-0104"),
    SampleUser(name = "Emily Clark", phoneNumber = "+1 415-555-0105"),
    SampleUser(name = "Franklin Nguyen", phoneNumber = "+1 415-555-0106"),
    SampleUser(name = "Grace Kim", phoneNumber = "+1 415-555-0107"),
    SampleUser(name = "Henry Thompson", phoneNumber = "+1 415-555-0108"),
    SampleUser(name = "Isabella Rodriguez", phoneNumber = "+1 415-555-0109"),
    SampleUser(name = "Jack Wilson", phoneNumber = "+1 415-555-0110")
)





















