package testcases;

import domain.models.Booking;
import domain.models.BookingDates;
import domain.models.BookingWithIds;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SmokeTest extends BaseRestFulHelper {
    @Test
    void SmokeTest () {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin( "2013-02-23");
        bookingDates.setCheckout("2014-10-23");
        Booking booking = new Booking();
        booking.setBookingdates(bookingDates);
        booking.setFirstname("John");
        booking.setLastname("Doe");
        booking.setDepositpaid(true);
        booking.setTotalprice(321);
        booking.setAdditionalneeds("Dinner");

        jacksonUtils.init();
        String bookingToJson = jacksonUtils.toJson(booking);
        String bookingToJsonFormatted =  bookingToJson
                .replace("firstName", "firstname")
                .replace("lastName", "lastname")
                .replace("totalPrice", "totalprice")
                .replace("depositPaid", "depositpaid")
                .replace("bookingDates", "bookingdates")
                .replace("checkIn", "checkin")
                .replace("checkOut", "checkout")
                .replace("additionalNeeds", "additionalneeds");

        String authToken = getAuthenticationToken();
        String bookingCreatedJson = bookingController.createBooking(authToken, bookingToJsonFormatted);
        BookingWithIds bookingCreated = jacksonUtils.fromJson(bookingCreatedJson, BookingWithIds.class);

        retry(40L, 1000L, () -> {
            String getCreatedBookingJson = bookingController.getBookingById(authToken, bookingCreated.getBookingid());
            Booking getBooking = jacksonUtils.fromJson(getCreatedBookingJson, Booking.class);

            assertEquals(getBooking.getFirstname(), booking.getFirstname());
            assertEquals(getBooking.getLastname(), booking.getLastname());
            assertEquals(getBooking.getTotalprice(), booking.getTotalprice());
            assertEquals(getBooking.isDepositpaid(), booking.isDepositpaid());
            assertEquals(getBooking.getBookingdates().getCheckin(), booking.getBookingdates().getCheckin());
            assertEquals(getBooking.getBookingdates().getCheckout(), booking.getBookingdates().getCheckout());
            assertEquals(getBooking.getTotalprice(), booking.getTotalprice());
            assertEquals(getBooking.getAdditionalneeds(), booking.getAdditionalneeds());
        });
    }
}
