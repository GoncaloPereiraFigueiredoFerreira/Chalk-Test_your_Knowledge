package pt.uminho.di.chalktyk.apis.utility;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.*;

import lombok.*;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

@Getter
@Setter
public class JWT {
    private final LinkedHashMap<String, Object> header;
    private final LinkedHashMap<String, Object> payload;
    private final String jwsString;
    private String userId;
    public JWT(String jws) throws JwtException, ParseException {
        if(jws == null)
            throw new ParseException("Token string is null.");

        this.jwsString = jws;

        if(jws.startsWith("Bearer "))
            jws = jws.substring("Bearer ".length());

        Dotenv dotenv = Dotenv.load();
        String secretKeyString = dotenv.get("PRIVATE_JWT_KEY");

        // Convert the input string to a byte array for key creation
        byte[] secretKeyBytes = secretKeyString.getBytes();

        // Build the secret key using the specified algorithm (HS256)
        SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

        // Verify and parse the JWT using the same key
        Jwts.parser().setSigningKey(key).build().parseClaimsJws(jws);

        String[] chunks = jws.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();

        this.header = new JSONParser(new String(decoder.decode(chunks[0]))).parseObject();
        this.payload = new JSONParser(new String(decoder.decode(chunks[1]))).parseObject();
    }

    public Object getPayloadParam(String key){
        return payload.get(key);
    }

    /**
     * Extracts the user role from the token.
     * @return user role
     */
    public String getUserRole() {
        return (String) getPayloadParam("role");
    }
    public String getUserEmail() {
        return (String) getPayloadParam("username");
    }
}
