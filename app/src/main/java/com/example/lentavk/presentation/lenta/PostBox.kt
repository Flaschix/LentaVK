package com.example.lentavk.presentation.lenta

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lentavk.R
import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.entity.Statistic
import com.example.lentavk.domain.entity.StatisticType
import java.lang.IllegalStateException

@Composable
fun PostBox(
    modifier: Modifier = Modifier,
    post: Post,
    onCommentClickListener: (Statistic) -> Unit,
    onLikeClickListener: (Statistic) -> Unit,
) {

    Card(
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Header(post)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = post.text)

            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = post.imgContentUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(10.dp))

            Stats(
                statistics = post.statistics,
                onCommentClickListener = onCommentClickListener,
                onLikeClickListener = onLikeClickListener,
                isFavourite = post.isLiked
            )
        }
    }
}

@Composable
fun Header(post: Post){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = post.imgGroupUrl,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier
            .weight(1f)
        ) {
            Text(text = post.groupName, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = post.time)
        }
        Icon(
            painter = painterResource(id = R.drawable.baseline_more_vert_24),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun Stats(
    statistics: List<Statistic>,
    onCommentClickListener: (Statistic) -> Unit,
    onLikeClickListener: (Statistic) -> Unit,
    isFavourite: Boolean
){
    Row{
        Row(modifier = Modifier.weight(1f)) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconNearText(
                iconId = R.drawable.baseline_remove_red_eye_24,
                text = formatStatCount(viewsItem.count),
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            val likesItem = statistics.getItemByType(StatisticType.LIKES)
            IconNearText(
                iconId = R.drawable.baseline_reply_24,
                text = formatStatCount(sharesItem.count),
            )
            IconNearText(
                iconId = R.drawable.baseline_comment_24,
                text = formatStatCount(commentsItem.count),
                itemClickListener = {
                    onCommentClickListener(commentsItem)
                }
            )
            IconNearText(
                iconId = if(isFavourite) R.drawable.baseline_thumb_up_24_black else R.drawable.baseline_thumb_up_24,
                text = formatStatCount(likesItem.count),
                itemClickListener = {
                    onLikeClickListener(likesItem)
                },
                tint = if(isFavourite) Color.Red else MaterialTheme.colorScheme.secondary
            )
        }
    }
}

private fun List<Statistic>.getItemByType(type: StatisticType): Statistic {
    return this.find { it.type == type } ?: throw IllegalStateException("Undefined StatisticType: $type")
}

@Composable
fun IconNearText(
    iconId: Int,
    text: String,
    itemClickListener: (() -> Unit)? = null,
    tint: Color = MaterialTheme.colorScheme.secondary
) {
    val modifier = if(itemClickListener == null) Modifier
    else Modifier.clickable { itemClickListener() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = text)
    }
}

private fun formatStatCount(count: Int): String{
    return if(count > 100_000) String.format("%sK", count/1000)
    else if(count > 1000) String.format("%.1fK", (count / 1000f))
    else count.toString()
}