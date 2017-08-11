package com.sunilsahoo.controller

import com.sunilsahoo.controller.entity.ImageList
import com.sunilsahoo.controller.utils.ControllerConstants


class ImageListController : Controller() {

    /**
     * sends getdevice request to server
     * @param taskListener
     */
    fun getImageList(taskListener: ITaskListener) {
        //set entity type to parse the data to DeviceList object
        super.setEntity(ImageList::class.java)
        super.execute(ControllerConstants.RequestCodes.GET_DEVICE_LSIT.toInt(), GET_DEVICE_LIST_URL, ControllerConstants.HttpMethods.HTTP_GET, null, taskListener)
    }

    companion object {
        private val GET_DEVICE_LIST_URL = "https://api.flickr.com/services/feeds/photos_public.gne?&format=json&per_page=50&nojsoncallback=1"
        private val TAG = ImageListController::class.java.name
    }

}
