package com.sunilsahoo.controller.entity

import java.util.ArrayList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageList {

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
     * *     The modified
     */
    /**

     * @param modified
     * *     The modified
     */
    @SerializedName("modified")
    @Expose
    var modified: String? = null
    /**

     * @return
     * *     The generator
     */
    /**

     * @param generator
     * *     The generator
     */
    @SerializedName("generator")
    @Expose
    var generator: String? = null
    /**

     * @return
     * *     The items
     */
    /**

     * @param items
     * *     The items
     */
    @SerializedName("items")
    @Expose
    var items: List<Item> = ArrayList()

    /**
     * No args constructor for use in serialization

     */
    constructor() {}

    /**

     * @param title
     * *
     * @param items
     * *
     * @param description
     * *
     * @param link
     * *
     * @param generator
     * *
     * @param modified
     */
    constructor(title: String, link: String, description: String, modified: String, generator: String, items: List<Item>) {
        this.title = title
        this.link = link
        this.description = description
        this.modified = modified
        this.generator = generator
        this.items = items
    }

}
