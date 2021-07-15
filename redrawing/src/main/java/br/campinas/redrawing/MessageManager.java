package br.campinas.redrawing;

import java.util.Hashtable;
import java.util.LinkedList;

import br.campinas.redrawing.data.DataDecoder;

/**
 * Manages incoming messages
 */
public class MessageManager 
{
    Hashtable<Class<?>, LinkedList<Object>> receivedLists;
    int queueSize;
    DataDecoder decoder;
    boolean delete;

    /**
     * MessageManager constructor
     * 
     * @param queueSize The max size of the message queue (by class type)
     * @param delete True if its should delete old messages when full
     */
    public MessageManager(int queueSize, boolean delete)
    {
        this.queueSize = queueSize;
        this.delete = delete;

        receivedLists = new Hashtable<Class<?>, LinkedList<Object>>();
        decoder = new DataDecoder();
    }

    /**
     * Get a message
     * 
     * Warning: use hasMessage before getting messages for avoid exceptions
     * 
     * @param <DataType> The type of the data class
     * @param dataType The type of the data class
     * @return The last received message from this type
     */
    public <DataType> DataType getData(Class<?> dataType)
    {
        LinkedList<Object> list = receivedLists.get(dataType);
        Object data = receivedLists.get(dataType).removeFirst();

        return (DataType) data;
    }

    /**
     * Get a data object without proper class
     * 
     * @param dataType The type of the data class
     * @return The last received message from this type
     */
    public Object getGenericData(Class<?> dataType)
    {
        return receivedLists.get(dataType).removeFirst();
    }

    /**
     * Inserts a received message in json format
     * 
     * @param jsonMessage The JSON message
     */
    public void insertMessage(String jsonMessage)
    {
        Object data = decoder.decode(jsonMessage);

        if(data == null)
        {
            return;
        }
        
        LinkedList<Object> list = receivedLists.get(data.getClass());

        if(list == null)
        {
            list = new LinkedList<Object>();
            receivedLists.put(data.getClass(), list);
        }

        if(list.size() < this.queueSize)
        {
            list.add(data);
        }
        else if(delete)
        {
            list.removeFirst();
            list.add(data);
        }   
    }

    /**
     * See if it has received message from a data type
     * 
     * @param dataType The type to check
     * @return True if it has messages
     */
    public boolean hasMessage(Class<?> dataType)
    {
        LinkedList<Object> list = receivedLists.get(dataType);
        
        if(list == null)
        {
            return false;
        }

        if(list.size() == 0)
        {
            return false;
        }

        return true;
    }
}
