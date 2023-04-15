/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.common.enums;

import lombok.Getter;

/**
 * @author moabdu
 * @version vr0.1
 * @since 02-03-2022
 */
@Getter
public enum SuccessCodes {
    CREATED_SUCCESS("SUCCESS-01", "Created Successfully."),
    UPDATED_SUCCESS("SUCCESS-02", "This ID: %s Updated Successfully."),
    DELETED_SUCCESS("SUCCESS-03", "This ID: %s Deleted Successfully."),
    DELETED_VIDEO_SUCCESS("SUCCESS-04", "This Video Deleted Successfully."),
    COLLECTION_IDS_DELETED_SUCCESS("SUCCESS-05", "These IDs: %s Deleted Successfully."),
    UPLOAD_VIDEOS_SUCCESS("SUCCESS-06", "These videos Uploaded Successfully."),
    UPLOAD_VIDEO_SUCCESS("SUCCESS-07", "This video Uploaded Successfully."),
    DELETED_IMAGE_SUCCESS("SUCCESS-08", "This image Deleted Successfully."),
    ADD_TO_CART_SUCCESS("SUCCESS-09", "Added to cart successfully."),
    DELETED_CART_ITEM_SUCCESS("SUCCESS-10", "CartItem Deleted Successfully."),;

    final String successCode;
    final String message;

    /**
     * @param successCode String
     * @param message     String
     */
    SuccessCodes(String successCode, String message) {
        this.successCode = successCode;
        this.message = message;
    }
}
