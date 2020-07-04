package io.controller;

import io.entity.*;
import io.service.ReadingsService;
import org.json.Cookie;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "readings")
public class ReadingsController {

    @Qualifier("readingsServiceImpl")
    @Autowired
    private ReadingsService readingsService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Readings> findAll(){
        return readingsService.findAll();
    }

    @RequestMapping(value = "{vin}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Readings> findByVin(@PathVariable("vin") String vin ){
        return readingsService.findByVin(vin);
    }

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Readings readings){

        readingsService.create(readings);
    }

    @RequestMapping(value = "locations",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GeoLocation> findAllLocations() throws IOException {
        List<GeoLocation> res = new ArrayList<>();
        List<Locations> temp = readingsService.findAllLocations();
        String baseURL="https://maps.googleapis.com/maps/api/geocode/json?latlng=";
        String key="&key=AIzaSyCCswMte50IBjdugGSW2Uk_WbcdFXJDhQw";
        for(Locations l:temp){
            GeoLocation gl = new GeoLocation();
            gl.setVin(l.getVin());
            gl.setTimestamp(l.getTimestamp());
            StringBuilder sb = new StringBuilder();
            sb.append(baseURL);
            sb.append(l.getLatitude());
            sb.append(",");
            sb.append(l.getLongitude());
            sb.append(key);
            URL url = new URL(sb.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
           // System.out.println(con.getResponseCode());
            String regex = "\"formatted_address\"\\s?:\\s? \"([^\"]+)\"";
            Pattern p = Pattern.compile(regex);
            String result = content.toString();
            Matcher m = p.matcher(result);
            if(m.find()){
                if(m.group(1)!= null )
                    gl.setFormattedAddress(m.group(1));
                else
                    gl.setFormattedAddress("**UNKNOWN**");
            }else{
                gl.setFormattedAddress("**UNKNOWN**");
            }
            res.add(gl);
            in.close();
            con.disconnect();
        }
       // System.out.println(res);
        return res;
    }
}
