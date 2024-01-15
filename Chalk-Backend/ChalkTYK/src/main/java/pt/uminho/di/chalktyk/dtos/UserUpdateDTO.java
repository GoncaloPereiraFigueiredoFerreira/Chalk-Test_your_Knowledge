package pt.uminho.di.chalktyk.dtos;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String name;
    private String email;
    private String description;
    private String photoPath;
}
