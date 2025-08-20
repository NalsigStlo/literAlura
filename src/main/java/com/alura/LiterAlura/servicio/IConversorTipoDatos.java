package com.alura.LiterAlura.servicio;

public interface IConversorTipoDatos {
    <T> T convertirDatos(String datos, Class<T> record);
}
