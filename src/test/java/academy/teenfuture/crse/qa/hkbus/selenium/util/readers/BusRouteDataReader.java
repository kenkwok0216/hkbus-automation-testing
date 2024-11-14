package academy.teenfuture.crse.qa.hkbus.selenium.util.readers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import academy.teenfuture.crse.qa.hkbus.selenium.pojo.BusRoute;

public class BusRouteDataReader extends AbstractDataReader<BusRoute> {

    // Relative path for route list json file
    private static final String ROUTE_LIST_FILE_PATH = "test-data/bus-route-list/BusRouteList_2024-11-13.json";

    @Override
    public List<BusRoute> readData() {
        List<BusRoute> routeList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(ROUTE_LIST_FILE_PATH);
            Object parsedData = JSONValue.parse(reader);

            // Check if data is a JSONObject
            if (parsedData instanceof JSONObject) {
                JSONObject jsonData = (JSONObject) parsedData;

                // Check for "data" key and if it's an array
                if (jsonData.containsKey("data") && jsonData.get("data") instanceof JSONArray) {
                    JSONArray routesJsonArray = (JSONArray) jsonData.get("data");

                    for (Object routeObject : routesJsonArray) {
                        BusRoute route = new BusRoute();

                        // Cast to JSONObject
                        JSONObject routeJson = (JSONObject) routeObject;

                        // Extract data
                        if (routeJson.containsKey("route")) {
                            route.setRoute((String) routeJson.get("route"));
                        }

                        if (routeJson.containsKey("bound")) {
                            route.setBound((String) routeJson.get("bound"));
                        }

                        if (routeJson.containsKey("service_type")) {
                            route.setServiceType((String) routeJson.get("service_type"));
                        }

                        // Origin (EN, TC, SC)
                        if (routeJson.containsKey("orig_en")) {
                            route.setOriginEn((String) routeJson.get("orig_en"));
                        }

                        if (routeJson.containsKey("orig_tc")) {
                            route.setOriginTc((String) routeJson.get("orig_tc"));
                        }

                        if (routeJson.containsKey("orig_sc")) {
                            route.setOriginSc((String) routeJson.get("orig_sc"));
                        }

                        // Destination (EN, TC, SC)
                        if (routeJson.containsKey("dest_en")) {
                            route.setDestinationEn((String) routeJson.get("dest_en"));
                        }

                        if (routeJson.containsKey("dest_tc")) {
                            route.setDestinationTc((String) routeJson.get("dest_tc"));
                        }

                        if (routeJson.containsKey("dest_sc")) {
                            route.setDestinationSc((String) routeJson.get("dest_sc"));
                        }

                        routeList.add(route);
                    }
                } else {
                    System.err.println("Invalid JSON format: Missing or invalid 'data' field.");
                }
            } else {
                System.err.println("Invalid JSON format: Expected a JSONObject.");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return routeList;
    }

}
