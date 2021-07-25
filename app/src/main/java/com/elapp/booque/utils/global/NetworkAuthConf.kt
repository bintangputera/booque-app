package com.elapp.booque.utils.global

object NetworkAuthConf {
    const val FIRST_PAGE = 1
    const val ITEM_PER_PAGE = 20
    const val BASE_URL = "https://wsjti.id/booque/public/"
    const val BLOG_URL = "$BASE_URL/blog/"
    const val THUMBNAIL_BASE_URL = "$BASE_URL/storage/blog/thumbnail/"
//    const val BOOK_THUMBNAIL_BASE_URL = "$BASE_URL/storage/user/1/books/"
    const val BOOK_THUMBNAIL_BASE_URL = "$BASE_URL/storage/user/"
    const val BOOK_UPLOAD_BASE_URL = BASE_URL + "api/v1/tambah-buku"
    const val TOKEN = "XSMN4BAkBys7jSbl2PIK7uZ30Y4SSwSPLyvdQj3S"
}