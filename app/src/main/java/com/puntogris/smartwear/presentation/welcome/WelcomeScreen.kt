package com.puntogris.smartwear.presentation.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puntogris.smartwear.R
import com.puntogris.smartwear.presentation.weather.SmartWearTheme

private data class WelcomePage(
    @StringRes val message: Int,
    @DrawableRes val illustration: Int
)

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    val pages = remember {
        listOf(
            WelcomePage(R.string.welcome_item_1, R.drawable.ic_search_illustration),
            WelcomePage(R.string.welcome_item_2, R.drawable.ic_clothing_illustration),
            WelcomePage(R.string.welcome_item_3, R.drawable.ic_amusment_park_ilustration)
        )
    }
    val pagerState = rememberPagerState(pageCount = pages::size)

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.welcome_title),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.app_slogan),
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) { index ->
                WelcomePageContent(pages[index])
            }

            PagerIndicator(pageCount = pages.size, selectedPage = pagerState.currentPage)
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = onContinue,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.action_continue), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun WelcomePageContent(page: WelcomePage) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(page.illustration),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = 28.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = stringResource(page.message),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun PagerIndicator(pageCount: Int, selectedPage: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        repeat(pageCount) { index ->
            val width by animateDpAsState(
                targetValue = if (index == selectedPage) 26.dp else 8.dp,
                label = "page indicator width"
            )
            Box(
                Modifier
                    .width(width)
                    .height(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (index == selectedPage) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outlineVariant
                    )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WelcomeScreenPreview() {
    SmartWearTheme { WelcomeScreen(onContinue = {}) }
}
