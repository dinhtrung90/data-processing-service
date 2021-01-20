package com.vts.data.processing.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppError implements Serializable {
    private String errorMessage;
    private String title;
    private Integer code;
}
