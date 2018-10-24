package rrs.handler;

import static rrs.handler.ViewHolder.*;

import javax.servlet.http.HttpServletRequest;

public class ReservationHandler implements Handler {

    @Override
    public String handleService(HttpServletRequest request) {

        // TODO 2C

        request.setAttribute("reservationList", "dummy");

        return PAGE_RSV_001;
    }

}
