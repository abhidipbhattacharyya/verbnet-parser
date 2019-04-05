package io.github.clearwsd.semlink;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.clearwsd.propbank.type.ArgNumber;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * PropBank role to VerbNet role mapping.
 *
 * @author jgung
 */
@Data
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PbVnMapping {

    public static final ObjectMapper OM = new ObjectMapper();

    private String lemma;

    private List<RolesetMapping> mappings = new ArrayList<>();

    @Data
    @Accessors(fluent = true)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class RolesetMapping {
        private String id;
        private List<RolesMapping> mappings = new ArrayList<>();
    }

    @Data
    @Accessors(fluent = true)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class RolesMapping {
        private String vncls;
        private List<MappedRole> roles = new ArrayList<>();
    }

    @Data
    @Accessors(fluent = true)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class MappedRole {
        private ArgNumber number;
        private String vntheta;
    }

    public static List<PbVnMapping> fromJson(@NonNull InputStream inputStream) throws IOException {
        JavaType type = OM.getTypeFactory().constructCollectionType(List.class, PbVnMapping.class);
        return OM.readValue(inputStream, type);
    }

}
