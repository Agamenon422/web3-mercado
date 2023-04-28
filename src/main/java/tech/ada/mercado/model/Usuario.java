package tech.ada.mercado.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Usuario {

    @Id
    private String id;

    private String name;
    private String username;
    private List<String> roles;
    private Long birth;
    private LocalDate since;

}