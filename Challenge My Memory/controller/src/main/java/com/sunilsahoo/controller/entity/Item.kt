package com.sunilsahoo.controller.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item {

    /**

     * @return
     * *     The title
     */
    /**

     * @param title
     * *     The title
     */
    @SerializedName("title")
    @Expose
    var title: String? = null
    /**

     * @return
     * *     The link
     */
    /**

     * @param link
     * *     The link
     */
    @SerializedName("link")
    @Expose
    var link: String? = null
    /**

     * @return
     * *     The media
     */
    /**

     * @param media
     * *     The media
     */
    @SerializedName("media")
    @Expose
    var media: Media? = null
    /**

     * @return
     * *     The dateTaken
     */
    /**

     * @param dateTaken
     * *     The date_taken
     */
    @SerializedName("date_taken")
    @Expose
    var dateTaken: String? = null
    /**

     * @return
     * *     The description
     */
    /**

     * @param description
     * *     The description
     */
    @SerializedName("description")
    @Expose
    var description: String? = null
    /**

     * @return
     * *     The published
     */
    /**

     * @param published
     * *     The published
     */
    @SerializedName("published")
    @Expose
    var published: String? = null
    /**

     * @return
     * *     The author
     */
    /**

     * @param author
     * *     The author
     */
    @SerializedName("author")
    @Expose
    var author: String? = null
    /**

     * @return
     * *     The authorId
     */
    /**

     * @param authorId
     * *     The author_id
     */
    @SerializedName("author_id")
    @Expose
    var authorId: String? = null
    /**

     * @return
     * *     The tags
     */
    /**

     * @param tags
     * *     The tags
     */
    @SerializedName("tags")
    @Expose
    var tags: String? = null

    /**
     * No args constructor for use in serialization

     */
    constructor() {}

    /**

     * @param tags
     * *
     * @param author
     * *
     * @param dateTaken
     * *
     * @param title
     * *
     * @param description
     * *
     * @param link
     * *
     * @param published
     * *
     * @param media
     * *
     * @param authorId
     */
    constructor(title: String, link: String, media: Media, dateTaken: String, description: String, published: String, author: String, authorId: String, tags: String) {
        this.title = title
        this.link = link
        this.media = media
        this.dateTaken = dateTaken
        this.description = description
        this.published = published
        this.author = author
        this.authorId = authorId
        this.tags = tags
    }

}
