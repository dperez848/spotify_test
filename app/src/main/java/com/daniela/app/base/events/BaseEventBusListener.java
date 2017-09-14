package com.daniela.app.base.events;

import com.daniela.app.base.events.EventAlertDialog;
import com.daniela.app.base.events.EventProgressDialog;
import com.daniela.app.base.events.EventSnackbarMessage;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 9/12/17.
 */

public interface BaseEventBusListener {

    void onProgressDialogEvent(EventProgressDialog event);

    void onAlertDialogEvent(EventAlertDialog alert);

    void onSnackbarMessageEvent(EventSnackbarMessage event);

}