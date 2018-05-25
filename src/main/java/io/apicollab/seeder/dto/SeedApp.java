package io.apicollab.seeder.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SeedApp {

    String email;
    String name;
    List<SeedApi> apis;

}
