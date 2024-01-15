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
    private static String privateKey = null;

    private final LinkedHashMap<String, Object> header;
    private final LinkedHashMap<String, Object> payload;
    private final String jwsString;
    private String userId;

    private static String getPrivateKey(){
        // if the private key is null, tries to get it from an environment variable or from a .env file
        if(privateKey == null){
            boolean retrieved = false;
            try {
                // searches for the key in the environment variables
                privateKey = System.getenv("PRIVATE_JWT_KEY");

                if(privateKey == null) {
                    // Attempts to get the private key from a .env file
                    Dotenv dotenv = Dotenv.load();
                    privateKey = dotenv.get("PRIVATE_JWT_KEY");
                }
            }catch (Exception e){
                // if any error occurs then the key could not be retrieved.
                throw new JwtException("Could not find the secret key to decode JWT tokens.");
            }
        }
        return privateKey;
    }

    public JWT(String jws) throws JwtException, ParseException {
        if(jws == null)
            throw new ParseException("Token string is null.");

        this.jwsString = jws;

        if(jws.startsWith("Bearer "))
            jws = jws.substring("Bearer ".length());

        // Convert the input string to a byte array for key creation
        byte[] secretKeyBytes = getPrivateKey().getBytes();

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
