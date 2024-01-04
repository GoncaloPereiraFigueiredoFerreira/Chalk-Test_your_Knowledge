package pt.uminho.di.chalktyk;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pt.uminho.di.chalktyk.apis.utility.JWT;

@SpringBootTest
class ChalkTykApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testJwtToken() throws ParseException {
        JWT jwt = new JWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Imx1aXNpbmhvQGdtYWlsLmNvbSIsIm5hbWUiOiJMdWlnaSIsInJvbGUiOiJTdHVkZW50IiwiYWN0aXZlIjp0cnVlLCJkYXRlX2NyZWF0ZWQiOiIyMDIzLTEyLTAyVDE4OjI2IiwibGFzdF9hY2Nlc3MiOiIyMDIzLTEyLTAyVDE4OjI2IiwiaWF0IjoxNzAxNTQxNTc0LCJleHAiOjE3MDE5MDE1NzR9.CJ0R7VJSH9-oDGAEb0r5e74YHHNUHXP992IFFwnaxU8");
        System.out.println(jwt.getPayloadParam("role"));
    }
}
