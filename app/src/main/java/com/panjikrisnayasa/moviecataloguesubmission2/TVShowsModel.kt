package com.panjikrisnayasa.moviecataloguesubmission2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TVShowsModel(
    var poster: Int = 0,
    var title: String? = "",
    var ratingScore: Float = 0f,
    var genre: String? = "",
    var runtime: String? = "",
    var network: String? = "",
    var synopsis: String? = ""
) : Parcelable