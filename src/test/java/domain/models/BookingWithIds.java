package domain.models;

public class BookingWithIds {
    private long bookingid;
    private Booking booking;

    public Booking getBooking() {
        return booking;
    }

    public long getBookingid() {
        return bookingid;
    }
}
