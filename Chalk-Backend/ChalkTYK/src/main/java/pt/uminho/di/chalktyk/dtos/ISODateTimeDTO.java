package pt.uminho.di.chalktyk.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ISODateTimeDTO {
    private LocalDateTime value;
}
