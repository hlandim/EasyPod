package com.hlandim.easypod.logic.web.audiosear

/**
 * Created by hlandim on 10/11/17.
 */
data class AuthRequest(val client_id: String,
                       val client_secret: String,
                       val redirect_uri: String,
                       val grant_type: String)

