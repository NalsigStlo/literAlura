package com.alura.LiterAlura.servicio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorTipoDatos implements IConversorTipoDatos{
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertirDatos(String datos, Class<T> record) {
        try {
            return mapper.readValue(datos, record);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
