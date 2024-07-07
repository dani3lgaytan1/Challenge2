package com.challengeLiterAlura.chellengeLiterAlura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
