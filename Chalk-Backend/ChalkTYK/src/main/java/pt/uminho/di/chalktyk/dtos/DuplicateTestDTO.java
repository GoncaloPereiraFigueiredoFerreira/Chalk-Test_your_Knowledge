package pt.uminho.di.chalktyk.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DuplicateTestDTO {
    @Schema(required = false)
    String courseId;

    @Schema(required = true,allowableValues = {"public", "institution", "course", "not_listed", "private"})
    String visibility;
}
