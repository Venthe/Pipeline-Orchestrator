package eu.venthe.platform.application.other.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.*;

import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class JsonWebToken {
    @ToString.Exclude
    @EqualsAndHashCode.Include
    private final String token;

    private final JsonNode header;
    private final JsonNode payload;
    private final String signature;

    @SneakyThrows
    public JsonWebToken(final ObjectMapper objectMapper, final String token) {
        this.token = token;

        var chunks = token.split("\\.");
        var decoder = Base64.getUrlDecoder();
        header = objectMapper.readTree(new String(decoder.decode(chunks[0])));
        payload = objectMapper.readTree(new String(decoder.decode(chunks[1])));
        signature = chunks[2];
    }

    @SneakyThrows
    public void verify(String publicKey) {
        var keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        var kf = KeyFactory.getInstance("RSA");
        var pk = kf.generatePublic(keySpec);
        var jwtParser = Jwts.parser()
                .verifyWith(pk)
                .build();
        try {
            jwtParser.parse(token);
        } catch (Exception e) {
            throw new RuntimeException("Could not verify JWT token integrity!", e);
        }
    }
}
