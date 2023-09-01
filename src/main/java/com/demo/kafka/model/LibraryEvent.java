package com.demo.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryEvent {
    private Integer id;
    private LibraryEventType libraryEventType;
    private Book book;
}
