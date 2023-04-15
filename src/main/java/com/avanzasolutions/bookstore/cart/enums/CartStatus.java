/**
 * ********************
 * Copyright ©2023-2023 MoAbdu. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of MoAbdu.
 * *********************
 */

package com.avanzasolutions.bookstore.cart.enums;

import lombok.Getter;

@Getter
public enum CartStatus {
    /**
     * This status could be used to indicate that a user has started creating a new shopping cart, but hasn't added any items to it yet.
     * The cart is not yet ready for checkout and can still be modified.
     */
    DRAFT("Draft"),
    /**
     * This status could be used to indicate that a user has added items to their shopping cart and is in the process of checking out.
     * The cart is not yet complete, but the user cannot modify it anymore.
     */
    PENDING("Pending"),
    /**
     * This status could be used to indicate that a user has successfully checked out their shopping cart and the purchase is complete.
     * The cart cannot be modified anymore.
     */
    COMPLETE("Complete"),
    /**
     * This status could be used to indicate that the shopping cart is in an invalid state, and cannot be used for checkout.
     * For example, if the user has deleted all items from the cart, the cart status could be set to INVALID.
     */
    INVALID("Invalid"),
    /**
     * This status value indicates that the shopping cart has been cancelled by the user or by the system.
     * This could happen if the user decides to abandon their cart, or if the system detects that the cart has been inactive for a certain period of time.
     * In this case, the cart should not be processed any further, and any items that were added to the cart should be removed or returned to inventory.
     */
    CANCELLED("Cancelled"),
    /**
     * This status value indicates that the shopping cart has been put on hold for some reason, and cannot be processed at this time.
     * This could happen if there are issues with the user's payment method, or if there are problems with the items in the cart (e.g. out of stock, discontinued, etc.).
     * In this case, the cart should be flagged as "on hold" and the user should be notified of the reason for the hold.
     * The cart can be released from hold status once the issue has been resolved.
     */
    ON_HOLD("OnHold"),
    /**
     * This status could be used to indicate that the shopping cart has expired and cannot be used for checkout.
     * For example, if the user has started creating a cart but has not completed the checkout process within a certain time period,
     * the cart status could be set to EXPIRED.
     */
    EXPIRED("Expired"),
    /**
     * This status could be used to indicate that an error occurred during the checkout process and the cart cannot be completed.
     * For example, if there is a problem processing the payment, the cart status could be set to ERROR.
     */
    ERROR("Error");

    private final String displayName;

    CartStatus(String displayName) {
        this.displayName = displayName;
    }
}

