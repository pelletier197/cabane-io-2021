package com.qohash.cabaneio2021.model.post

import com.qohash.cabaneio2021.model.user.User
import java.util.*

interface Post {
    val id: PostId
    val author: User
}

@JvmInline
value class PostId(
    val value: UUID
)