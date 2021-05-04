package br.campinas.redrawing.data;

import org.json.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Decodes json messages to the proper data class
 */
public class DataDecoder
{
    private ObjectMapper mapper;


    /**
     * DataDecoder constructor
     */
    public DataDecoder()
    {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Decodes a message for a specific type
     * 
     * @param jsonString Message in JSON format
     * @param msg_class Target class
     * 
     * @return Decoded object
     */
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

    /**
     * Decode a message.
     * 
     * Seeks the "data_type" key for the class 
     * 
     * @param json Message parsed to a JSONObject
     * 
     * @return Decoded object
     */
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

    /**
     * Decode a message.
     * 
     * Seeks the "data_type" key for the class 
     * 
     * @param json Message in JSON format
     * 
     * @return Decoded object
     */
    public Object decode(String msgString)
    {
        JSONObject json = new JSONObject(msgString);

        return decode(json);
    }

}