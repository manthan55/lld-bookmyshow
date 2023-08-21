package com.scaler.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpResponseDto {
    private ResponseStatus responseStatus;
    private Long userId;
}
