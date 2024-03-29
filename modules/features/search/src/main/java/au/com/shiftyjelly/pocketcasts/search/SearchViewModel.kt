package au.com.shiftyjelly.pocketcasts.search

import androidx.lifecycle.ViewModel
import au.com.shiftyjelly.pocketcasts.analytics.AnalyticsEvent
import au.com.shiftyjelly.pocketcasts.analytics.AnalyticsSource
import au.com.shiftyjelly.pocketcasts.analytics.AnalyticsTrackerWrapper
import au.com.shiftyjelly.pocketcasts.models.to.FolderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchHandler: SearchHandler,
    private val analyticsTracker: AnalyticsTrackerWrapper,
) : ViewModel() {

    val searchResults = searchHandler.searchResults
    val loading = searchHandler.loading

    fun updateSearchQuery(query: String) {
        searchHandler.updateSearchQuery(query)
    }

    fun setOnlySearchRemote(remote: Boolean) {
        searchHandler.setOnlySearchRemote(remote)
    }

    fun setSource(source: AnalyticsSource) {
        searchHandler.setSource(source)
    }

    fun trackSearchResultTapped(
        source: AnalyticsSource,
        uuid: String,
        type: SearchResultType
    ) {
        analyticsTracker.track(
            AnalyticsEvent.SEARCH_RESULT_TAPPED,
            AnalyticsProp.searchResultTapped(source = source, uuid = uuid, type = type)
        )
    }

    enum class SearchResultType(val value: String) {
        PODCAST_LOCAL_RESULT("podcast_local_result"),
        PODCAST_REMOTE_RESULT("podcast_remote_result"),
        FOLDER("folder"),
    }

    private object AnalyticsProp {
        private const val SOURCE = "source"
        private const val UUID = "uuid"
        private const val RESULT_TYPE = "result_type"
        fun searchResultTapped(source: AnalyticsSource, uuid: String, type: SearchResultType) =
            mapOf(SOURCE to source.analyticsValue, UUID to uuid, RESULT_TYPE to type.value)
    }
}

sealed class SearchState {
    object NoResults : SearchState()
    data class Results(val list: List<FolderItem>, val loading: Boolean, val error: Throwable?) : SearchState()
}
