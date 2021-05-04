package br.campinas.redrawing.data;

import org.json.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataDecoder
{
    private ObjectMapper mapper;
    public DataDecoder()
    {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SuppressWarnings("rawtypes")
    public Object decode(String jsonString, Class msg_class)
    {
        Object decoded = null;
        try
        {   
            decoded = mapper.readValue(jsonString, msg_class);
        }
        catch(Exception e)
        {
            
        }

        return decoded;
    }

    @SuppressWarnings("rawtypes")
    public Object decode(JSONObject json)
    {
        Object decoded = null;

        try
        {
            String jsonString = json.toString();
            String data_type = json.getString("data_type");
            Class msg_class = Class.forName("br.campinas.redrawing.data.".concat(data_type));

            decoded = decode(jsonString, msg_class);

        }
        catch(Exception e)
        {
            
        }

        return decoded;
    }

    public Object decode(String msgString)
    {
        JSONObject json = new JSONObject(msgString);

        return decode(json);
    }

}