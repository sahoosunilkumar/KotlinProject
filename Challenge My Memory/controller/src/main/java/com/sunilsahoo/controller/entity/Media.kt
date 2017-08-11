package com.sunilsahoo.controller.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Media {

    /**

     * @return
     * *     The m
     */
    /**

     * @param m
     * *     The m
     */
    @SerializedName("m")
    @Expose
    var m: String? = null

    /**
     * No args constructor for use in serialization

     */
    constructor() {}

    /**

     * @param m
     */
    constructor(m: String) {
        this.m = m
    }

}
