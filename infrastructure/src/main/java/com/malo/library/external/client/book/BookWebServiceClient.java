package com.malo.library.external.client.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bookLibrary",url = "${api.book.url}")
public interface BookWebServiceClient {
    @GetMapping(value = "/{bookId}/availabilities")
   BookAvailabilityResult availabilities(@PathVariable("bookId") Long bookId);

    @PostMapping(value = "/{bookId}/bookings")
    BookBookingResult booking(@PathVariable("bookId") Long bookId, @RequestBody BookBookingRequest bookingRequest);

    @PostMapping(value = "/{bookId}/availabilities")
    void restore(@PathVariable("bookId") Long bookId, @RequestBody Integer quantity);
}
