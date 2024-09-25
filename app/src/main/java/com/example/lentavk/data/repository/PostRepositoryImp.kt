package com.example.lentavk.data.repository

import com.example.lentavk.data.api.ApiService
import com.example.lentavk.data.mapper.PostMapper
import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.entity.PostComment
import com.example.lentavk.domain.entity.Statistic
import com.example.lentavk.domain.entity.StatisticType
import com.example.lentavk.domain.entity.UserState
import com.example.lentavk.domain.repository.PostRepository
import com.example.lentavk.utils.mergeWith
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PostRepositoryImp @Inject constructor(
    private val storage: VKPreferencesKeyValueStorage,
    private val apiService: ApiService,
    private val mapper: PostMapper,
): PostRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val token
        get () = VKAccessToken.restore(storage)

    private val nextPostEvent = MutableSharedFlow<Unit>(replay = 1)
    private val refreshListEvent = MutableSharedFlow<List<Post>>()

    private val postListFlow = flow {
        nextPostEvent.emit(Unit)

        nextPostEvent.collect {
            val startFrom = nextFrom
            if (startFrom == null && postList.isNotEmpty()) {
                emit(postList)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadRecommendation(getAccessToken())
            } else {
                apiService.loadRecommendation(getAccessToken(), startFrom)
            }
            nextFrom = response.postContent.nexFrom

            val posts = mapper.mapResponseToPost(response)
            _postList.addAll(posts)

            emit(postList)
        }
    }.retry(3) {
        delay(RETRY_TIME)
        true
    }

    private val _postList = mutableListOf<Post>()

    private val postList: List<Post>
        get() = _postList.toList()

    private var nextFrom: String? = null

    private val userStateEvent = MutableSharedFlow<Unit>(replay = 1)

    private val userStateFlow = flow {
        userStateEvent.emit(Unit)

        userStateEvent.collect{
            val userState = if(VK.isLoggedIn()) UserState.Authorized else UserState.NotAuthorized
            emit(userState)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = UserState.Init
    )

    private val lenta: StateFlow<List<Post>> = postListFlow
        .mergeWith(refreshListEvent)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = postList
        )

    private fun getAccessToken(): String{
        return token?.accessToken ?: throw IllegalStateException("Token is NULL")
    }

    override fun getUserStateFlow(): StateFlow<UserState> = userStateFlow

    override fun getPosts(): StateFlow<List<Post>> = lenta

    override fun getComments(post: Post): StateFlow<List<PostComment>> = flow{
        val comments = apiService.getComments(
            token = getAccessToken(),
            owner_id = post.groupId,
            postId = post.id
        )


        emit(mapper.mapResponseToComments(comments))
    }.retry {
        delay(RETRY_TIME)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun loadNextPosts() {
        nextPostEvent.emit(Unit)
    }

    override suspend fun checkUserState() {
        userStateEvent.emit(Unit)
    }

    override suspend fun changeLikeStatus(post: Post) {
        val response = if (post.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                owner_id = post.groupId,
                postId = post.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                owner_id = post.groupId,
                postId = post.id
            )
        }

        val newLikesCount = response.likes.count
        val newStats = post.statistics.toMutableList().apply {
            removeIf {it.type == StatisticType.LIKES}
            add(Statistic(StatisticType.LIKES, newLikesCount))
        }

        val newPost = post.copy(statistics = newStats, isLiked = !post.isLiked)
        val postIndex = _postList.indexOf(post)

        _postList[postIndex] = newPost

        refreshListEvent.emit(postList)
    }

    private companion object{
        const val RETRY_TIME: Long = 3000
    }
}