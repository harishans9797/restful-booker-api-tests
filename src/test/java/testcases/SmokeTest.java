package testcases;

import domain.models.Booking;
import domain.models.BookingDates;
import domain.models.BookingWithIds;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static utils.Constants.NOT_FOUND;

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
        String bookingToJsonFormatted =  formatBooking(bookingToJson);;

        String authToken = getAuthenticationToken();
        assertNotNull(authToken);

        String bookingCreatedJson = bookingController.createBooking(bookingToJsonFormatted);
        BookingWithIds bookingCreated = jacksonUtils.fromJson(bookingCreatedJson, BookingWithIds.class);

        retry(40000L, 1000L, () -> {
            String getCreatedBookingJson = bookingController.getBookingById(bookingCreated.getBookingid(), 200);
            Booking getBooking = jacksonUtils.fromJson(getCreatedBookingJson, Booking.class);

            assertBooking(getBooking, booking);
        });

        Booking bookingEdited = new Booking();
        bookingEdited.setBookingdates(bookingDates);
        bookingEdited.setFirstname("Jane");
        bookingEdited.setLastname("Doe");
        bookingEdited.setDepositpaid(false);
        bookingEdited.setTotalprice(432);
        bookingEdited.setAdditionalneeds("Breakfast");

        String bookingEditedToJson = jacksonUtils.toJson(bookingEdited);
        String bookingEditedToJsonFormatted =  formatBooking(bookingEditedToJson);
        bookingController.updateBooking(bookingEditedToJsonFormatted, bookingCreated.getBookingid());

        retry(40000L, 1000L, () -> {
            String getCreatedBookingJson = bookingController.getBookingById(bookingCreated.getBookingid(), 200);
            Booking getBooking = jacksonUtils.fromJson(getCreatedBookingJson, Booking.class);

            assertBooking(getBooking, bookingEdited);
        });

        bookingController.deleteBooking(bookingCreated.getBookingid());
        String notFound = bookingController.getBookingById(bookingCreated.getBookingid(), 404);
        assertEquals(NOT_FOUND, notFound);
    }

    private void assertBooking(Booking bookingExpected, Booking booking) {
        assertEquals(bookingExpected.getFirstname(), booking.getFirstname());
        assertEquals(bookingExpected.getLastname(), booking.getLastname());
        assertEquals(bookingExpected.getTotalprice(), booking.getTotalprice());
        assertEquals(bookingExpected.isDepositpaid(), booking.isDepositpaid());
        assertEquals(bookingExpected.getBookingdates().getCheckin(), booking.getBookingdates().getCheckin());
        assertEquals(bookingExpected.getBookingdates().getCheckout(), booking.getBookingdates().getCheckout());
        assertEquals(bookingExpected.getTotalprice(), booking.getTotalprice());
        assertEquals(bookingExpected.getAdditionalneeds(), booking.getAdditionalneeds());
    }

    private String formatBooking(String bookingStr) {
        return bookingStr
                .replace("firstName", "firstname")
                .replace("lastName", "lastname")
                .replace("totalPrice", "totalprice")
                .replace("depositPaid", "depositpaid")
                .replace("bookingDates", "bookingdates")
                .replace("checkIn", "checkin")
                .replace("checkOut", "checkout")
                .replace("additionalNeeds", "additionalneeds");
    }
}
