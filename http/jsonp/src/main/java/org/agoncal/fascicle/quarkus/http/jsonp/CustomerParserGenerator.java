package org.agoncal.fascicle.quarkus.http.jsonp;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
// @formatter:off
public class CustomerParserGenerator {

  public void parseEvents() throws IOException {
    // tag::adocParseEvents[]
    StringReader string = new StringReader("{\"hello\":\"world\"}");
    JsonParser parser = Json.createParser(string);

    while (parser.hasNext()) {
      JsonParser.Event event = parser.next();
      switch (event) {
        case START_OBJECT:
        case END_OBJECT:
          System.out.println(event.toString());
          break;
        case KEY_NAME:
          System.out.print("Key " + parser.getString() + " - ");
          break;
        case VALUE_STRING:
          System.out.println("Value " + parser.getString());
          break;
      }
    }
    // end::adocParseEvents[]
  }

  public String parseCustomer() throws IOException {

    String email = null;

    // tag::adocParseCustomer[]
    FileReader file = new FileReader("src/main/resources/customer.json");
    JsonParser parser = Json.createParser(file);

    while (parser.hasNext()) {
      JsonParser.Event event = parser.next();
      while (parser.hasNext() &&
             (event.equals(JsonParser.Event.KEY_NAME) &&
               !parser.getString().matches("email"))) {
        event = parser.next();
      }

      if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches(" email")) {
        parser.next();
        email = parser.getString();
      }
    }

    return email;
    // end::adocParseCustomer[]
  }

  public JsonObject parseString() throws FileNotFoundException {
    // tag::adocParseString[]
    StringReader string = new StringReader("{\"hello\":\"world\"}");
    JsonParser parser = Json.createParser(string);

    // end::adocParseString[]
    return parser.getObject();
  }

  public JsonObject parseStringWithConfig() throws FileNotFoundException {
    Map<String, Boolean> config = new HashMap<>();
    config.put(JsonGenerator.PRETTY_PRINTING, true);

    // tag::adocParseString[]
    StringReader string = new StringReader("{\"hello\":\"world\"}");
    JsonParserFactory factory = Json.createParserFactory(config);
    JsonParser parser = factory.createParser(string);

    // end::adocParseString[]
    return parser.getObject();
  }

  public JsonObject readCustomer() throws FileNotFoundException {
    // tag::adocReadCustomer[]
    FileReader file = new FileReader("src/main/resources/customer.json");
    JsonReader reader = Json.createReader(file);
    JsonObject jsonObject = reader.readObject();
    // end::adocReadCustomer[]
    return jsonObject;
  }
}