package com.ae.api.models.responses.sourcesResponse

import com.ae.api.models.source.SourceDTO
import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @field:SerializedName("sources")
    val sources: List<SourceDTO?>? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)