package com.challengeLiterAlura.chellengeLiterAlura.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuestaLibros(@JsonAlias("count") int cantidad,
                                   @JsonAlias("next") String siguiente,
                                   @JsonAlias("previous") String anterior,
                                   @JsonAlias("results") List<DatosLibro> datosLibros) {
}
