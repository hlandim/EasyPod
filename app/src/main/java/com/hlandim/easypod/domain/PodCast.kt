package com.hlandim.easypod.domain

/**
 * Created by hlandim on 08/11/17.
 */

class PodCast(
        var id: Long = 0,
        var idApi: Long = 0,
        var title: String = "",
        var description: String = "",
        var imgFullUrl: String = "",
        var imgThumbUrl: String = "",
        var signed: Boolean = false
)