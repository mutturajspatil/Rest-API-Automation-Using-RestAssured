package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification req;
    public RequestSpecification requestSpecification() throws IOException
    {
        if (req == null)
        {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = (new RequestSpecBuilder()).setBaseUri(getGlobalValue("baseURL")).addQueryParam("key", new Object[]{"qaclick123"})
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis= new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\RestAssuredFrameWorkSeries\\src\\test\\java\\resources\\global.properties");
        prop.load(fis);
        return prop.getProperty(key);

    }

    public String getJsonPath(Response response, String key)
    {
        String resp=response.asString();
        JsonPath js = new JsonPath(resp);
        System.out.println(js.get(key).toString());
        return js.get(key).toString();
    }
}
