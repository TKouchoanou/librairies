package com.malo.library.external.client.book;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BookAvailabilityResult {
    Long bookId;

    Integer quantity;
}
