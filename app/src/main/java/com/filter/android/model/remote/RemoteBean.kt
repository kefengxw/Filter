package com.filter.android.model.remote

import com.google.gson.annotations.SerializedName

data class RemoteBean(
    @SerializedName("artists")
    val artists: Artists
)

data class Artists( //total overview of many Artists
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("href")
    val href: String = "",
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("items")
    val items: List<ArtistItem>?
)

data class ArtistItem( //each artist information
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("href")
    val href: String = "",
    @SerializedName("popularity")
    val popularity: Int = 0,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("followers")
    val followers: Followers,
    @SerializedName("images")
    val images: List<ImagesItem>?,
    @SerializedName("uri")
    val uri: String = "",
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls
)

data class ImagesItem( //different size images of certain artist
    @SerializedName("width")
    val width: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("height")
    val height: Int = 0
)

data class Followers(
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("href")
    val href: String? = null
)

data class ExternalUrls( //external or open url without token
    @SerializedName("spotify")
    val spotify: String = ""
)