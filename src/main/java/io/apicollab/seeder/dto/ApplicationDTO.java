package io.apicollab.seeder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO implements Serializable {

    private static final long serialVersionUID = -8324210453516097299L;

    @JsonProperty("id")
    private String applicationId;
    private String name;
    private String email;
}
